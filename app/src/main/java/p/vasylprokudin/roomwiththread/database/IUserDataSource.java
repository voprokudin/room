package p.vasylprokudin.roomwiththread.database;

import java.util.List;

import p.vasylprokudin.roomwiththread.model.User;

public interface IUserDataSource {
    User getUserById(int userId);
    List<User> getAllUsers();
    void insertUser(User... users);
    void updateUser(User... users);
    void deleteUser(User users);
    void deleteAllUsers();
}
