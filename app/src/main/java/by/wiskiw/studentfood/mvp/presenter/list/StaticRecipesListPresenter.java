package by.wiskiw.studentfood.mvp.presenter.list;

import android.support.annotation.NonNull;

import java.util.List;

import by.wiskiw.studentfood.di.bus.ListItemUpdateAction;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.list.StaticRecipesListView;

public class StaticRecipesListPresenter extends RecipesListPresenter<StaticRecipesListView> {

    private RecipeCategory recipeCategory;

    @Override
    public void attachView(@NonNull StaticRecipesListView view) {
        super.attachView(view);
        recipeCategory = view.getRecipeCategory();
        loadList(view);
    }

    private void loadList(StaticRecipesListView view) {
        List<SimpleRecipe> recipes = view.getStaticRecipeRep().getAll(recipeCategory);
        view.showRecipes(recipes);
    }

    public void editRecipe(int listPos, SimpleRecipe simpleRecipe) {
        // todo start edit activity
    }

    public void deleteRecipe(int listPos, SimpleRecipe simpleRecipe) {
        ifViewAttached(view -> {
            if (view.getStaticRecipeRep().delete(simpleRecipe.getId())) {
                // если был удален из БД, удаляем из "Любимых" и "Моих"
                view.getMyRecipeRep().removeFromMy(simpleRecipe.getId());
                view.getFavoriteRecipeRep().removeFromFavorites(simpleRecipe.getId());

                // обновляем список
                loadList(view);
            }
        });
    }

    @Override
    public void onListItemUpdateEvent(ListItemUpdateAction action) {
        super.onListItemUpdateEvent(action);
        // обновляем список при получении события о изменении/удалении/добавлении элемента списка
        ifViewAttached(this::loadList);
    }
}
