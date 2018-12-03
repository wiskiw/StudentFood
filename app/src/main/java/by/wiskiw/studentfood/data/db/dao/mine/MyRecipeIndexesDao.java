package by.wiskiw.studentfood.data.db.dao.mine;

import java.util.Set;

public interface MyRecipeIndexesDao {

    Set<Integer> getIds();

    boolean putId(int id);

    boolean removeId(int id);

}
