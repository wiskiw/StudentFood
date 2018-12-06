package by.wiskiw.studentfood.data.db.dao.recipe;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import by.wiskiw.studentfood.data.db.DatabaseHolder;
import by.wiskiw.studentfood.data.db.Response;
import by.wiskiw.studentfood.data.db.dummy.DummyRecipeReader;
import by.wiskiw.studentfood.data.db.exception.RecordByIdNotFound;
import by.wiskiw.studentfood.mvp.model.RecipeGroup;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import io.paperdb.Book;

public class RecipeDaoJava implements RecipeDao {

    private static final String TAG_RECIPES = "recipes";
    private static RecipeDaoJava instance;

    private Book book;
    private Set<SimpleRecipe> recipeList;

    public static RecipeDaoJava getInstance() {
        if (instance == null) {
            instance = new RecipeDaoJava();
        }
        return instance;
    }

    private RecipeDaoJava() {
        this.book = DatabaseHolder.getDatabase();
    }

    @Override
    @NonNull
    public List<SimpleRecipe> getAll(Context context) {
        return new ArrayList<>(getAllAsSet(context));
    }

    private Set<SimpleRecipe> getAllAsSet(Context context) {
        if (recipeList == null) {
            recipeList = book.read(TAG_RECIPES, new HashSet<>());
            if (recipeList.isEmpty()) {
                DummyRecipeReader dummyRecipeReader = new DummyRecipeReader(context);
                recipeList = dummyRecipeReader.read();
            }
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
    public Response<SimpleRecipe> get(Context context, int id) {
        Iterator<SimpleRecipe> iterator = getAllAsSet(context).iterator();
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
    public Response<Boolean> delete(Context context, int id) {
        Iterator<SimpleRecipe> iterator = getAllAsSet(context).iterator();
        SimpleRecipe simpleRecipe;
        while (iterator.hasNext()) {
            simpleRecipe = iterator.next();
            if (simpleRecipe.getId() == id) {
                getAllAsSet(context).remove(simpleRecipe);
                saveAll();
                return new Response<>(true);
            }
        }
        return new Response<>(new RecordByIdNotFound(id));
    }

    @Override
    public void save(Context context, SimpleRecipe simpleRecipe) {
        delete(context, simpleRecipe.getId());
        getAllAsSet(context).add(simpleRecipe);
        saveAll();
    }

    @Override
    public int getNextId(@NonNull Context context) {
        Iterator<SimpleRecipe> recipeIterator = getAllAsSet(context).iterator();

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

    @Override
    public int[] deleteAllMy(@NonNull Context context) {
        List<SimpleRecipe> toRemove = getAll(context)
                .stream()
                .filter(SimpleRecipe::isMine)
                .collect(Collectors.toList());

        int numberRemoved = toRemove.size();
        if (numberRemoved > 0) {
            getAll(context).removeAll(toRemove);
            saveAll();
        }
        return toRemove.stream().mapToInt(SimpleRecipe::getId).toArray();
    }

    @Override
    public int[] deleteAllFavorites(@NonNull Context context) {
        List<SimpleRecipe> toRemove = getAll(context)
                .stream()
                .filter(SimpleRecipe::isFavorite)
                .collect(Collectors.toList());

        int numberRemoved = toRemove.size();
        if (numberRemoved > 0) {
            getAll(context).removeAll(toRemove);
            saveAll();
        }
        return toRemove.stream().mapToInt(SimpleRecipe::getId).toArray();
    }

    @Override
    public int[] deleteAll(@NonNull Context context) {
        List<SimpleRecipe> toRemove = getAll(context);

        int numberRemoved = toRemove.size();
        if (numberRemoved > 0) {
            getAll(context).removeAll(toRemove);
            saveAll();
        }
        return toRemove.stream().mapToInt(SimpleRecipe::getId).toArray();
    }
}
