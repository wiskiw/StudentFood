package by.wiskiw.studentfood.data.db.dao.recipe

import android.content.Context
import by.wiskiw.studentfood.data.db.Response
import by.wiskiw.studentfood.data.db.dao.dummy.DummyRecipeReader
import by.wiskiw.studentfood.data.db.exception.RecordByIdNotFound
import by.wiskiw.studentfood.mvp.model.RecipeGroup
import by.wiskiw.studentfood.mvp.model.SimpleRecipe
import io.paperdb.Book

class RecipeDaoKt(private val context: Context, private val book: Book) : RecipeDao {

    companion object {
        private const val TAG_RECIPES = "recipes"
    }

    private val recipeList: MutableSet<SimpleRecipe> by lazy {
        return@lazy book.read<MutableSet<SimpleRecipe>>(TAG_RECIPES, HashSet()).let {
            if (it.isEmpty()) {
                val dummyRecipeReader = DummyRecipeReader(context)
                dummyRecipeReader.read()
            } else {
                it
            }
        }
    }

    override fun getAll() = recipeList.toList()

    private fun saveAll() {
        book.write(TAG_RECIPES, recipeList)
    }

    override operator fun get(id: Int): Response<SimpleRecipe> {
        return recipeList.firstOrNull { it.id == id }
                ?.let { Response(it) } ?: Response(RecordByIdNotFound(id))
    }

    override fun delete(id: Int): Response<Boolean> {
        return if (recipeList.removeIf { it.id == id }) {
            saveAll()
            Response(true)
        } else Response(false)
    }

    override fun save(simpleRecipe: SimpleRecipe) {
        recipeList.removeIf { it.id == simpleRecipe.id }
        recipeList.add(simpleRecipe)
        saveAll()
    }

    override fun getNextId(): Int {
        val recipeIterator = all.iterator()

        var recipeAId: Int
        while (recipeIterator.hasNext()) {
            recipeAId = recipeIterator.next().id
            if (recipeIterator.hasNext()) {
                if (recipeIterator.next().id - recipeAId > 1) {
                    return recipeAId + 1
                }
            } else {
                return recipeAId + 1
            }
        }
        return 0
    }


    override fun deleteAll(vararg recipeGroup: RecipeGroup): IntArray {
        val toRemove = recipeList.filter { it.isIt(recipeGroup) }
        val numberRemoved = toRemove.size
        if (numberRemoved > 0) {
            recipeList.removeAll(toRemove)
            saveAll()
        }
        return toRemove.map { it.id }.toIntArray()
    }

}