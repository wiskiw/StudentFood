package by.wiskiw.studentfood.mvp.presenter;

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

    public void loadRecipe(DescriptionView view) {
        SimpleRecipe simpleRecipe = view.getRecipesRepository().get(recipeId);
        if (!simpleRecipe.isNull()) {
            view.showRecipe(simpleRecipe);
        } else {
            view.showRecipeNotFound(recipeId);
        }
    }

    public void clickAddToFavorite() {
        ifViewAttached(view -> {
            RecipesRepositoryKt rep = view.getRecipesRepository();
            SimpleRecipe recipe = rep.get(recipeId);
            recipe.setFavorite(!recipe.isFavorite());
            rep.save(recipe);
            view.showFavoriteButtonMarked(recipe.isFavorite());
        });
    }

    public void clickEditRecipe() {
        ifViewAttached(view -> view.startEditActivity(recipeId));
    }

    public void onItemBeenUpdate() {
        ifViewAttached(this::loadRecipe);
    }
}
