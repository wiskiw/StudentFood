package by.wiskiw.studentfood.mvp.view;

import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import by.wiskiw.studentfood.mvp.model.RecipeGroup;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;

public interface MainView extends MvpView {

    void startListActivity(RecipeGroup type, @Nullable RecipeCategory recipeCategory);

}
