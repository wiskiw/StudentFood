package by.wiskiw.studentfood.mvp.view.list;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface RecipesListView extends MvpView {

    void showRecipes(List<SimpleRecipe> recipes);

    RecipesRepositoryKt getRecipeRepository();

}
