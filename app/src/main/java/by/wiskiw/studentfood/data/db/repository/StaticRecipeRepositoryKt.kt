package by.wiskiw.studentfood.data.db.repository

import android.support.annotation.NonNull
import by.wiskiw.studentfood.data.db.DaoProvider
import by.wiskiw.studentfood.data.db.Response
import by.wiskiw.studentfood.mvp.model.RecipeGroup
import by.wiskiw.studentfood.mvp.model.SimpleRecipe

object StaticRecipeRepositoryKt {

    private val dbProvider by lazy { DaoProvider.getInstance() }

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
        return dbProvider.recipeDao.all.filter { group == null || it.isIt(group) }
    }

    public fun save(simpleRecipe: SimpleRecipe) {
        dbProvider.recipeDao.save(simpleRecipe)
    }

    @NonNull
    public fun get(recipeId: Int): Response<SimpleRecipe> {
        return dbProvider.recipeDao.get(recipeId)
    }

}