package by.wiskiw.studentfood.data.db.dao.mine;

import java.util.HashSet;
import java.util.Set;

import io.paperdb.Book;

public class MyRecipeIndexesDaoJava implements MyRecipeIndexesDao {

    private Book book;
    private static final String TAG_MY_RECIPES = "my-recipes";
    private Set<Integer> myRecipeIds;

    public MyRecipeIndexesDaoJava(Book book) {
        this.book = book;
    }

    @Override
    public Set<Integer> getIds() {
        if (myRecipeIds == null) {
            myRecipeIds = book.read(TAG_MY_RECIPES, new HashSet<>());
        }
        return myRecipeIds;
    }

    private void saveAll() {
        if (myRecipeIds != null) {
            book.write(TAG_MY_RECIPES, myRecipeIds);
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
