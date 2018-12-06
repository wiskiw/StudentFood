package by.wiskiw.studentfood.mvp.presenter.list;

import android.support.annotation.NonNull;

import java.util.List;

import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.model.RecipeGroup;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.list.StaticCategoryListView;

public class StaticCategoryListPresenter extends CategoryListPresenter<StaticCategoryListView> {

    @Override
    public void attachView(@NonNull StaticCategoryListView view) {
        super.attachView(view);

        RecipeCategory recipeCategory = view.getRecipeCategory();
        List<SimpleRecipe> recipes = view.getStaticRecipeRep().getAll(recipeCategory);
        view.showRecipes(recipes);
    }

}
