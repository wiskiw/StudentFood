package by.wiskiw.studentfood.ui.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;

public class ListActivity extends AppCompatActivity {

    private static final String INTENT_TAG_RECIPE_CAT = "rec-cat";

    public static void putParams(Intent intent, RecipeCategory recipeCategory) {
        if (intent != null) {
            intent.putExtra(INTENT_TAG_RECIPE_CAT, recipeCategory);
        }
    }

    protected RecipeCategory getRecipeCategory(Intent args) {
        return args.getParcelableExtra(INTENT_TAG_RECIPE_CAT);
    }

}
