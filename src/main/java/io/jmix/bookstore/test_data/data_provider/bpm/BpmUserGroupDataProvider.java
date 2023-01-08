package io.jmix.bookstore.test_data.data_provider.bpm;

import io.jmix.bookstore.security.OrderFulfillmentRole;
import io.jmix.bookstore.security.ProcurementSpecialistRole;
import io.jmix.bookstore.security.ProcurementManagerRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.entity.UserGroupRole;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("bookstore_BpmUserGroupDataProvider")
public class BpmUserGroupDataProvider implements TestDataProvider<UserGroup, BpmUserGroupDataProvider.DataContext> {

    protected final DataManager dataManager;

    public record DataContext(){}
    public BpmUserGroupDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<UserGroup> create(DataContext dataContext) {
        return findUserGroupsOf(commit(createUserGroups()));
    }

    private List<Object> createUserGroups() {

        List<UserGroupData> userGroups = List.of(
                new UserGroupData("Order Fulfillment Specialists", "order-fulfillment-specialists", List.of(OrderFulfillmentRole.CODE)),
                new UserGroupData("Procurement Specialists", "procurement-specialists", List.of(ProcurementSpecialistRole.CODE, ProcurementManagerRole.CODE))
        );

        return userGroups.stream()
                .flatMap(this::toEntities)
                .collect(Collectors.toList());
    }

    private Stream<Object> toEntities(UserGroupData userGroupData) {
        UserGroup userGroup = dataManager.create(UserGroup.class);
        userGroup.setName(userGroupData.name());
        userGroup.setCode(userGroupData.code());
        List<UserGroupRole> userGroupRoles = toUserGroupRoles(userGroup, userGroupData.roleCodes());
        userGroup.setUserGroupRoles(userGroupRoles);

        return Stream.concat(Stream.of(userGroup), userGroupRoles.stream());
    }

    private List<UserGroupRole> toUserGroupRoles(UserGroup userGroup, List<String> roleCodes) {
        return roleCodes.stream().map(roleCode -> toUserGroupRole(userGroup, roleCode)).collect(Collectors.toList());
    }

    private UserGroupRole toUserGroupRole(UserGroup userGroup, String roleCode) {
        UserGroupRole userGroupRole = dataManager.create(UserGroupRole.class);
        userGroupRole.setUserGroup(userGroup);
        userGroupRole.setRoleCode(roleCode);
        return userGroupRole;
    }

    record UserGroupData(String name, String code, List<String> roleCodes) {}

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }
    private List<UserGroup> findUserGroupsOf(List<Object> storedEntities) {
        return storedEntities.stream()
                .filter(o -> o instanceof UserGroup)
                .map(user -> (UserGroup) user)
                .collect(Collectors.toList());
    }
}
