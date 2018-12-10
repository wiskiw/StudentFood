package by.wiskiw.studentfood.mvp.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface CreateEditView extends MvpView {

    RecipesRepositoryKt getRecipeRepository();

    void notifyListItemUpdate();

    void showRecipe(SimpleRecipe recipe);

}

