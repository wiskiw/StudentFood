package by.wiskiw.studentfood.mvp.presenter;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.DescriptionView;

public class DescriptionPresenter extends MvpBasePresenter<DescriptionView> {

    private int recipeId = -1;
    private int recipeListPos = -1;

    public void setRecipeData(int recipeId, int recipeListPos) {
        this.recipeId = recipeId;
        this.recipeListPos = recipeListPos;
    }

    private void loadRecipe(DescriptionView view) {
        SimpleRecipe simpleRecipe = view.getRecipesRepository().get(recipeId);
        if (!simpleRecipe.isNull()) {
            view.showRecipe(simpleRecipe);
        } else {
            view.showRecipeNotFound(recipeId);
        }
    }

    @Override
    public void attachView(@NonNull DescriptionView view) {
        super.attachView(view);
        loadRecipe(view);
    }


    public void clickAddToFavorite() {
        ifViewAttached(view -> {
            RecipesRepositoryKt rep = view.getRecipesRepository();
            SimpleRecipe recipe = rep.get(recipeId);
            recipe.setFavorite(!recipe.isFavorite());
            rep.save(recipe);
        });
    }

}
