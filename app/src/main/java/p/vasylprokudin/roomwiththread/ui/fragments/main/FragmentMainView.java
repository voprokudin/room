package p.vasylprokudin.roomwiththread.ui.fragments.main;

import java.util.List;

import p.vasylprokudin.roomwiththread.model.User;

public interface FragmentMainView {
    void initViews();
    void showData(List<User> userList);
    void showRefreshedData(int i);
    void showContextMenu(int clickedItem, User user);
    void showMessage(String string);
}
