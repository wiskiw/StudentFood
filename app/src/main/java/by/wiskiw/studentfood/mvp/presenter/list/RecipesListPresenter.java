package by.wiskiw.studentfood.mvp.presenter.list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import by.wiskiw.studentfood.di.bus.ListItemUpdateAction;
import by.wiskiw.studentfood.mvp.view.list.RecipesListView;

public abstract class RecipesListPresenter<V extends RecipesListView>
        extends MvpBasePresenter<V> {

    public void onListItemUpdateEvent(ListItemUpdateAction action) {

    }

}
