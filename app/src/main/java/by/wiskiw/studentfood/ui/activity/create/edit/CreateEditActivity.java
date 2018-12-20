package by.wiskiw.studentfood.ui.activity.create.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.data.image.RecipeImageFileManager;
import by.wiskiw.studentfood.di.FoodApp;
import by.wiskiw.studentfood.di.bus.ListItemUpdateAction;
import by.wiskiw.studentfood.mvp.model.CookStep;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.CreateEditPresenter;
import by.wiskiw.studentfood.mvp.view.CreateEditView;
import by.wiskiw.studentfood.ui.activity.FoodAppActivity;
import by.wiskiw.studentfood.ui.adapter.cook.step.DragDropItemTouchHelperCallback;
import by.wiskiw.studentfood.ui.adapter.cook.step.SortCookStepListAdapter;

public class CreateEditActivity extends FoodAppActivity<CreateEditView, CreateEditPresenter>
        implements CreateEditView, SortCookStepListAdapter.ClickListener {

    private static final String INTENT_TAG_RECIPE_ID = "recipe-id";
    private static final String INTENT_TAG_RECIPE_LIST_POS = "list-pos";
    private RecipesRepositoryKt recipesRepository;

    private RecyclerView cookStepsRv;
    private ConstraintLayout chooseImageContainer;
    private EditText titleEt;
    private EditText descriptionEt;
    private TextView imagePathTv;
    private ImageView recipeIv;

    private int listPos;
    private SortCookStepListAdapter cookStepsAdapter;

    private RecipeImageFileManager recipeImageFm = new RecipeImageFileManager(this);


    public static void putRecipeId(@NonNull Intent launchIntent, int id) {
        launchIntent.putExtra(INTENT_TAG_RECIPE_ID, id);
    }

    public static void putListPos(@NonNull Intent launchIntent, int listPos) {
        launchIntent.putExtra(INTENT_TAG_RECIPE_LIST_POS, listPos);
    }

    private int parseRecipeId(@NonNull Intent launchIntent) {
        return launchIntent.getIntExtra(INTENT_TAG_RECIPE_ID, -1);
    }

    private int parseListPos(@NonNull Intent launchIntent) {
        return launchIntent.getIntExtra(INTENT_TAG_RECIPE_LIST_POS, -1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);
        enableToolbarBackArror(true);

        cookStepsRv = findViewById(R.id.cook_steps_view_recipes);
        chooseImageContainer = findViewById(R.id.container_choose_image);
        titleEt = findViewById(R.id.edit_text_title);
        descriptionEt = findViewById(R.id.edit_text_description);
        imagePathTv = findViewById(R.id.text_view_image_path);
        recipeIv = findViewById(R.id.image_view_choose_image);

        cookStepsRv.setLayoutManager(new LinearLayoutManager(this));
        cookStepsRv.setItemAnimator(new DefaultItemAnimator());
        cookStepsAdapter = new SortCookStepListAdapter();
        cookStepsAdapter.setClickListener(this);
        cookStepsRv.setAdapter(cookStepsAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            parseIntentArgs(intent);
        }

        setupImageChooseButton();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            Image image = ImagePicker.getFirstImageOrNull(data);
            imagePathTv.setText(image.getPath());

            File imageFile = recipeImageFm.saveImageToAppFolder(image);

            SimpleRecipe recipe = presenter.getRecipe();
            if (recipe != null && imageFile != null) {
                String imageFileName = imageFile.getName();
                recipe.setHeaderImageFileName(imageFileName);
                showHeaderRecipeImage(imageFileName);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_edit_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_action_save) {
            saveRecipe();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void parseIntentArgs(@NonNull Intent intent) {
        int recipeId = parseRecipeId(intent);
        presenter.initRecipe(recipeId);
        if (recipeId >= 0) {
            SimpleRecipe recipe = presenter.getRecipe();
            if (recipe != null) {
                setToolbarTitle(presenter.getRecipe().getTitle());
            }
            listPos = parseListPos(intent);
        }
    }

    @NonNull
    @Override
    public CreateEditPresenter createPresenter() {
        return new CreateEditPresenter();
    }

    @Override
    public RecipesRepositoryKt getRecipeRepository() {
        if (recipesRepository == null) {
            recipesRepository = new RecipesRepositoryKt(this);
        }
        return recipesRepository;
    }

    private void showHeaderRecipeImage(@Nullable String recipeImageFileName) {
        if (recipeImageFileName == null || recipeImageFileName.isEmpty()) return;

        Bitmap bitmap = recipeImageFm.getImageBitmapByName(recipeImageFileName);
        if (bitmap != null) {
            recipeIv.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showRecipe(SimpleRecipe recipe) {
        showHeaderRecipeImage(recipe.getHeaderImageFileName());

        titleEt.setText(recipe.getTitle());
        descriptionEt.setText(recipe.getDescription());

        cookStepsAdapter.setAll(recipe.getSteps());

        DragDropItemTouchHelperCallback callback =
                new DragDropItemTouchHelperCallback(cookStepsAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(cookStepsRv);

    }

    @Override
    public void notifyListItemUpdate() {
        Log.d(FoodApp.LOG_TAG, "notifyListItemUpdate");
        ListItemUpdateAction action;
        if (listPos >= 0) {
            action = new ListItemUpdateAction(listPos, ListItemUpdateAction.Type.UPDATE);
        } else {
            action = new ListItemUpdateAction(ListItemUpdateAction.Type.ADDED);
        }
        EventBus.getDefault().post(action);
    }

    private void saveRecipe() {
        SimpleRecipe recipe = presenter.getRecipe();
        if (recipe != null) {
            recipe.setTitle(titleEt.getText().toString().trim());
            recipe.setDescription(descriptionEt.getText().toString().trim());

            // записываем этапы готовки
            List<CookStep> steps = recipe.getSteps();
            steps.clear();
            steps.addAll(cookStepsAdapter.getList());

            presenter.saveRecipe();
            finish();
        }
    }

    private void setupImageChooseButton() {
        chooseImageContainer.setOnClickListener(v -> {

            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .folderMode(true) // folder mode (false by default)
                    .toolbarFolderTitle("Folders")
                    .toolbarImageTitle("Tap to select") // image selection title
                    .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                    .includeVideo(false) // Show video on image picker
                    .single() // single mode
                    .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                    .theme(R.style.AppTheme_CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                    .enableLog(false) // disabling log
                    //.imageLoader(new GrayscaleImageLoder()) // custom image loader, must be serializeable
                    .start(); // start image picker activity with request code

            // TODO show image choose dialog
            // todo запись image file
        });
    }


    @Override
    public void onCookStepDeleteStep(int listPos, CookStep cookStep) {
        cookStepsAdapter.getList().remove(listPos);
        cookStepsAdapter.notifyItemRemoved(listPos);
    }

    @Override
    public void onCookStepEditStep(int listPos, CookStep cookStep) {
        // todo start edit form
        cookStepsAdapter.notifyItemChanged(listPos);

    }
}
