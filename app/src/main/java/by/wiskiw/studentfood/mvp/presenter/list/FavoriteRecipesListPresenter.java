package by.wiskiw.studentfood.mvp.presenter.list;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.list.FavoriteRecipesListView;

public class FavoriteRecipesListPresenter extends RecipesListPresenter<FavoriteRecipesListView> {

    @Override
    public void attachView(FavoriteRecipesListView view) {
        super.attachView(view);

        loadList(view);
    }

    private void loadList(FavoriteRecipesListView view) {
        List<SimpleRecipe> recipes = view.getRecipeRepository().getAllFavorite();
        view.showRecipes(recipes);
    }

    public void editRecipe(int listPos, SimpleRecipe simpleRecipe) {
        // todo start edit activity
    }

    public void deleteRecipe(int listPos, SimpleRecipe simpleRecipe) {
        ifViewAttached(view -> {
            if (view.getRecipeRepository().delete(simpleRecipe.getId())) {
                // если был удален
                // обновляем список
                loadList(view);
            }
        });
    }

    public void removeRecipeFromFavorite(int listPos, SimpleRecipe simpleRecipe) {
        ifViewAttached(view -> {
            if (view.getRecipeRepository().removeFromFavorite(simpleRecipe.getId())) {
                // если был убран
                // обновляем список
                loadList(view);
            }
        });
    }

    public boolean isMyRecipe(int recipeId) {
        AtomicBoolean isMy = new AtomicBoolean(true);
        ifViewAttached(view ->
                isMy.set(view.getRecipeRepository().get(recipeId).isMine())
        );
        return isMy.get();
    }
}
