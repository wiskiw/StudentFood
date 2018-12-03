package by.wiskiw.studentfood.mvp.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import by.wiskiw.studentfood.mvp.model.RecipeCategory;

public interface MainView extends MvpView {

    void startListActivity(RecipeCategory recipeCategory);

}
