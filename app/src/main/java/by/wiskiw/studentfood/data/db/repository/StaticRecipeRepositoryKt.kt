package by.wiskiw.studentfood.data.db.repository

import android.content.Context
import android.support.annotation.NonNull
import by.wiskiw.studentfood.data.db.Response
import by.wiskiw.studentfood.data.db.dao.recipe.RecipeDaoKt
import by.wiskiw.studentfood.mvp.model.RecipeGroup
import by.wiskiw.studentfood.mvp.model.SimpleRecipe

class StaticRecipeRepositoryKt(val context: Context) {

    private val recipeDao = RecipeDaoKt

    @NonNull
    public fun getAll(group: RecipeGroup? = null,
                      sortBy: (recipe: SimpleRecipe) -> String): List<SimpleRecipe> {
        return getAll(group)
                .asSequence()
                .sortedBy { sortBy.invoke(it) }
                .toList()
    }

    @NonNull
    public fun getAll(group: RecipeGroup? = null): List<SimpleRecipe> {
        return recipeDao.getAll(context).filter { group == null || it.isIt(group) }
    }

    public fun save(simpleRecipe: SimpleRecipe) {
        recipeDao.save(context, simpleRecipe)
    }

    @NonNull
    public fun get(recipeId: Int): Response<SimpleRecipe> {
        return recipeDao.get(context, recipeId)
    }

}