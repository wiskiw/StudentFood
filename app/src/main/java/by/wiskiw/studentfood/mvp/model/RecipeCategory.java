package by.wiskiw.studentfood.mvp.model;

import android.content.Context;

import by.wiskiw.studentfood.BuildConfig;
import by.wiskiw.studentfood.R;

/**
 * Катигории рецептов
 */
public enum RecipeCategory {

    CATEGORY_A,
    CATEGORY_B,
    CATEGORY_C;

    public String getCategoryTitle(Context context) {
        int strId = -1;
        switch (this) {
            case CATEGORY_A:
                strId = R.string.category_breakfast_title;
                break;
            case CATEGORY_B:
                strId = R.string.category_dinner_title;
                break;
            case CATEGORY_C:
                strId = R.string.category_fastfood_title;
                break;
            default:
                if (BuildConfig.DEBUG) {
                    throw new IllegalStateException("You must process all switch statements! '"
                            + this.name() + "' unknown.");
                }
        }
        return context.getResources().getString(strId);
    }

}
