package by.wiskiw.studentfood.mvp.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import by.wiskiw.studentfood.data.db.repository.FavoriteRecipeRepositoryKt;
import by.wiskiw.studentfood.data.db.repository.StaticRecipeRepositoryKt;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface DescriptionView extends MvpView {

    void showRecipe(SimpleRecipe recipe);

    void showRecipeNotFound(int recipeId);

    StaticRecipeRepositoryKt getStaticRecipeRep();

    FavoriteRecipeRepositoryKt getFavoriteRecipeRep();

}
