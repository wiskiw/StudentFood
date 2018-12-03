package by.wiskiw.studentfood.data.db.dao.favorite;

import java.util.Set;

public interface FavoriteRecipeIndexesDao {

    Set<Integer> getIds();

    boolean putId(int id);

    boolean removeId(int id);

}
