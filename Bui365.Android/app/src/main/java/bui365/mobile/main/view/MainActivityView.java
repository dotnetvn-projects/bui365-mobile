package bui365.mobile.main.view;

import bui365.mobile.main.BaseView;
import bui365.mobile.main.presenter.MainActivityPresenter;


public interface MainActivityView extends BaseView<MainActivityPresenter> {
    void showTaskDetailUi(int id);
}
