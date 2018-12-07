package by.wiskiw.studentfood.data.db.repository

import android.content.Context
import android.support.annotation.NonNull
import by.wiskiw.studentfood.data.db.dao.RecipeDaoKt
import by.wiskiw.studentfood.mvp.model.RecipeCategory
import by.wiskiw.studentfood.mvp.model.SimpleRecipe

class RecipesRepositoryKt(val context: Context) {

    private val recipeDao = RecipeDaoKt

    @NonNull
    public fun getAll(category: RecipeCategory? = null): List<SimpleRecipe> {
        return recipeDao.getAll(context).filter { category == null || it.isIt(category) }
    }

    @NonNull
    public fun getAllMine(): List<SimpleRecipe> {
        return recipeDao.getAll(context)
                .filter { it.isMine }
    }

    @NonNull
    public fun getAllFavorite(): List<SimpleRecipe> {
        return recipeDao.getAll(context)
                .filter { it.isFavorite }
    }

    public fun save(simpleRecipe: SimpleRecipe) {
        recipeDao.save(context, simpleRecipe)
    }

    @NonNull
    public fun get(recipeId: Int): SimpleRecipe {
        val response = recipeDao.get(context, recipeId)
        return if (response.isOk) {
            response.data
        } else {
            SimpleRecipe.nullRecipe()
        }
    }

    public fun delete(recipeId: Int): Boolean {
        val response = recipeDao.delete(context, recipeId)
        return response.isOk
    }

    private fun removeFrom(recipeId: Int,
                           isMine: Boolean? = null,
                           isFavorite: Boolean? = null): Boolean {
        val response = recipeDao.get(context, recipeId)
        return if (response.isOk) {
            val recipe = response.data
            isMine?.let { recipe.isMine = it }
            isFavorite?.let { recipe.isFavorite = it }
            recipeDao.save(context, recipe)
            true
        } else {
            false
        }
    }

    public fun removeFromMine(recipeId: Int) = removeFrom(recipeId, isMine = false)

    public fun removeFromFavorite(recipeId: Int) = removeFrom(recipeId, isFavorite = false)

}