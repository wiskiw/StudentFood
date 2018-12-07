package by.wiskiw.studentfood.mvp.presenter.list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import by.wiskiw.studentfood.di.bus.ListItemUpdateAction;
import by.wiskiw.studentfood.mvp.view.list.CategoryListView;

public abstract class CategoryListPresenter<V extends CategoryListView>
        extends MvpBasePresenter<V> {

    public void onListItemUpdateEvent(ListItemUpdateAction action) {

    }

}
