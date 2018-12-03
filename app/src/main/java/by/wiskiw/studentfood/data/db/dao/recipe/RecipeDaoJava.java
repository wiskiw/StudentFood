package by.wiskiw.studentfood.data.db.dao.recipe;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import by.wiskiw.studentfood.data.db.Response;
import by.wiskiw.studentfood.data.db.exception.RecordByIdNotFound;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import io.paperdb.Book;

public class RecipeDaoJava implements RecipeDao {

    private static final String TAG_RECIPES = "recipes";

    private Book book;
    private Set<SimpleRecipe> recipeList;

    RecipeDaoJava(Book book) {
        this.book = book;
    }

    @Override
    @NonNull
    public List<SimpleRecipe> getAll() {
        return new ArrayList<>(getAllAsSet());
    }

    private Set<SimpleRecipe> getAllAsSet() {
        if (recipeList == null) {
            recipeList = book.read(TAG_RECIPES, new HashSet<>());
        }
        return recipeList;
    }

    private void saveAll() {
        if (recipeList != null) {
            book.write(TAG_RECIPES, recipeList);
        }
    }

    @Override
    @NonNull
    public Response<SimpleRecipe> get(int id) {
        Iterator<SimpleRecipe> iterator = getAllAsSet().iterator();
        SimpleRecipe simpleRecipe;
        while (iterator.hasNext()) {
            simpleRecipe = iterator.next();
            if (simpleRecipe.getId() == id) {
                return new Response<>(simpleRecipe);
            }
        }
        return new Response<>(new RecordByIdNotFound(id));
    }

    @Override
    @NonNull
    public Response<Boolean> delete(int id) {
        Iterator<SimpleRecipe> iterator = getAllAsSet().iterator();
        SimpleRecipe simpleRecipe;
        while (iterator.hasNext()) {
            simpleRecipe = iterator.next();
            if (simpleRecipe.getId() == id) {
                getAllAsSet().remove(simpleRecipe);
                saveAll();
                return new Response<>(true);
            }
        }
        return new Response<>(new RecordByIdNotFound(id));
    }

    @Override
    public void save(SimpleRecipe simpleRecipe) {
        delete(simpleRecipe.getId());
        getAllAsSet().add(simpleRecipe);
        saveAll();
    }

    @Override
    public int getNextId() {
        Iterator<SimpleRecipe> recipeIterator = getAllAsSet().iterator();

        int recipeAId;
        while (recipeIterator.hasNext()) {
            recipeAId = recipeIterator.next().getId();
            if (recipeIterator.hasNext()) {
                if (recipeIterator.next().getId() - recipeAId > 1) {
                    return recipeAId + 1;
                }
            } else {
                return recipeAId + 1;
            }
        }
        return 0;
    }
}
