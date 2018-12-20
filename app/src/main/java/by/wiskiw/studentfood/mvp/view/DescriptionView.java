package by.wiskiw.studentfood.mvp.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface DescriptionView extends MvpView {

    void showRecipe(SimpleRecipe recipe);

    void showRecipeNotFound(int recipeId);

    void showFavoriteButtonMarked(boolean isMarked);

    RecipesRepositoryKt getRecipesRepository();

    void startEditActivity(int recipeId);

}
