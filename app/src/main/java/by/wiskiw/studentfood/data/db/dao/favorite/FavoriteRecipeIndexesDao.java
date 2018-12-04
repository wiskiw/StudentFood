package by.wiskiw.studentfood.data.db.dao.favorite;

import android.content.Context;

import java.util.Set;

public interface FavoriteRecipeIndexesDao {

    Set<Integer> getIds(Context context);

    boolean putId(Context context, int id);

    boolean removeId(Context context, int id);

}
