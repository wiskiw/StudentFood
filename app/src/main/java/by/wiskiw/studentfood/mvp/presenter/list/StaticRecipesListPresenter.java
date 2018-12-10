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
        List<SimpleRecipe> recipes = view.getRecipeRepository().getAll(recipeCategory);
        view.showRecipes(recipes);
    }

    public void deleteRecipe(int listPos, SimpleRecipe simpleRecipe) {
        ifViewAttached(view -> {
            if (view.getRecipeRepository().delete(simpleRecipe.getId())) {
                // если был удален из БД
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
