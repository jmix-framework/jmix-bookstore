package io.jmix.bookstore.entity.test_support;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class Users
        implements TestDataProvisioning<UserData, UserData.UserDataBuilder, User> {

    @Autowired
    UserRepository repository;

    public static final String DEFAULT_USERNAME = "username";
    public static final String DEFAULT_FIRST_NAME = "first_name";
    public static final String DEFAULT_LAST_NAME = "last_name";
    public static final String DEFAULT_EMAIL = "user@company.com";
    public static final String DEFAULT_RAW_PASSWORD = "user_name";
    @Autowired
    private UserMapper mapper;

    @Override
    public UserData.UserDataBuilder defaultData() {
        return UserData.builder()
                .username("%s-%s".formatted(DEFAULT_USERNAME, UUID.randomUUID()))
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .password("{noop}" + DEFAULT_RAW_PASSWORD);
    }

    @Override
    public User save(UserData data) {
        return repository.save(data);
    }

    @Override
    public User create(UserData data) {
        return mapper.toEntity(data);
    }

    @Override
    public User createDefault() {
        return create(defaultData().build());
    }

    @Override
    public User saveDefault() {
        return save(defaultData().build());
    }
}
