package p.vasylprokudin.roomwiththread.ui.fragments.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import p.vasylprokudin.roomwiththread.R;
import p.vasylprokudin.roomwiththread.model.User;
import p.vasylprokudin.roomwiththread.ui.IToolbarTitle;

public class FragmentMain extends Fragment implements FragmentMainView, UsersAdapter.OnItemClicked {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;
    private UsersAdapter adapter;
    private FragmentMainPresenter mPresenter;
    private IToolbarTitle mToolbarTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mToolbarTitle = (IToolbarTitle) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        mPresenter = new FragmentMainPresenter(this, mToolbarTitle);
        return view;
    }

    @Override
    public void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showData(List<User> userList) {
        adapter = new UsersAdapter(userList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showRefreshedData(int i) {
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(i-1);
    }

    @Override
    public void showContextMenu(int clickedItem, final User user) {
        switch (clickedItem){
            case 0: //update
            {
                final EditText edName = new EditText(getActivity());
                edName.setText(user.getName());
                edName.setHint("Enter your name");
                new AlertDialog.Builder(getActivity())
                        .setTitle("Edit")
                        .setMessage("Edit Your Name")
                        .setView(edName)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String userName = edName.getText().toString();
                                        if (TextUtils.isEmpty(userName))
                                            return;
                                        else {
                                            user.setName(userName);
                                            mPresenter.updateUser(user);
                                        }
                                    }
                                }
                        ).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            break;
            case 1: //delete
            {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Do you want to delete " + user.toString())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.deleteUser(user);
                                    }
                                }
                        ).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            break;
        }
    }

    @Override
    public void showMessage(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        final EditText edName = new EditText(getActivity());
        edName.setHint("Enter your name");
        new AlertDialog.Builder(getActivity())
                .setTitle("Enter")
                .setMessage("Enter Your Name")
                .setView(edName)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (TextUtils.isEmpty(edName.getText().toString()))
                                    return;
                                else {
                                    String userName = edName.getText().toString();
                                    mPresenter.insertUser(userName);
                                }
                            }
                        }
                ).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_clear:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Deleting")
                        .setMessage("Are you sure you want to delete all users?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.deleteAllUsers();
                                    }
                                }
                        ).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        mPresenter.onGetClickedItem(item);
        return true;
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.onClickShowFragmentDetails(position);
    }
}
