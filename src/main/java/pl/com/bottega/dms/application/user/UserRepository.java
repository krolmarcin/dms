package pl.com.bottega.dms.application.user;

import pl.com.bottega.dms.model.EmployeeId;

public interface UserRepository {


    void put(User user);

    User findByEmployeeId(EmployeeId employeeId);

    User findByUserName(String userName);

    User findByLoginAndHashedPassword(String login, String hashedPassword);

}
