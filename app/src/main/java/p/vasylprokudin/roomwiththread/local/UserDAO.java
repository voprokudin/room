package p.vasylprokudin.roomwiththread.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p.vasylprokudin.roomwiththread.model.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE id=:userId")
    User getUserById(int userId);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert
    void insertUser(User... users);

    @Update
    void updateUser(User... users);

    @Delete
    void deleteUser(User users);

    @Query("DELETE FROM users")
    void deleteAllUsers();

}
