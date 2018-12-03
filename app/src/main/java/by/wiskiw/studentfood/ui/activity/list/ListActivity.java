package by.wiskiw.studentfood.ui.activity.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import by.wiskiw.studentfood.mvp.model.RecipeCategory;

public abstract class ListActivity extends AppCompatActivity {

    private static final String INTENT_TAG_RECIPE_CAT = "recipe-category";

    public static void putParams(Intent intent, RecipeCategory category) {
        if (intent != null) {
            intent.putExtra(INTENT_TAG_RECIPE_CAT, category);
        }
    }

    protected RecipeCategory getRecipeCategory(Intent args) {
        return (RecipeCategory) args.getSerializableExtra(INTENT_TAG_RECIPE_CAT);
    }

}
