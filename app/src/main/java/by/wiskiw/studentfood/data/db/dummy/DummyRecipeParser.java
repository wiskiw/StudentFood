package by.wiskiw.studentfood.data.db.dummy;

import android.support.annotation.NonNull;
import android.util.AndroidRuntimeException;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashSet;
import java.util.Set;

import by.wiskiw.studentfood.BuildConfig;
import by.wiskiw.studentfood.di.FoodApp;
import by.wiskiw.studentfood.mvp.model.CookStep;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

class DummyRecipeParser {

    private static final String XML_TAG_RECIPE = "recipe";
    private static final String XML_TAG_COOK_STEP = "cookStep";
    private static final String XML_TAG_CATEGORY = "category";

    private Set<SimpleRecipe> recipeSet = new HashSet<>();

    @NonNull
    Set<SimpleRecipe> getRecipeSet() {
        return recipeSet;
    }

    private int parseInt(String intStr) {
        return Integer.parseInt(intStr);
    }

    private long parseLong(String longStr) {
        return Long.parseLong(longStr);
    }

    private RecipeCategory parseRecipeCategory(String strValue) {
        return RecipeCategory.valueOf(strValue.trim().toUpperCase());
    }

    boolean parse(XmlPullParser xpp) {
        boolean resultStatus = true;

        SimpleRecipe simpleRecipe = null;
        CookStep cookStep = null;

        String textValue = "";
        String tagName;

        try {
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        switch (tagName) {
                            case XML_TAG_RECIPE:
                                simpleRecipe = new SimpleRecipe(
                                        parseInt(xpp.getAttributeValue(null, "id")));
                                break;
                            case XML_TAG_COOK_STEP:
                                cookStep = new CookStep();
                                break;
                        }

                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (simpleRecipe != null) {
                            processTagCloseSimpleRecipe(simpleRecipe, tagName, textValue);
                            processTagCloseCookStep(simpleRecipe, cookStep, tagName, textValue);
                            processTagCloseCategory(simpleRecipe, tagName, textValue);
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        } catch (Exception ex) {
            resultStatus = false;
            if (BuildConfig.DEBUG) {
                Log.e(FoodApp.LOG_TAG, "Dummy recipes parsing exception!", ex);
            }
        }
        return resultStatus;
    }

    private void processTagCloseSimpleRecipe(SimpleRecipe recipe, String tagName, String textValue) {
        switch (tagName) {
            case XML_TAG_RECIPE:
                // рецепт закончился
                checkForClone(recipe);
                recipeSet.add(recipe);
                break;
            case "title":
                recipe.setTitle(textValue);
                break;
            case "description":
                recipe.setDescription(textValue);
                break;
            case "mine":
                recipe.setMine(Boolean.valueOf(textValue));
                break;
            case "favorite":
                recipe.setFavorite(Boolean.valueOf(textValue));
                break;
            case "image":
                recipe.setHeaderImageFileName(textValue);
                break;
        }
    }

    private void processTagCloseCategory(SimpleRecipe recipe, String tagName, String textValue) {
        switch (tagName) {
            case XML_TAG_CATEGORY:
                recipe.addCategory(parseRecipeCategory(textValue));
                break;
        }
    }

    private void processTagCloseCookStep(SimpleRecipe recipe, CookStep cookStep, String tagName, String textValue) {
        if (cookStep != null) {
            switch (tagName) {
                case XML_TAG_COOK_STEP:
                    // cookStep закрыт
                    recipe.addStep(cookStep);
                    break;
                case "text":
                    cookStep.setText(textValue);
                    break;
                case "timeLong":
                    cookStep.setTime(parseLong(textValue));
                    break;
            }
        }
    }

    private void checkForClone(SimpleRecipe simpleRecipe) {
        for (SimpleRecipe recipe : recipeSet) {
            if (recipe.getId() == simpleRecipe.getId()) {
                throw new AndroidRuntimeException("Dummy xml should not contains Recipes with same. " +
                        "Found at least two Recipes with id: " + simpleRecipe.getId());
            }
        }
    }

}
