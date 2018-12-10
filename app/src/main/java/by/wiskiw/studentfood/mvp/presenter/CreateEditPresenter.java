package by.wiskiw.studentfood.mvp.presenter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.di.FoodApp;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.view.CreateEditView;

public class CreateEditPresenter extends MvpBasePresenter<CreateEditView> {

    @Nullable
    private SimpleRecipe editRecipe;

    public void initRecipe(int id) {
        ifViewAttached(view -> {
            RecipesRepositoryKt rep = view.getRecipeRepository();
            if (id >= 0) {
                editRecipe = rep.get(id);
            } else {
                editRecipe = rep.getNew();
            }
            Log.d(FoodApp.LOG_TAG, "editRecipe: " + editRecipe.toString());
        });
    }

    @Nullable
    public SimpleRecipe getRecipe() {
        return this.editRecipe;
    }

    public void saveRecipe() {
        if (editRecipe != null) {
            ifViewAttached(view -> {
                view.getRecipeRepository().save(editRecipe);
                view.notifyListItemUpdate();
            });
        }
    }


}
