package io.jmix.bookstore.entity;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions extends org.assertj.core.api.Assertions {

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.customer.CustomerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.customer.CustomerAssert assertThat(io.jmix.bookstore.customer.Customer actual) {
    return new io.jmix.bookstore.customer.CustomerAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.entity.AddressAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.entity.AddressAssert assertThat(io.jmix.bookstore.entity.Address actual) {
    return new io.jmix.bookstore.entity.AddressAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.entity.MoneyAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.entity.MoneyAssert assertThat(io.jmix.bookstore.entity.Money actual) {
    return new io.jmix.bookstore.entity.MoneyAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.fulfillment.FulfillmentCenterAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.fulfillment.FulfillmentCenterAssert assertThat(io.jmix.bookstore.fulfillment.FulfillmentCenter actual) {
    return new io.jmix.bookstore.fulfillment.FulfillmentCenterAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.order.entity.OrderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.order.entity.OrderAssert assertThat(io.jmix.bookstore.order.entity.Order actual) {
    return new io.jmix.bookstore.order.entity.OrderAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.order.entity.OrderLineAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.order.entity.OrderLineAssert assertThat(io.jmix.bookstore.order.entity.OrderLine actual) {
    return new io.jmix.bookstore.order.entity.OrderLineAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.product.ProductAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.product.ProductAssert assertThat(io.jmix.bookstore.product.Product actual) {
    return new io.jmix.bookstore.product.ProductAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.product.ProductCategoryAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.product.ProductCategoryAssert assertThat(io.jmix.bookstore.product.ProductCategory actual) {
    return new io.jmix.bookstore.product.ProductCategoryAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.product.supplier.SupplierAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.product.supplier.SupplierAssert assertThat(io.jmix.bookstore.product.supplier.Supplier actual) {
    return new io.jmix.bookstore.product.supplier.SupplierAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.product.supplier.SupplierOrderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.product.supplier.SupplierOrderAssert assertThat(io.jmix.bookstore.product.supplier.SupplierOrder actual) {
    return new io.jmix.bookstore.product.supplier.SupplierOrderAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.product.supplier.SupplierOrderLineAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.product.supplier.SupplierOrderLineAssert assertThat(io.jmix.bookstore.product.supplier.SupplierOrderLine actual) {
    return new io.jmix.bookstore.product.supplier.SupplierOrderLineAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link io.jmix.bookstore.product.supplier.SupplierOrderRequestAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static io.jmix.bookstore.product.supplier.SupplierOrderRequestAssert assertThat(io.jmix.bookstore.product.supplier.SupplierOrderRequest actual) {
    return new io.jmix.bookstore.product.supplier.SupplierOrderRequestAssert(actual);
  }

  protected Assertions() {
    // empty
  }
}