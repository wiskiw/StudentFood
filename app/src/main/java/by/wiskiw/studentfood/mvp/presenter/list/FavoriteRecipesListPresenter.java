package by.wiskiw.studentfood.mvp.presenter.list;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import by.wiskiw.studentfood.di.bus.RecipeUpdateAction;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.list.FavoriteRecipesListView;

public class FavoriteRecipesListPresenter extends RecipesListPresenter<FavoriteRecipesListView> {

    @Override
    public void attachView(FavoriteRecipesListView view) {
        super.attachView(view);

        loadList();
    }

    @Override
    protected void loadList() {
        ifViewAttached(view -> {
            List<SimpleRecipe> recipes = view.getRecipeRepository().getAllFavorite();
            sort(recipes);
            view.showRecipes(recipes);
        });
    }

    public void deleteRecipe(int listPos, SimpleRecipe simpleRecipe) {
        ifViewAttached(view -> {
            if (view.getRecipeRepository().delete(simpleRecipe.getId())) {
                // если был удален
                // обновляем список
                loadList();
            }
        });
    }

    public void removeRecipeFromFavorite(int listPos, SimpleRecipe simpleRecipe) {
        ifViewAttached(view -> {
            if (view.getRecipeRepository().removeFromFavorite(simpleRecipe.getId())) {
                // если был убран
                // обновляем список
                loadList();
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

    @Override
    public void onListItemUpdateEvent(RecipeUpdateAction action) {
        super.onListItemUpdateEvent(action);
        // обновляем список при получении события о изменении/удалении/добавлении элемента списка
        loadList();
    }
}
