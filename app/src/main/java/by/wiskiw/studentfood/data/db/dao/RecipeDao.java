package by.wiskiw.studentfood.data.db.dao;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import by.wiskiw.studentfood.data.db.Response;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface RecipeDao {

    @NonNull
    List<SimpleRecipe> getAll(@NonNull Context context);

    @NonNull
    Response<SimpleRecipe> get(@NonNull Context context, int id);

    @NonNull
    Response<Boolean> delete(@NonNull Context context, int id);

    void save(Context context, SimpleRecipe simpleRecipe);

    int getNextId(@NonNull Context context);

    public int[] deleteAllMy(@NonNull Context context);

    public int[] deleteAllFavorites(@NonNull Context context);

    public int[] deleteAll(@NonNull Context context);

}
