package p.vasylprokudin.roomwiththread.ui;

import android.support.v4.app.Fragment;

import java.util.List;

import p.vasylprokudin.roomwiththread.model.User;

public interface MainActivityView<T> {
    void showFragment(Fragment fragment, String tag, boolean addToBackStack, T t, String message);
}

