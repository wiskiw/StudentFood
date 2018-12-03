package by.wiskiw.studentfood.mvp.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.view.MainView;

public class MainPresenter extends MvpBasePresenter<MainView> {

    public void onCategoryClicked(RecipeCategory category) {
        ifViewAttached(view -> view.startListActivity(category));
    }

}
