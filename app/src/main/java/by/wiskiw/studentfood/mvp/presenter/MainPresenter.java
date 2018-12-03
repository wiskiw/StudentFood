package by.wiskiw.studentfood.mvp.presenter;

import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.model.RecipeGroup;
import by.wiskiw.studentfood.mvp.view.MainView;

public class MainPresenter extends MvpBasePresenter<MainView> {

    public void onCategoryClicked(RecipeGroup group, @Nullable RecipeCategory recipeCategory) {
        ifViewAttached(view -> view.startListActivity(group, recipeCategory));
    }

}
