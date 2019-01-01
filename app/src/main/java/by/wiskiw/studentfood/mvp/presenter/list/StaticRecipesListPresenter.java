package by.wiskiw.studentfood.mvp.presenter.list;

import android.support.annotation.NonNull;

import java.util.List;

import by.wiskiw.studentfood.di.bus.RecipeUpdateAction;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.list.StaticRecipesListView;

public class StaticRecipesListPresenter extends RecipesListPresenter<StaticRecipesListView> {

    private RecipeCategory recipeCategory;

    @Override
    public void attachView(@NonNull StaticRecipesListView view) {
        super.attachView(view);
        recipeCategory = view.getRecipeCategory();
        loadList();
    }

    @Override
    protected void loadList() {
        ifViewAttached(view -> {
            List<SimpleRecipe> recipes = view.getRecipeRepository().getAll(recipeCategory);
            sort(recipes);
            view.showRecipes(recipes);
        });
    }

    public void deleteRecipe(int listPos, SimpleRecipe simpleRecipe) {
        ifViewAttached(view -> {
            if (view.getRecipeRepository().delete(simpleRecipe.getId())) {
                // если был удален из БД
                // обновляем список
                loadList();
            }
        });
    }

    @Override
    public void onListItemUpdateEvent(RecipeUpdateAction action) {
        super.onListItemUpdateEvent(action);
        // обновляем список при получении события о изменении/удалении/добавлении элемента списка
        loadList();
    }
}
