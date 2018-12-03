package by.wiskiw.studentfood.data.db;

import by.wiskiw.studentfood.data.db.dao.favorite.FavoriteRecipeIndexesDao;
import by.wiskiw.studentfood.data.db.dao.favorite.FavoriteRecipeIndexesDaoJava;
import by.wiskiw.studentfood.data.db.dao.mine.MyRecipeIndexesDao;
import by.wiskiw.studentfood.data.db.dao.mine.MyRecipeIndexesDaoJava;
import by.wiskiw.studentfood.data.db.dao.recipe.RecipeDao;
import by.wiskiw.studentfood.data.db.dao.recipe.RecipeDaoKt;
import io.paperdb.Book;
import io.paperdb.Paper;

public class DaoProvider {

    private static final String BOOK_NAME_RECIPE = "recipes-database";
    private static DaoProvider instance;

    private Book paperBook = Paper.book(BOOK_NAME_RECIPE);

    public static DaoProvider getInstance() {
        if (instance == null) {
            instance = new DaoProvider();
        }
        return instance;
    }

    private RecipeDao recipeDao;
    private MyRecipeIndexesDao myRecipeIndexesDao;
    private FavoriteRecipeIndexesDao favoriteRecipeIndexesDao;

    public RecipeDao getRecipeDao() {
        if (recipeDao == null) {
            recipeDao = new RecipeDaoKt(paperBook);
        }
        return recipeDao;
    }

    public MyRecipeIndexesDao getMyRecipeIndexesDao() {
        if (myRecipeIndexesDao == null) {
            myRecipeIndexesDao = new MyRecipeIndexesDaoJava(paperBook);
        }
        return myRecipeIndexesDao;
    }

    public FavoriteRecipeIndexesDao getFavoriteRecipeIndexesDao() {
        if (favoriteRecipeIndexesDao == null) {
            favoriteRecipeIndexesDao = new FavoriteRecipeIndexesDaoJava(paperBook);
        }
        return favoriteRecipeIndexesDao;
    }

}
