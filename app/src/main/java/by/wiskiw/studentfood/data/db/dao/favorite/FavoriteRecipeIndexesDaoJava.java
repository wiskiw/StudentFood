package by.wiskiw.studentfood.data.db.dao.favorite;

import java.util.HashSet;
import java.util.Set;

import io.paperdb.Book;

public class FavoriteRecipeIndexesDaoJava implements FavoriteRecipeIndexesDao {

    private Book book;
    private static final String TAG_FAVORITE_RECIPES = "favorite-recipes";
    private Set<Integer> favoriteRecipeIds;

    public FavoriteRecipeIndexesDaoJava(Book book) {
        this.book = book;
    }

    @Override
    public Set<Integer> getIds() {
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
    public boolean putId(int id) {
        return onSetUpdate(getIds().add(id));
    }


    @Override
    public boolean removeId(int id) {
        return onSetUpdate(getIds().remove(id));
    }

    private boolean onSetUpdate(boolean r) {
        if (r) {
            saveAll();
        }
        return r;
    }
}
