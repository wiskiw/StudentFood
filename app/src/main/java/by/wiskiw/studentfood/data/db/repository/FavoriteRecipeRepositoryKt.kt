package by.wiskiw.studentfood.data.db.repository

import by.wiskiw.studentfood.data.db.DaoProvider
import by.wiskiw.studentfood.data.db.model.SimpleRecipe

object FavoriteRecipeRepositoryKt {

    private val dbProvider by lazy { DaoProvider.getInstance() }

    public fun getAllFavorites(sortBy: (recipe: SimpleRecipe) -> String): List<SimpleRecipe> {
        return getAllFavorites()
                .asSequence()
                .sortedBy { sortBy.invoke(it) }
                .toList()
    }

    public fun getAllFavorites(): List<SimpleRecipe> {
        val idList = dbProvider.favoriteRecipeIndexesDao.ids
        return dbProvider.recipeDao.all.filter { idList.contains(it.id) }
    }

    public fun putToFavorites(simpleRecipe: SimpleRecipe) {
        dbProvider.favoriteRecipeIndexesDao.putId(simpleRecipe.id)
        dbProvider.recipeDao.save(simpleRecipe)
    }

    public fun removeFromFavorites(recipeId: Int) {
        dbProvider.favoriteRecipeIndexesDao.removeId(recipeId)
    }

    public fun delete(recipeId: Int) {
        removeFromFavorites(recipeId)
        dbProvider.recipeDao.delete(recipeId)
    }


}