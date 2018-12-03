package by.wiskiw.studentfood.data.db.dao.recipe;

import java.util.List;

import by.wiskiw.studentfood.data.db.Response;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public interface RecipeDao {

    List<SimpleRecipe> getAll();

    Response<SimpleRecipe> get(int id);

    Response<Boolean> delete(int id);

    void save(SimpleRecipe simpleRecipe);

    int getNextId();

}
