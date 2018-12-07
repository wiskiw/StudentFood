package by.wiskiw.studentfood.data.db.repository

import android.content.Context
import by.wiskiw.studentfood.data.db.dao.mine.MyRecipeIndexesDaoJava
import by.wiskiw.studentfood.data.db.dao.recipe.RecipeDaoKt
import by.wiskiw.studentfood.mvp.model.SimpleRecipe

class MyRecipeRepositoryKt(private val context: Context) {

    private val recipeDao = RecipeDaoKt
    private val myRecipeIndexesDao = MyRecipeIndexesDaoJava.getInstance()

    public fun getAllMine(sortBy: (recipe: SimpleRecipe) -> String): List<SimpleRecipe> {
        return getAllMine()
                .asSequence()
                .sortedBy { sortBy.invoke(it) }
                .toList()
    }

    public fun getAllMine(): List<SimpleRecipe> {
        val idList = myRecipeIndexesDao.ids
        return recipeDao.getAll(context)
                .filter { idList.contains(it.id) }
    }

    public fun saveToMy(simpleRecipe: SimpleRecipe) {
        myRecipeIndexesDao.putId(simpleRecipe.id)
        recipeDao.save(context, simpleRecipe)
    }

    public fun removeFromMy(recipeId: Int) {
        myRecipeIndexesDao.removeId(recipeId)
    }

    public fun delete(recipeId: Int): Boolean {
        removeFromMy(recipeId)
        return recipeDao.delete(context, recipeId).isOk
    }

}