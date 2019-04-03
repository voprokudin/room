package p.vasylprokudin.roomwiththread.ui.fragments.details;

import p.vasylprokudin.roomwiththread.ui.IToolbarTitle;

public class FragmentDetailsPresenter {

    private FragmentDetails mView;
    private int clickedPosition;
    private IToolbarTitle mToolbarTitle;

    public FragmentDetailsPresenter(FragmentDetails mView, IToolbarTitle mToolbarTitle, int clickedPosition) {
        this.mView = mView;
        this.mToolbarTitle = mToolbarTitle;
        this.clickedPosition = clickedPosition;
        initPresenter();
    }

    private void initPresenter() {
        setToolbarTitle();
        mView.setData(clickedPosition);
    }

    private void setToolbarTitle() {
        String tag = mView.getTag();
        mToolbarTitle.setToolbarTitle(tag);
    }

}
