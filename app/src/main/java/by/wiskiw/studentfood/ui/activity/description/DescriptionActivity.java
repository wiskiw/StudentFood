package by.wiskiw.studentfood.ui.activity.description;

import android.content.Intent;
import android.graphics.Bitmap;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import by.wiskiw.studentfood.BuildConfig;
import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.data.image.RecipeImageFileManager;
import by.wiskiw.studentfood.di.FoodApp;
import by.wiskiw.studentfood.di.bus.RecipeUpdateAction;
import by.wiskiw.studentfood.mvp.model.CookStep;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.DescriptionPresenter;
import by.wiskiw.studentfood.mvp.view.DescriptionView;
import by.wiskiw.studentfood.ui.activity.FoodAppActivity;
import by.wiskiw.studentfood.ui.activity.create.edit.CreateEditActivity;

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
    private ImageView editRecipeBtn;
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
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_description);
        parseArgs();

        cookingLevelsLl = findViewById(R.id.linear_layout_cooking_levels);
        headerIv = findViewById(R.id.image_view_header);

        descriptionTv = findViewById(R.id.text_view_description);

        setupToolbar();
        setupButtons();
        getPresenter().loadRecipe(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setupButtons() {
        addToFavoriteBtn = findViewById(R.id.image_view_add_to_favorite);
        addToFavoriteBtn.setOnClickListener(v -> presenter.clickAddToFavorite());

        editRecipeBtn = findViewById(R.id.image_view_edit);
        editRecipeBtn.setOnClickListener(v -> presenter.clickEditRecipe());

        action3Iv = findViewById(R.id.image_view_action_3);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableToolbarBackArror(true);
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
        View itemView = inflater.inflate(R.layout.cook_step_item, cookingLevelsLl, false);

        TextView textTv = itemView.findViewById(R.id.text_view_text);
        TextView timeTv = itemView.findViewById(R.id.text_view_time);
        View timeContainer = itemView.findViewById(R.id.container_time);

        textTv.setText(cookStep.getText());

        if (cookStep.getTime() > 0) {
            timeTv.setText(cookStep.getTimeString());
        } else {
            timeContainer.setVisibility(View.GONE);
        }

        cookingLevelsLl.addView(itemView);
    }

    @Override
    public void showRecipe(SimpleRecipe recipe) {
        setupToolbarTitle(recipe.getTitle());
        setDescription(recipe.getDescription());
        recipe.getSteps().forEach(this::addCookStep);

        RecipeImageFileManager recipeImageFm = new RecipeImageFileManager(this);
        Bitmap bitmap = recipeImageFm.getImageBitmapByName(recipe.getHeaderImageFileName());
        if (bitmap != null) {
            headerIv.setImageBitmap(bitmap);
        }

        showFavoriteButtonMarked(recipe.isFavorite());

        boolean isEditAllow = recipe.isMine();
        editRecipeBtn.setEnabled(isEditAllow);
        editRecipeBtn.setAlpha(isEditAllow ? 1f : 0.5f);
    }

    @Override
    public void showFavoriteButtonMarked(boolean isMarked) {
        if (isMarked) {
            addToFavoriteBtn.setImageResource(R.drawable.ic_star_white_24dp);
        } else {
            addToFavoriteBtn.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }

    @Override
    public void showRecipeNotFound(int recipeId) {
        if (BuildConfig.DEBUG) {
            throw new IllegalStateException("Description activity was launched with incorrect recipe id: " + recipeId);
        } else {
            this.finish();
        }
    }

    @Override
    public RecipesRepositoryKt getRecipesRepository() {
        if (recipesRepository == null) {
            recipesRepository = new RecipesRepositoryKt(this);
        }
        return recipesRepository;
    }

    @Override
    public void startEditActivity(int recipeId) {
        Intent launchIntent = new Intent(this, CreateEditActivity.class);
        CreateEditActivity.putRecipeId(launchIntent, recipeId);
        startActivity(launchIntent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemUpdateEvent(RecipeUpdateAction action) {
        presenter.onItemBeenUpdate();
    }
}
