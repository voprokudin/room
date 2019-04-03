package p.vasylprokudin.roomwiththread.ui.fragments.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import p.vasylprokudin.roomwiththread.R;
import p.vasylprokudin.roomwiththread.ui.IToolbarTitle;

public class FragmentDetails extends Fragment implements FragmentDetailsView {
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    Unbinder unbinder;
    private int clickedPosition;
    private FragmentDetailsPresenter mPresenter;
    private IToolbarTitle mToolbarTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mToolbarTitle = (IToolbarTitle) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            String key = getString(R.string.intent_message);
            clickedPosition = bundle.getInt(key);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new FragmentDetailsPresenter(this, mToolbarTitle, clickedPosition);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setData(int clickedPosition) {
        tvDetail.setText(String.valueOf(clickedPosition));
    }

}
