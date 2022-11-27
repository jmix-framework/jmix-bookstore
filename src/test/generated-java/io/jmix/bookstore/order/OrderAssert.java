package io.jmix.bookstore.order;

import java.util.Objects;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.internal.Iterables;

/**
 * {@link Order} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class OrderAssert extends AbstractObjectAssert<OrderAssert, Order> {

  /**
   * Creates a new <code>{@link OrderAssert}</code> to make assertions on actual Order.
   * @param actual the Order we want to make assertions on.
   */
  public OrderAssert(Order actual) {
    super(actual, OrderAssert.class);
  }

  /**
   * An entry point for OrderAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myOrder)</code> and get specific assertion with code completion.
   * @param actual the Order we want to make assertions on.
   * @return a new <code>{@link OrderAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static OrderAssert assertThat(Order actual) {
    return new OrderAssert(actual);
  }

  /**
   * Verifies that the actual Order's createdBy is equal to the given one.
   * @param createdBy the given createdBy to compare the actual Order's createdBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's createdBy is not equal to the given one.
   */
  public OrderAssert hasCreatedBy(String createdBy) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting createdBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualCreatedBy = actual.getCreatedBy();
    if (!Objects.deepEquals(actualCreatedBy, createdBy)) {
      failWithMessage(assertjErrorMessage, actual, createdBy, actualCreatedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's createdDate is equal to the given one.
   * @param createdDate the given createdDate to compare the actual Order's createdDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's createdDate is not equal to the given one.
   */
  public OrderAssert hasCreatedDate(java.util.Date createdDate) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting createdDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualCreatedDate = actual.getCreatedDate();
    if (!Objects.deepEquals(actualCreatedDate, createdDate)) {
      failWithMessage(assertjErrorMessage, actual, createdDate, actualCreatedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's customer is equal to the given one.
   * @param customer the given customer to compare the actual Order's customer to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's customer is not equal to the given one.
   */
  public OrderAssert hasCustomer(io.jmix.bookstore.customer.Customer customer) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting customer of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.customer.Customer actualCustomer = actual.getCustomer();
    if (!Objects.deepEquals(actualCustomer, customer)) {
      failWithMessage(assertjErrorMessage, actual, customer, actualCustomer);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's deletedBy is equal to the given one.
   * @param deletedBy the given deletedBy to compare the actual Order's deletedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's deletedBy is not equal to the given one.
   */
  public OrderAssert hasDeletedBy(String deletedBy) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting deletedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualDeletedBy = actual.getDeletedBy();
    if (!Objects.deepEquals(actualDeletedBy, deletedBy)) {
      failWithMessage(assertjErrorMessage, actual, deletedBy, actualDeletedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's deletedDate is equal to the given one.
   * @param deletedDate the given deletedDate to compare the actual Order's deletedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's deletedDate is not equal to the given one.
   */
  public OrderAssert hasDeletedDate(java.util.Date deletedDate) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting deletedDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualDeletedDate = actual.getDeletedDate();
    if (!Objects.deepEquals(actualDeletedDate, deletedDate)) {
      failWithMessage(assertjErrorMessage, actual, deletedDate, actualDeletedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's fulfilledBy is equal to the given one.
   * @param fulfilledBy the given fulfilledBy to compare the actual Order's fulfilledBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's fulfilledBy is not equal to the given one.
   */
  public OrderAssert hasFulfilledBy(io.jmix.bookstore.fulfillment.FulfillmentCenter fulfilledBy) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting fulfilledBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.fulfillment.FulfillmentCenter actualFulfilledBy = actual.getFulfilledBy();
    if (!Objects.deepEquals(actualFulfilledBy, fulfilledBy)) {
      failWithMessage(assertjErrorMessage, actual, fulfilledBy, actualFulfilledBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's id is equal to the given one.
   * @param id the given id to compare the actual Order's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's id is not equal to the given one.
   */
  public OrderAssert hasId(java.util.UUID id) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting id of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.UUID actualId = actual.getId();
    if (!Objects.deepEquals(actualId, id)) {
      failWithMessage(assertjErrorMessage, actual, id, actualId);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's lastModifiedBy is equal to the given one.
   * @param lastModifiedBy the given lastModifiedBy to compare the actual Order's lastModifiedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's lastModifiedBy is not equal to the given one.
   */
  public OrderAssert hasLastModifiedBy(String lastModifiedBy) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastModifiedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualLastModifiedBy = actual.getLastModifiedBy();
    if (!Objects.deepEquals(actualLastModifiedBy, lastModifiedBy)) {
      failWithMessage(assertjErrorMessage, actual, lastModifiedBy, actualLastModifiedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's lastModifiedDate is equal to the given one.
   * @param lastModifiedDate the given lastModifiedDate to compare the actual Order's lastModifiedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's lastModifiedDate is not equal to the given one.
   */
  public OrderAssert hasLastModifiedDate(java.util.Date lastModifiedDate) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastModifiedDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualLastModifiedDate = actual.getLastModifiedDate();
    if (!Objects.deepEquals(actualLastModifiedDate, lastModifiedDate)) {
      failWithMessage(assertjErrorMessage, actual, lastModifiedDate, actualLastModifiedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's orderDate is equal to the given one.
   * @param orderDate the given orderDate to compare the actual Order's orderDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's orderDate is not equal to the given one.
   */
  public OrderAssert hasOrderDate(java.time.LocalDate orderDate) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting orderDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.time.LocalDate actualOrderDate = actual.getOrderDate();
    if (!Objects.deepEquals(actualOrderDate, orderDate)) {
      failWithMessage(assertjErrorMessage, actual, orderDate, actualOrderDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's orderLines contains the given OrderLine elements.
   * @param orderLines the given elements that should be contained in actual Order's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual Order's orderLines does not contain all given OrderLine elements.
   */
  public OrderAssert hasOrderLines(OrderLine... orderLines) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // check that given OrderLine varargs is not null.
    if (orderLines == null) failWithMessage("Expecting orderLines parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getOrderLines(), orderLines);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's orderLines contains the given OrderLine elements in Collection.
   * @param orderLines the given elements that should be contained in actual Order's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual Order's orderLines does not contain all given OrderLine elements.
   */
  public OrderAssert hasOrderLines(java.util.Collection<? extends OrderLine> orderLines) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // check that given OrderLine collection is not null.
    if (orderLines == null) {
      failWithMessage("Expecting orderLines parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getOrderLines(), orderLines.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's orderLines contains <b>only</b> the given OrderLine elements and nothing else in whatever order.
   * @param orderLines the given elements that should be contained in actual Order's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual Order's orderLines does not contain all given OrderLine elements.
   */
  public OrderAssert hasOnlyOrderLines(OrderLine... orderLines) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // check that given OrderLine varargs is not null.
    if (orderLines == null) failWithMessage("Expecting orderLines parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getOrderLines(), orderLines);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's orderLines contains <b>only</b> the given OrderLine elements in Collection and nothing else in whatever order.
   * @param orderLines the given elements that should be contained in actual Order's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual Order's orderLines does not contain all given OrderLine elements.
   */
  public OrderAssert hasOnlyOrderLines(java.util.Collection<? extends OrderLine> orderLines) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // check that given OrderLine collection is not null.
    if (orderLines == null) {
      failWithMessage("Expecting orderLines parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getOrderLines(), orderLines.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's orderLines does not contain the given OrderLine elements.
   *
   * @param orderLines the given elements that should not be in actual Order's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual Order's orderLines contains any given OrderLine elements.
   */
  public OrderAssert doesNotHaveOrderLines(OrderLine... orderLines) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // check that given OrderLine varargs is not null.
    if (orderLines == null) failWithMessage("Expecting orderLines parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getOrderLines(), orderLines);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's orderLines does not contain the given OrderLine elements in Collection.
   *
   * @param orderLines the given elements that should not be in actual Order's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual Order's orderLines contains any given OrderLine elements.
   */
  public OrderAssert doesNotHaveOrderLines(java.util.Collection<? extends OrderLine> orderLines) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // check that given OrderLine collection is not null.
    if (orderLines == null) {
      failWithMessage("Expecting orderLines parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getOrderLines(), orderLines.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order has no orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual Order's orderLines is not empty.
   */
  public OrderAssert hasNoOrderLines() {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have orderLines but had :\n  <%s>";

    // check
    if (actual.getOrderLines().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getOrderLines());
    }

    // return the current assertion for method chaining
    return this;
  }


  /**
   * Verifies that the actual Order's shippingAddress is equal to the given one.
   * @param shippingAddress the given shippingAddress to compare the actual Order's shippingAddress to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's shippingAddress is not equal to the given one.
   */
  public OrderAssert hasShippingAddress(io.jmix.bookstore.entity.Address shippingAddress) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting shippingAddress of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.entity.Address actualShippingAddress = actual.getShippingAddress();
    if (!Objects.deepEquals(actualShippingAddress, shippingAddress)) {
      failWithMessage(assertjErrorMessage, actual, shippingAddress, actualShippingAddress);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's shippingDate is equal to the given one.
   * @param shippingDate the given shippingDate to compare the actual Order's shippingDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's shippingDate is not equal to the given one.
   */
  public OrderAssert hasShippingDate(java.time.LocalDate shippingDate) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting shippingDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.time.LocalDate actualShippingDate = actual.getShippingDate();
    if (!Objects.deepEquals(actualShippingDate, shippingDate)) {
      failWithMessage(assertjErrorMessage, actual, shippingDate, actualShippingDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's status is equal to the given one.
   * @param status the given status to compare the actual Order's status to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's status is not equal to the given one.
   */
  public OrderAssert hasStatus(OrderStatus status) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting status of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    OrderStatus actualStatus = actual.getStatus();
    if (!Objects.deepEquals(actualStatus, status)) {
      failWithMessage(assertjErrorMessage, actual, status, actualStatus);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's tenant is equal to the given one.
   * @param tenant the given tenant to compare the actual Order's tenant to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's tenant is not equal to the given one.
   */
  public OrderAssert hasTenant(String tenant) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting tenant of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualTenant = actual.getTenant();
    if (!Objects.deepEquals(actualTenant, tenant)) {
      failWithMessage(assertjErrorMessage, actual, tenant, actualTenant);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Order's version is equal to the given one.
   * @param version the given version to compare the actual Order's version to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Order's version is not equal to the given one.
   */
  public OrderAssert hasVersion(Integer version) {
    // check that actual Order we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting version of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualVersion = actual.getVersion();
    if (!Objects.deepEquals(actualVersion, version)) {
      failWithMessage(assertjErrorMessage, actual, version, actualVersion);
    }

    // return the current assertion for method chaining
    return this;
  }

}
