package p.vasylprokudin.roomwiththread.ui;

import android.os.AsyncTask;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import p.vasylprokudin.roomwiththread.R;
import p.vasylprokudin.roomwiththread.database.UserRepository;
import p.vasylprokudin.roomwiththread.local.UserDataSource;
import p.vasylprokudin.roomwiththread.local.UserDatabase;
import p.vasylprokudin.roomwiththread.model.User;
import p.vasylprokudin.roomwiththread.ui.fragments.main.FragmentMain;

public class MainActivityPresenter {

    private MainActivity mView;

    public MainActivityPresenter(MainActivity mView) {
        this.mView = mView;
        initPresenter();
    }

    private void initPresenter() {
        String tag = mView.getString(R.string.fragmentMain);
        FragmentMain fragmentMain = new FragmentMain();
        mView.showFragment(fragmentMain, tag, false, null, "");
    }

}
