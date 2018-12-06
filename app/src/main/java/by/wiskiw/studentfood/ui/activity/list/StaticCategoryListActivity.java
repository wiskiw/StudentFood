package by.wiskiw.studentfood.ui.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.db.repository.StaticRecipeRepositoryKt;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.presenter.list.StaticCategoryListPresenter;
import by.wiskiw.studentfood.mvp.view.list.StaticCategoryListView;

public class StaticCategoryListActivity extends ListActivity<StaticCategoryListView, StaticCategoryListPresenter>
        implements StaticCategoryListView {

    private static final String INTENT_TAG_RECIPE_CAT = "recipe-category";
    private StaticRecipeRepositoryKt staticRecipeRepository;

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

        RecyclerView recipesRv = findViewById(R.id.recipes_recycler_view);
        super.initRecyclerView(recipesRv);
    }

    @NonNull
    @Override
    public StaticCategoryListPresenter createPresenter() {
        return new StaticCategoryListPresenter();
    }

    @Override
    public StaticRecipeRepositoryKt getStaticRecipeRep() {
        if (staticRecipeRepository == null) {
            staticRecipeRepository = new StaticRecipeRepositoryKt(this);
        }
        return staticRecipeRepository;
    }
}
