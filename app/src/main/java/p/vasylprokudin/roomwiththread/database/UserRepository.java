package p.vasylprokudin.roomwiththread.database;

import java.util.List;

import p.vasylprokudin.roomwiththread.model.User;

public class UserRepository implements IUserDataSource {

    private IUserDataSource mLocalDataSource;
    private static UserRepository mInstance;

    public UserRepository(IUserDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(IUserDataSource mLocalDataSource){
        if (mInstance == null){
            mInstance = new UserRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public User getUserById(int userId) {
        return mLocalDataSource.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
        mLocalDataSource.insertUser(users);
    }

    @Override
    public void updateUser(User... users) {
        mLocalDataSource.updateUser(users);
    }

    @Override
    public void deleteUser(User users) {
        mLocalDataSource.deleteUser(users);
    }

    @Override
    public void deleteAllUsers() {
        mLocalDataSource.deleteAllUsers();
    }
}
