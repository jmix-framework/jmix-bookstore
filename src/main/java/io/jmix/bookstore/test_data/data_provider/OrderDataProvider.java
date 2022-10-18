package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.product.supplier.Currency;
import io.jmix.bookstore.product.supplier.Money;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.bookstore.product.Product;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import net.datafaker.Address;
import net.datafaker.DateAndTime;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.*;

@Component("bookstore_OrderDataProvider")
public class OrderDataProvider implements TestDataProvider<Order, OrderDataProvider.Dependencies> {

    public record Dependencies(List<Customer> customers, List<Product> products){}

    protected final DataManager dataManager;

    public OrderDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Order> create(int amount, Dependencies dependencies) {
        List<Order> orders = createOrders(amount, dependencies.customers(), dependencies.products());
        SaveContext saveContext = new SaveContext();
        orders.forEach(saveContext::saving);

        orders.stream().flatMap(order -> order.getOrderLines().stream())
                .forEach(saveContext::saving);

        commit(saveContext);

        return orders;
    }


    private List<Order> createOrders(int amount, List<Customer> customers, List<Product> products) {
        Faker faker = new Faker();

        return Stream.generate(faker::address).limit(amount)
                .map(address -> toOrder(address, customers, products))
                .collect(Collectors.toList());
    }

    private  Order toOrder(Address address, List<Customer> customers, List<Product> products) {
        Order order = dataManager.create(Order.class);

        order.setCustomer(randomOfList(customers));
        LocalDate orderDate = randomPastLocalDate(10);
        order.setOrderDate(orderDate);
        order.setShippingDate(orderDate.plusDays(randomPositiveNumber(30)));
        order.setShippingAddress(toAddress(address));
        order.setOrderLines(
                generateOrderLines(randomPositiveNumber(5), order, products)
        );
        return order;
    }
    private io.jmix.bookstore.product.supplier.Address toAddress(Address address) {
        io.jmix.bookstore.product.supplier.Address addressEntity = dataManager.create(io.jmix.bookstore.product.supplier.Address.class);
        addressEntity.setCity(address.city());
        addressEntity.setStreet(String.format("%s %s", address.streetName(), address.buildingNumber()));
        addressEntity.setPostCode(address.postcode());
        return addressEntity;
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
