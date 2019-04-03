package p.vasylprokudin.roomwiththread.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import p.vasylprokudin.roomwiththread.R;

public class MainActivity extends AppCompatActivity implements MainActivityView, IToolbarTitle {

    private MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainActivityPresenter(this);
    }

    @Override
    public void showFragment(Fragment fragment, String tag, boolean addToBackStack, Object o, String message) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!message.equals("")){
            Bundle bundle = new Bundle();
            switch (o.getClass().getName()){
                case "java.lang.Integer":
                    bundle.putInt(getString(R.string.intent_message), (Integer) o);
                    fragment.setArguments(bundle);
                    break;
                case "java.lang.String":
                    bundle.putString(getString(R.string.intent_message), (String) o);
                    fragment.setArguments(bundle);
                    break;
                default: break;
            }
        }
        ft.replace(R.id.main_container, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }

    @Override
    public void setToolbarTitle(String string) {
        getSupportActionBar().setTitle(string);
    }

}