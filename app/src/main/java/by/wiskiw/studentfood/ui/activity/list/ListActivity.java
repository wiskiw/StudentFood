package by.wiskiw.studentfood.ui.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.db.repository.RecipesRepositoryKt;
import by.wiskiw.studentfood.di.FoodApp;
import by.wiskiw.studentfood.di.bus.ListItemUpdateAction;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.list.RecipesListPresenter;
import by.wiskiw.studentfood.mvp.view.list.RecipesListView;
import by.wiskiw.studentfood.ui.activity.FoodAppActivity;
import by.wiskiw.studentfood.ui.activity.create.edit.CreateEditActivity;
import by.wiskiw.studentfood.ui.adapter.ListItemOnClickListener;
import by.wiskiw.studentfood.ui.adapter.recipe.main.RecipeMainListAdapter;

public abstract class ListActivity<V extends RecipesListView, P extends RecipesListPresenter<V>>
        extends FoodAppActivity<V, P> implements RecipesListView, ListItemOnClickListener<SimpleRecipe> {

    private RecipesRepositoryKt recipesRepository;

    private RecipeMainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        enableToolbarBackArror(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.toolbar_action_add:
                Intent launchIntent = new Intent(this, CreateEditActivity.class);
                startActivity(launchIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void startEditRecipeActivity(int recipeId, int listPos) {
        Intent launchIntent = new Intent(this, CreateEditActivity.class);
        CreateEditActivity.putRecipeId(launchIntent, recipeId);
        CreateEditActivity.putListPos(launchIntent, listPos);
        startActivity(launchIntent);
    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(getAdapter());
    }

    @Override
    public RecipesRepositoryKt getRecipeRepository() {
        if (recipesRepository == null) {
            recipesRepository = new RecipesRepositoryKt(this);
        }
        return recipesRepository;
    }

    protected void enableListClickListener(boolean enable) {
        if (enable) {
            getAdapter().setOnClickListener(this);
        } else {
            getAdapter().setOnClickListener(null);
        }
    }

    protected RecipeMainListAdapter getAdapter() {
        if (adapter == null) {
            adapter = new RecipeMainListAdapter(this);
        }
        return adapter;
    }

    @Override
    public void showRecipes(List<SimpleRecipe> recipes) {
        getAdapter().setAll(recipes);
    }


    @Override
    public void onListItemClick(int listPos, SimpleRecipe item) {

    }

    @Override
    public boolean onListItemLongClick(int listPos, SimpleRecipe item) {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListItemUpdateEvent(ListItemUpdateAction action) {
        Log.d(FoodApp.LOG_TAG, "onListItemUpdateEvent: " + action.toString());
        presenter.onListItemUpdateEvent(action);
    }
}
