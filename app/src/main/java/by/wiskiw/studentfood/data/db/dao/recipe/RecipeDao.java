package by.wiskiw.studentfood.data.db.dao.recipe;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import by.wiskiw.studentfood.data.db.Response;
import by.wiskiw.studentfood.mvp.model.RecipeGroup;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface RecipeDao {

    @NonNull
    List<SimpleRecipe> getAll(Context context);

    @NonNull
    Response<SimpleRecipe> get(Context context, int id);

    @NonNull
    Response<Boolean> delete(Context context, int id);

    void save(Context context, SimpleRecipe simpleRecipe);

    int getNextId(Context context);

    int[] deleteAll(Context context, RecipeGroup[] recipeGroup);

}
