package io.jmix.bookstore.test_data.data_provider;

import java.util.List;

public interface TestDataProvider<T, U> {

    List<T> create(U dataContext);
}
