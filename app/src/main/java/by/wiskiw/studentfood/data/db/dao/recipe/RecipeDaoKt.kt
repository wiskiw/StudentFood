package by.wiskiw.studentfood.data.db.dao.recipe

import android.content.Context
import by.wiskiw.studentfood.data.db.DatabaseHolder
import by.wiskiw.studentfood.data.db.Response
import by.wiskiw.studentfood.data.db.dummy.DummyRecipeReader
import by.wiskiw.studentfood.data.db.exception.RecordByIdNotFound
import by.wiskiw.studentfood.mvp.model.SimpleRecipe
import io.paperdb.Book

object RecipeDaoKt : RecipeDao {

    private const val TAG_RECIPES = "recipes"
    private const val ALWAYS_REREAD_DUMMY = true

    private val book: Book = DatabaseHolder.getDatabase()

    private var recipeSet: MutableSet<SimpleRecipe>? = null

    override fun getAll(context: Context) = getSet(context).toList()

    private fun getSet(context: Context): MutableSet<SimpleRecipe> {
        recipeSet?.let {
            return it
        } ?: run {
            recipeSet = RecipeDaoKt.book.read<MutableSet<SimpleRecipe>>(
                    RecipeDaoKt.TAG_RECIPES, HashSet())
            if (ALWAYS_REREAD_DUMMY || recipeSet?.isEmpty() != false) {
                val dummyRecipeReader = DummyRecipeReader(context)
                recipeSet = dummyRecipeReader.read()
            }
            return recipeSet!!
        }
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


    private fun deleteAll(context: Context, filter: (recipe: SimpleRecipe) -> Boolean): IntArray {
        val toRemove = getSet(context).filter(filter)
        val numberRemoved = toRemove.size
        if (numberRemoved > 0) {
            getSet(context).removeAll(toRemove)
            saveAll(context)
        }
        return toRemove.map { it.id }.toIntArray()
    }

    override fun deleteAll(context: Context): IntArray {
        return deleteAll(context) { true }
    }

    override fun deleteAllMy(context: Context): IntArray {
        return deleteAll(context) { recipe -> recipe.isMine }
    }

    override fun deleteAllFavorites(context: Context): IntArray {
        return deleteAll(context) { recipe -> recipe.isFavorite }
    }
}