package by.wiskiw.studentfood.data.db.repository

import by.wiskiw.studentfood.data.db.DaoProvider
import by.wiskiw.studentfood.mvp.model.RecipeCategory
import by.wiskiw.studentfood.mvp.model.SimpleRecipe

object StaticRecipeRepositoryKt {

    private val dbProvider by lazy { DaoProvider.getInstance() }

    public fun getAll(category: RecipeCategory? = null,
                      sortBy: (recipe: SimpleRecipe) -> String): List<SimpleRecipe> {
        return dbProvider.recipeDao.all
                .asSequence()
                .filter { category == null || it.isIt(category) }
                .sortedBy { sortBy.invoke(it) }
                .toList()
    }

    public fun getAll(category: RecipeCategory? = null): List<SimpleRecipe> {
        return dbProvider.recipeDao.all.filter { category == null || it.isIt(category) }
    }

    public fun save(simpleRecipe: SimpleRecipe) {
        dbProvider.recipeDao.save(simpleRecipe)
    }

}