package by.wiskiw.studentfood.data.db.repository

import android.content.Context
import by.wiskiw.studentfood.data.db.dao.favorite.FavoriteRecipeIndexesDaoJava
import by.wiskiw.studentfood.data.db.dao.recipe.RecipeDaoKt
import by.wiskiw.studentfood.mvp.model.SimpleRecipe

class FavoriteRecipeRepositoryKt(private val context: Context) {

    private val recipeDao = RecipeDaoKt
    private val favoriteRecipeIndexesDao = FavoriteRecipeIndexesDaoJava.getInstance()

    public fun getAllFavorites(sortBy: (recipe: SimpleRecipe) -> String): List<SimpleRecipe> {
        return getAllFavorites()
                .asSequence()
                .sortedBy { sortBy.invoke(it) }
                .toList()
    }

    public fun getAllFavorites(): List<SimpleRecipe> {
        val idList = favoriteRecipeIndexesDao.getIds(context)
        return recipeDao.getAll(context).filter { idList.contains(it.id) }
    }

    public fun putToFavorites(recipeId: Int) {
        favoriteRecipeIndexesDao.putId(context, recipeId)
    }

    public fun removeFromFavorites(recipeId: Int) {
        favoriteRecipeIndexesDao.removeId(context, recipeId)
    }

    public fun delete(recipeId: Int) {
        removeFromFavorites(recipeId)
        recipeDao.delete(context, recipeId)
    }

    public fun isInFavorite(recipeId: Int): Boolean {
        return favoriteRecipeIndexesDao.getIds(context).contains(recipeId)
    }


}