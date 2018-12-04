package by.wiskiw.studentfood.data.db.dao.recipe

import android.content.Context
import by.wiskiw.studentfood.data.db.DatabaseHolder
import by.wiskiw.studentfood.data.db.Response
import by.wiskiw.studentfood.data.db.dummy.DummyRecipeReader
import by.wiskiw.studentfood.data.db.exception.RecordByIdNotFound
import by.wiskiw.studentfood.mvp.model.RecipeGroup
import by.wiskiw.studentfood.mvp.model.SimpleRecipe
import io.paperdb.Book

object RecipeDaoKt : RecipeDao {

    private const val TAG_RECIPES = "recipes"

    private val book: Book = DatabaseHolder.getDatabase()

    private lateinit var recipeSet: MutableSet<SimpleRecipe>

    override fun getAll(context: Context) = getSet(context).toList()

    private fun getSet(context: Context): MutableSet<SimpleRecipe> {
        recipeSet = RecipeDaoKt.book.read<MutableSet<SimpleRecipe>>(
                RecipeDaoKt.TAG_RECIPES, HashSet()).let {
            if (it.isEmpty()) {
                val dummyRecipeReader = DummyRecipeReader(context)
                dummyRecipeReader.read()
            } else {
                it
            }
        }
        return recipeSet
    }

    private fun saveAll(context: Context) {
        book.write(TAG_RECIPES, getSet(context))
    }

    override operator fun get(context: Context, id: Int): Response<SimpleRecipe> {
        return getSet(context).firstOrNull { it.id == id }
                ?.let { Response(it) } ?: Response(RecordByIdNotFound(id))
    }

    override fun delete(context: Context, id: Int): Response<Boolean> {
        return if (getSet(context).removeIf { it.id == id }) {
            saveAll(context)
            Response(true)
        } else Response(false)
    }

    override fun save(context: Context, simpleRecipe: SimpleRecipe) {
        getSet(context).removeIf { it.id == simpleRecipe.id }
        getSet(context).add(simpleRecipe)
        saveAll(context)
    }

    override fun getNextId(context: Context): Int {
        val recipeIterator = getAll(context).iterator()

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


    override fun deleteAll(context: Context, vararg recipeGroup: RecipeGroup): IntArray {
        val toRemove = getSet(context).filter { it.isIt(recipeGroup) }
        val numberRemoved = toRemove.size
        if (numberRemoved > 0) {
            getSet(context).removeAll(toRemove)
            saveAll(context)
        }
        return toRemove.map { it.id }.toIntArray()
    }

}