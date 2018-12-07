package by.wiskiw.studentfood.mvp.presenter.list;

import java.util.List;

import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.list.MyRecipesListView;

public class MyRecipesListPresenter extends RecipesListPresenter<MyRecipesListView> {

    @Override
    public void attachView(MyRecipesListView view) {
        super.attachView(view);

        loadList(view);
    }

    private void loadList(MyRecipesListView view) {
        List<SimpleRecipe> recipes = view.getRecipeRepository().getAllMine();
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


}
