package by.wiskiw.studentfood.mvp.presenter.list;

import java.util.List;

import by.wiskiw.studentfood.di.bus.RecipeUpdateAction;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.list.MyRecipesListView;

public class MyRecipesListPresenter extends RecipesListPresenter<MyRecipesListView> {

    @Override
    public void attachView(MyRecipesListView view) {
        super.attachView(view);

        loadList();
    }

    @Override
    protected void loadList() {
        ifViewAttached(view -> {
            List<SimpleRecipe> recipes = view.getRecipeRepository().getAllMine();
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

    @Override
    public void onListItemUpdateEvent(RecipeUpdateAction action) {
        super.onListItemUpdateEvent(action);
        // обновляем список при получении события о изменении/удалении/добавлении элемента списка
        loadList();
    }

}
