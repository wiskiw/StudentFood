package by.wiskiw.studentfood.ui.activity.create.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.di.FoodApp;
import by.wiskiw.studentfood.di.bus.ListItemUpdateAction;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.CreateEditPresenter;
import by.wiskiw.studentfood.mvp.view.CreateEditView;
import by.wiskiw.studentfood.ui.activity.FoodAppActivity;

public class CreateEditActivity extends FoodAppActivity<CreateEditView, CreateEditPresenter>
        implements CreateEditView {

    private static final String INTENT_TAG_RECIPE_ID = "recipe-id";
    private static final String INTENT_TAG_RECIPE_LIST_POS = "list-pos";
    private RecipesRepositoryKt recipesRepository;

    private Button saveBtn;
    private Button cancelBtn;

    private int listPos;


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

        Intent intent = getIntent();
        if (intent != null) {
            parseIntentArgs(intent);
        }

        saveBtn = findViewById(R.id.button_save);
        cancelBtn = findViewById(R.id.button_cancel);

        setupNavButtons();
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

    private void setupNavButtons() {
        saveBtn.setOnClickListener(v -> saveRecipe());
        cancelBtn.setOnClickListener(v -> finish());
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

            // todo запись полей в recipe

            presenter.saveRecipe();
            finish();
        }
    }

}
