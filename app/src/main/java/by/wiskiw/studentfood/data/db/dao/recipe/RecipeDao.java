package by.wiskiw.studentfood.data.db.dao.recipe;

import android.support.annotation.NonNull;

import java.util.List;

import by.wiskiw.studentfood.data.db.Response;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface RecipeDao {

    @NonNull
    List<SimpleRecipe> getAll();

    @NonNull
    Response<SimpleRecipe> get(int id);

    @NonNull
    Response<Boolean> delete(int id);

    void save(SimpleRecipe simpleRecipe);

    int getNextId();

}
