package by.wiskiw.studentfood.ui.activity.description;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.di.FoodApp;
import by.wiskiw.studentfood.mvp.model.CookStep;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.DescriptionPresenter;
import by.wiskiw.studentfood.mvp.view.DescriptionView;
import by.wiskiw.studentfood.ui.activity.FoodAppActivity;

public class DescriptionActivity extends FoodAppActivity<DescriptionView, DescriptionPresenter>
        implements DescriptionView {

    private static final String INTENT_TAG_RECIPE_ID = "recipe-id";
    private static final String INTENT_TAG_RECIPE_LIST_POSITION = "recipe-list-pos";


    /*
     Чтобы избежать context leaks memory создаем и храним репозиторий во вью (Activity)
     и предоставляем его Презентеру по требованию
     */
    private RecipesRepositoryKt recipesRepository;


    private LinearLayout cookingLevelsLl;

    private ImageView headerIv;
    private ImageView addToFavoriteBtn;
    private ImageView action2Iv;
    private ImageView action3Iv;

    private TextView descriptionTv;

    @NonNull
    @Override
    public DescriptionPresenter createPresenter() {
        return new DescriptionPresenter();
    }

    public static void putArgs(Intent intent, int recipeId, int listPosition) {
        intent.putExtra(INTENT_TAG_RECIPE_ID, recipeId);
        intent.putExtra(INTENT_TAG_RECIPE_LIST_POSITION, listPosition);
    }

    private void parseArgs() {
        Intent args = getIntent();
        int recipeId = args.getIntExtra(INTENT_TAG_RECIPE_ID, -1);
        int recipeListPos = args.getIntExtra(INTENT_TAG_RECIPE_LIST_POSITION, -1);
        if (recipeId < 0 || recipeListPos < 0) {
            Exception ex = new IllegalArgumentException("You must pass recipe Id and recipe list position " +
                    "to DescriptionActivity!");
            Log.e(FoodApp.LOG_TAG, "Wrong activity args:" +
                            " recipeId " + recipeId
                            + "; recipeListPos " + recipeListPos
                            + "; recipeGroup " + recipeListPos
                    , ex);
            finish();
        } else {
            presenter.setRecipeData(recipeId, recipeListPos);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        parseArgs();

        cookingLevelsLl = findViewById(R.id.linear_layout_cooking_levels);
        headerIv = findViewById(R.id.image_view_header);

        addToFavoriteBtn = findViewById(R.id.image_view_add_to_favorite);
        addToFavoriteBtn.setOnClickListener(v -> presenter.clickAddToFavorite());

        action2Iv = findViewById(R.id.image_view_action_2);
        action3Iv = findViewById(R.id.image_view_action_3);

        descriptionTv = findViewById(R.id.text_view_description);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupToolbarTitle(@NotNull String title) {
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    private void setDescription(@NotNull String description) {
        descriptionTv.setText(description);
    }

    private void addCookStep(CookStep cookStep) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemView = inflater.inflate(R.layout.content_cook_step, cookingLevelsLl, false);

        TextView textTv = itemView.findViewById(R.id.text_view_text);
        TextView timeTv = itemView.findViewById(R.id.text_view_time);

        textTv.setText(cookStep.getText());
        timeTv.setText(cookStep.getTimeString());

        cookingLevelsLl.addView(itemView);
    }

    @Override
    public void showRecipe(SimpleRecipe recipe) {
        setupToolbarTitle(recipe.getTitle());
        setDescription(recipe.getDescription());
        recipe.getSteps().forEach(this::addCookStep);
    }

    @Override
    public void showRecipeNotFound(int recipeId) {
        // todo showRecipeNotFound
    }

    @Override
    public RecipesRepositoryKt getRecipesRepository() {
        if (recipesRepository == null) {
            recipesRepository = new RecipesRepositoryKt(this);
        }
        return recipesRepository;
    }
}
