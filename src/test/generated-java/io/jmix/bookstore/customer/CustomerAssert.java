package io.jmix.bookstore.customer;

import java.util.Objects;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.internal.Iterables;

/**
 * {@link Customer} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class CustomerAssert extends AbstractObjectAssert<CustomerAssert, Customer> {

  /**
   * Creates a new <code>{@link CustomerAssert}</code> to make assertions on actual Customer.
   * @param actual the Customer we want to make assertions on.
   */
  public CustomerAssert(Customer actual) {
    super(actual, CustomerAssert.class);
  }

  /**
   * An entry point for CustomerAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myCustomer)</code> and get specific assertion with code completion.
   * @param actual the Customer we want to make assertions on.
   * @return a new <code>{@link CustomerAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static CustomerAssert assertThat(Customer actual) {
    return new CustomerAssert(actual);
  }

  /**
   * Verifies that the actual Customer's address is equal to the given one.
   * @param address the given address to compare the actual Customer's address to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's address is not equal to the given one.
   */
  public CustomerAssert hasAddress(io.jmix.bookstore.entity.Address address) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting address of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.entity.Address actualAddress = actual.getAddress();
    if (!Objects.deepEquals(actualAddress, address)) {
      failWithMessage(assertjErrorMessage, actual, address, actualAddress);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's createdBy is equal to the given one.
   * @param createdBy the given createdBy to compare the actual Customer's createdBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's createdBy is not equal to the given one.
   */
  public CustomerAssert hasCreatedBy(String createdBy) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's createdDate is equal to the given one.
   * @param createdDate the given createdDate to compare the actual Customer's createdDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's createdDate is not equal to the given one.
   */
  public CustomerAssert hasCreatedDate(java.util.Date createdDate) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's deletedBy is equal to the given one.
   * @param deletedBy the given deletedBy to compare the actual Customer's deletedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's deletedBy is not equal to the given one.
   */
  public CustomerAssert hasDeletedBy(String deletedBy) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's deletedDate is equal to the given one.
   * @param deletedDate the given deletedDate to compare the actual Customer's deletedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's deletedDate is not equal to the given one.
   */
  public CustomerAssert hasDeletedDate(java.util.Date deletedDate) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's email is equal to the given one.
   * @param email the given email to compare the actual Customer's email to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's email is not equal to the given one.
   */
  public CustomerAssert hasEmail(String email) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting email of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualEmail = actual.getEmail();
    if (!Objects.deepEquals(actualEmail, email)) {
      failWithMessage(assertjErrorMessage, actual, email, actualEmail);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's firstName is equal to the given one.
   * @param firstName the given firstName to compare the actual Customer's firstName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's firstName is not equal to the given one.
   */
  public CustomerAssert hasFirstName(String firstName) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting firstName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualFirstName = actual.getFirstName();
    if (!Objects.deepEquals(actualFirstName, firstName)) {
      failWithMessage(assertjErrorMessage, actual, firstName, actualFirstName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's fullName is equal to the given one.
   * @param fullName the given fullName to compare the actual Customer's fullName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's fullName is not equal to the given one.
   */
  public CustomerAssert hasFullName(String fullName) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting fullName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualFullName = actual.getFullName();
    if (!Objects.deepEquals(actualFullName, fullName)) {
      failWithMessage(assertjErrorMessage, actual, fullName, actualFullName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's geometry is equal to the given one.
   * @param geometry the given geometry to compare the actual Customer's geometry to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's geometry is not equal to the given one.
   */
  public CustomerAssert hasGeometry(org.locationtech.jts.geom.Point geometry) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting geometry of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.locationtech.jts.geom.Point actualGeometry = actual.getGeometry();
    if (!Objects.deepEquals(actualGeometry, geometry)) {
      failWithMessage(assertjErrorMessage, actual, geometry, actualGeometry);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's id is equal to the given one.
   * @param id the given id to compare the actual Customer's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's id is not equal to the given one.
   */
  public CustomerAssert hasId(java.util.UUID id) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's instanceName is equal to the given one.
   * @param instanceName the given instanceName to compare the actual Customer's instanceName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's instanceName is not equal to the given one.
   */
  public CustomerAssert hasInstanceName(String instanceName) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting instanceName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualInstanceName = actual.getInstanceName();
    if (!Objects.deepEquals(actualInstanceName, instanceName)) {
      failWithMessage(assertjErrorMessage, actual, instanceName, actualInstanceName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's lastModifiedBy is equal to the given one.
   * @param lastModifiedBy the given lastModifiedBy to compare the actual Customer's lastModifiedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's lastModifiedBy is not equal to the given one.
   */
  public CustomerAssert hasLastModifiedBy(String lastModifiedBy) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's lastModifiedDate is equal to the given one.
   * @param lastModifiedDate the given lastModifiedDate to compare the actual Customer's lastModifiedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's lastModifiedDate is not equal to the given one.
   */
  public CustomerAssert hasLastModifiedDate(java.util.Date lastModifiedDate) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's lastName is equal to the given one.
   * @param lastName the given lastName to compare the actual Customer's lastName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's lastName is not equal to the given one.
   */
  public CustomerAssert hasLastName(String lastName) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualLastName = actual.getLastName();
    if (!Objects.deepEquals(actualLastName, lastName)) {
      failWithMessage(assertjErrorMessage, actual, lastName, actualLastName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's orders contains the given io.jmix.bookstore.order.Order elements.
   * @param orders the given elements that should be contained in actual Customer's orders.
   * @return this assertion object.
   * @throws AssertionError if the actual Customer's orders does not contain all given io.jmix.bookstore.order.Order elements.
   */
  public CustomerAssert hasOrders(io.jmix.bookstore.order.Order... orders) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // check that given io.jmix.bookstore.order.Order varargs is not null.
    if (orders == null) failWithMessage("Expecting orders parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getOrders(), orders);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's orders contains the given io.jmix.bookstore.order.Order elements in Collection.
   * @param orders the given elements that should be contained in actual Customer's orders.
   * @return this assertion object.
   * @throws AssertionError if the actual Customer's orders does not contain all given io.jmix.bookstore.order.Order elements.
   */
  public CustomerAssert hasOrders(java.util.Collection<? extends io.jmix.bookstore.order.Order> orders) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // check that given io.jmix.bookstore.order.Order collection is not null.
    if (orders == null) {
      failWithMessage("Expecting orders parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getOrders(), orders.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's orders contains <b>only</b> the given io.jmix.bookstore.order.Order elements and nothing else in whatever order.
   * @param orders the given elements that should be contained in actual Customer's orders.
   * @return this assertion object.
   * @throws AssertionError if the actual Customer's orders does not contain all given io.jmix.bookstore.order.Order elements.
   */
  public CustomerAssert hasOnlyOrders(io.jmix.bookstore.order.Order... orders) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // check that given io.jmix.bookstore.order.Order varargs is not null.
    if (orders == null) failWithMessage("Expecting orders parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getOrders(), orders);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's orders contains <b>only</b> the given io.jmix.bookstore.order.Order elements in Collection and nothing else in whatever order.
   * @param orders the given elements that should be contained in actual Customer's orders.
   * @return this assertion object.
   * @throws AssertionError if the actual Customer's orders does not contain all given io.jmix.bookstore.order.Order elements.
   */
  public CustomerAssert hasOnlyOrders(java.util.Collection<? extends io.jmix.bookstore.order.Order> orders) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // check that given io.jmix.bookstore.order.Order collection is not null.
    if (orders == null) {
      failWithMessage("Expecting orders parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getOrders(), orders.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's orders does not contain the given io.jmix.bookstore.order.Order elements.
   *
   * @param orders the given elements that should not be in actual Customer's orders.
   * @return this assertion object.
   * @throws AssertionError if the actual Customer's orders contains any given io.jmix.bookstore.order.Order elements.
   */
  public CustomerAssert doesNotHaveOrders(io.jmix.bookstore.order.Order... orders) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // check that given io.jmix.bookstore.order.Order varargs is not null.
    if (orders == null) failWithMessage("Expecting orders parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getOrders(), orders);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer's orders does not contain the given io.jmix.bookstore.order.Order elements in Collection.
   *
   * @param orders the given elements that should not be in actual Customer's orders.
   * @return this assertion object.
   * @throws AssertionError if the actual Customer's orders contains any given io.jmix.bookstore.order.Order elements.
   */
  public CustomerAssert doesNotHaveOrders(java.util.Collection<? extends io.jmix.bookstore.order.Order> orders) {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // check that given io.jmix.bookstore.order.Order collection is not null.
    if (orders == null) {
      failWithMessage("Expecting orders parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getOrders(), orders.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Customer has no orders.
   * @return this assertion object.
   * @throws AssertionError if the actual Customer's orders is not empty.
   */
  public CustomerAssert hasNoOrders() {
    // check that actual Customer we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have orders but had :\n  <%s>";

    // check
    if (actual.getOrders().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getOrders());
    }

    // return the current assertion for method chaining
    return this;
  }


  /**
   * Verifies that the actual Customer's tenant is equal to the given one.
   * @param tenant the given tenant to compare the actual Customer's tenant to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's tenant is not equal to the given one.
   */
  public CustomerAssert hasTenant(String tenant) {
    // check that actual Customer we want to make assertions on is not null.
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
   * Verifies that the actual Customer's version is equal to the given one.
   * @param version the given version to compare the actual Customer's version to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Customer's version is not equal to the given one.
   */
  public CustomerAssert hasVersion(Integer version) {
    // check that actual Customer we want to make assertions on is not null.
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
