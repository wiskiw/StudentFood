package by.wiskiw.studentfood.data.db.repository

import by.wiskiw.studentfood.data.db.DaoProvider
import by.wiskiw.studentfood.data.db.model.CookCategory
import by.wiskiw.studentfood.data.db.model.SimpleRecipe

object StaticRecipeRepositoryKt {

    private val dbProvider by lazy { DaoProvider.getInstance() }

    public fun getAll(category: CookCategory? = null,
                      sortBy: (recipe: SimpleRecipe) -> String): List<SimpleRecipe> {
        return dbProvider.recipeDao.all
                .asSequence()
                .filter { category == null || it.isIt(category) }
                .sortedBy { sortBy.invoke(it) }
                .toList()
    }

    public fun getAll(category: CookCategory? = null): List<SimpleRecipe> {
        return dbProvider.recipeDao.all.filter { category == null || it.isIt(category) }
    }

    public fun save(simpleRecipe: SimpleRecipe) {
        dbProvider.recipeDao.save(simpleRecipe)
    }

}