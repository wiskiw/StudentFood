package by.wiskiw.studentfood.ui.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.list.StaticRecipesListPresenter;
import by.wiskiw.studentfood.mvp.view.list.StaticRecipesListView;
import by.wiskiw.studentfood.ui.ActionDialogBuilder;
import by.wiskiw.studentfood.ui.activity.description.DescriptionActivity;

public class StaticRecipesListActivity extends ListActivity<StaticRecipesListView, StaticRecipesListPresenter>
        implements StaticRecipesListView {

    private static final String INTENT_TAG_RECIPE_CAT = "recipe-category";

    public static void putParams(Intent intent, RecipeCategory category) {
        if (intent != null) {
            intent.putExtra(INTENT_TAG_RECIPE_CAT, category);
        }
    }

    @Override
    public RecipeCategory getRecipeCategory() {
        return (RecipeCategory) getIntent().getSerializableExtra(INTENT_TAG_RECIPE_CAT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        setToolbarTitle(getRecipeCategory().getCategoryTitle(this));

        RecyclerView recipesRv = findViewById(R.id.recipes_recycler_view);
        super.initRecyclerView(recipesRv);
        super.enableListClickListener(true);
    }

    @NonNull
    @Override
    public StaticRecipesListPresenter createPresenter() {
        return new StaticRecipesListPresenter();
    }

    @Override
    public void onListItemClick(int listPos, SimpleRecipe recipe) {
        super.onListItemClick(listPos, recipe);
        Intent intent = new Intent(this, DescriptionActivity.class);
        DescriptionActivity.putArgs(intent, recipe.getId(), listPos);
        startActivity(intent);
    }

    @Override
    public boolean onListItemLongClick(final int listPos, final SimpleRecipe item) {
        ActionDialogBuilder dialogBuilder = new ActionDialogBuilder(this);
        dialogBuilder.setTitle(item.getTitle());
        dialogBuilder.setActions(getResources().getStringArray(R.array.static_recipes_list_item_actions));
        dialogBuilder.setListener(actionIndex -> {
            switch (actionIndex) {
                case 0:
                    // Изменить
                    presenter.editRecipe(listPos, item);
                    break;
                case 1:
                    // Удалить
                    presenter.deleteRecipe(listPos, item);
                    break;
            }
        });
        dialogBuilder.build().show();
        return true;
    }
}
