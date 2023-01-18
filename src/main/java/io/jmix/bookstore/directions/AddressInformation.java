package io.jmix.bookstore.directions;

import io.jmix.bookstore.entity.Address;

public record AddressInformation(String street, String postalCode, String city, String state, String country){

    public static AddressInformation fromAddress(Address address) {
        return new AddressInformation(
                address.getStreet(),
                address.getPostCode(),
                address.getCity(),
                address.getState(),
                address.getCountry()
        );
    }
}
