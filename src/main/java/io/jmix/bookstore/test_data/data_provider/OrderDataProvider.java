package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import io.jmix.bookstore.test_data.data_provider.fulfillment_center.AvailableFulfillmentCenters;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import net.datafaker.Address;
import net.datafaker.DateAndTime;
import net.datafaker.Faker;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.*;

@Component("bookstore_OrderDataProvider")
public class OrderDataProvider implements TestDataProvider<Order, OrderDataProvider.DataContext> {

    public record DataContext(int amount,
                              List<Customer> customers,
                              List<Product> products,
                              AvailableFulfillmentCenters fulfillmentCenters,
                              AvailableTerritories territories
    ){}

    protected final DataManager dataManager;

    public OrderDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Order> create(DataContext dataContext) {
        List<Order> orders = createOrders(
                dataContext.amount(),
                dataContext.customers(),
                dataContext.products(),
                dataContext.fulfillmentCenters(),
                dataContext.territories()
        );
        SaveContext saveContext = new SaveContext();
        saveContext.setDiscardSaved(true);

        orders.forEach(saveContext::saving);

        orders.stream().flatMap(order -> order.getOrderLines().stream())
                .forEach(saveContext::saving);

        commit(saveContext);

        return orders;
    }


    private List<Order> createOrders(int amount, List<Customer> customers, List<Product> products, AvailableFulfillmentCenters fulfillmentCenters, AvailableTerritories territories) {
        Faker faker = new Faker();

        return Stream.generate(faker::address).limit(amount)
                .map(address -> toOrder(address, customers, products, fulfillmentCenters, territories))
                .collect(Collectors.toList());
    }

    private  Order toOrder(Address address, List<Customer> customers, List<Product> products, AvailableFulfillmentCenters fulfillmentCenters, AvailableTerritories territories) {
        Order order = dataManager.create(Order.class);

        Customer customer = randomOfList(customers);

        io.jmix.bookstore.entity.Address customerAddress = customer.getAddress();
        Point position = customerAddress.getPosition();


        order.setCustomer(customer);
        LocalDate orderDate = randomPastLocalDate(10);
        order.setOrderDate(orderDate);
        order.setShippingDate(orderDate.plusDays(randomPositiveNumber(30)));
        order.setShippingAddress(customerAddress);
        OrderStatus orderStatus = randomEnum(OrderStatus.values());
        order.setStatus(orderStatus);



        if (!orderStatus.equals(OrderStatus.NEW)) {
            territories.findTerritoryForPosition(position)
                    .flatMap(territory -> fulfillmentCenters.findByRegion(territory.getRegion()))
                    .ifPresentOrElse(
                            order::setFulfilledBy,
                            () -> order.setFulfilledBy(fulfillmentCenters.random())
                            );
        }

        order.setOrderLines(
                generateOrderLines(randomPositiveNumber(5), order, products)
        );
        return order;
    }

    private List<OrderLine> generateOrderLines(int amount, Order order, List<Product> products) {

        Faker faker = new Faker();

        return Stream.generate(faker::date).limit(amount)
                .map(date -> toOrderLine(date, order, products))
                .collect(Collectors.groupingBy(o -> o.getProduct().getName()))
                .values().stream()
                .map(orderLines -> orderLines.get(0))
                .collect(Collectors.toList());

    }

    private OrderLine toOrderLine(DateAndTime date, Order order, List<Product> products) {
        OrderLine orderLine = dataManager.create(OrderLine.class);

        Product product = randomOfList(products);
        orderLine.setProduct(product);
        orderLine.setOrder(order);
        orderLine.setQuantity(randomPositiveNumber(10));
        Money unitPrice = product.getUnitPrice();
        orderLine.setUnitPrice(unitPrice);
        Money discount = randomPrice();
        discount.setCurrency(unitPrice.getCurrency());

        orderLine.setDiscount(discount);
        return orderLine;
    }

    private Money randomPrice() {
        Faker faker = new Faker();
        Money money = dataManager.create(Money.class);
        money.setAmount(BigDecimal.valueOf(faker.number().numberBetween(15L,80L)));
        money.setCurrency(randomEnum(Currency.values()));
        return money;
    }

    private EntitySet commit(SaveContext saveContext) {
        return dataManager.save(saveContext);
    }
}
