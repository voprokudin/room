package p.vasylprokudin.roomwiththread.ui.fragments.main;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import p.vasylprokudin.roomwiththread.R;
import p.vasylprokudin.roomwiththread.database.UserRepository;
import p.vasylprokudin.roomwiththread.local.UserDataSource;
import p.vasylprokudin.roomwiththread.local.UserDatabase;
import p.vasylprokudin.roomwiththread.model.User;
import p.vasylprokudin.roomwiththread.ui.IToolbarTitle;
import p.vasylprokudin.roomwiththread.ui.MainActivityView;
import p.vasylprokudin.roomwiththread.ui.fragments.details.FragmentDetails;

public class FragmentMainPresenter {
    private FragmentMain mView;
    private List<User> userList = new ArrayList<>();
    //Database
    private UserRepository userRepository;
    private IToolbarTitle mToolbarTitle;

    public FragmentMainPresenter(FragmentMain mView, IToolbarTitle mToolbarTitle) {
        this.mView = mView;
        this.mToolbarTitle = mToolbarTitle;
        mView.initViews();
        initPresenter();
    }

    private void initPresenter() {
        setToolbarTitle();
        mView.showData(userList);
        //Database
        UserDatabase userDatabase = UserDatabase.getInstance(mView.getActivity());//Create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));
        //Load all data from Database
        loadData();
    }

    private void setToolbarTitle() {
        mToolbarTitle.setToolbarTitle(mView.getTag());
    }

    private void loadData() {
        //first way with runOnUiThread method
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<User> userList = userRepository.getAllUsers();
                mView.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onGetAllUserSuccess(userList);
                    }
                });
            }
        }).start();


        //second way with handler
        /*final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<User> userList = userRepository.getAllUsers();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onGetAllUserSuccess(userList);
                    }
                });
            }
        }).start();*/
    }

    private void onGetAllUserSuccess(List<User> users) {
        userList.clear();
        userList.addAll(users);
        int listSize = users.size();
        mView.showRefreshedData(listSize);
    }

    public void insertUser(final String userName){
        //in background thread with Thread
        Thread insertThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(userName, UUID.randomUUID()
                        .toString()+ "@gmail.com");
                userRepository.insertUser(user);
                loadData();
            }
        });
        insertThread.start();

        /*
        //in background thread with AsyncTask
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User(userName, UUID.randomUUID()
                        .toString()+ "@gmail.com");
                userRepository.insertUser(user);
                return null;
            }
        };
        asyncTask.execute();*/
    }

    public void updateUser(final User user) {
        Thread updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                userRepository.updateUser(user);
                loadData();
            }
        });
        updateThread.start();


        //in background thread with AsyncTask
        /*AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userRepository.updateUser(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                loadData();
            }
        };
        asyncTask.execute();*/
    }


    public void deleteUser(final User user) {
        Thread deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                userRepository.deleteUser(user);
                loadData();
            }
        });
        deleteThread.start();
    }

    public void deleteAllUsers() {
        Thread deleteAllThread = new Thread(new Runnable() {
            @Override
            public void run() {
                userRepository.deleteAllUsers();
                loadData();
            }
        });
        deleteAllThread.start();
    }

    public void onGetClickedItem(MenuItem item) {
        int position = item.getOrder();
        int clickedItem = item.getItemId();
        mView.showMessage(String.valueOf(position));
        final User user = userList.get(position);

        mView.showContextMenu(clickedItem, user);
    }

    public void onClickShowFragmentDetails(int position) {
        String tag = mView.getString(R.string.fragmentDetails);
        String key = mView.getString(R.string.intent_message);
        FragmentDetails fragmentDetails = new FragmentDetails();
        MainActivityView mShowFragment = (MainActivityView) mView.getActivity();
        mShowFragment.showFragment(fragmentDetails, tag, true, position, key);
    }
}
