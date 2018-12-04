package by.wiskiw.studentfood.data.db.dao.favorite;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

import by.wiskiw.studentfood.data.db.DatabaseHolder;
import io.paperdb.Book;

public class FavoriteRecipeIndexesDaoJava implements FavoriteRecipeIndexesDao {

    private static FavoriteRecipeIndexesDaoJava instance;

    private Book book;
    private static final String TAG_FAVORITE_RECIPES = "favorite-recipes";
    private Set<Integer> favoriteRecipeIds;

    public static FavoriteRecipeIndexesDaoJava getInstance() {
        if (instance == null) {
            instance = new FavoriteRecipeIndexesDaoJava();
        }
        return instance;
    }

    private FavoriteRecipeIndexesDaoJava() {
        this.book = DatabaseHolder.getDatabase();
    }

    @Override
    public Set<Integer> getIds(Context context) {
        if (favoriteRecipeIds == null) {
            favoriteRecipeIds = book.read(TAG_FAVORITE_RECIPES, new HashSet<>());
        }
        return favoriteRecipeIds;
    }

    private void saveAll() {
        if (favoriteRecipeIds != null) {
            book.write(TAG_FAVORITE_RECIPES, favoriteRecipeIds);
        }
    }

    @Override
    public boolean putId(Context context, int id) {
        return onSetUpdate(getIds(context).add(id));
    }


    @Override
    public boolean removeId(Context context, int id) {
        return onSetUpdate(getIds(context).remove(id));
    }

    private boolean onSetUpdate(boolean r) {
        if (r) {
            saveAll();
        }
        return r;
    }
}
