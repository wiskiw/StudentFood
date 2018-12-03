package by.wiskiw.studentfood.data.db.repository

import by.wiskiw.studentfood.data.db.DaoProvider
import by.wiskiw.studentfood.data.db.model.SimpleRecipe

object MyRecipeRepositoryKt {

    private val dbProvider by lazy { DaoProvider.getInstance() }

    public fun getAllMine(sortBy: (recipe: SimpleRecipe) -> String): List<SimpleRecipe> {
        return getAllMine()
                .asSequence()
                .sortedBy { sortBy.invoke(it) }
                .toList()
    }

    public fun getAllMine(): List<SimpleRecipe> {
        val idList = dbProvider.myRecipeIndexesDao.ids
        return dbProvider.recipeDao.all.filter { idList.contains(it.id) }
    }

    public fun saveToMy(simpleRecipe: SimpleRecipe) {
        dbProvider.myRecipeIndexesDao.putId(simpleRecipe.id)
        dbProvider.recipeDao.save(simpleRecipe)
    }

    public fun removeFromMy(recipeId: Int) {
        dbProvider.myRecipeIndexesDao.removeId(recipeId)
    }

    public fun delete(recipeId: Int) {
        removeFromMy(recipeId)
        dbProvider.recipeDao.delete(recipeId)
    }


}