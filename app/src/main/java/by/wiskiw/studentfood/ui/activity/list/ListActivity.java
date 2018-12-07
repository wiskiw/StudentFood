package by.wiskiw.studentfood.ui.activity.list;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import by.wiskiw.studentfood.data.db.repository.FavoriteRecipeRepositoryKt;
import by.wiskiw.studentfood.data.db.repository.MyRecipeRepositoryKt;
import by.wiskiw.studentfood.data.db.repository.StaticRecipeRepositoryKt;
import by.wiskiw.studentfood.di.bus.ListItemUpdateAction;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.list.RecipesListPresenter;
import by.wiskiw.studentfood.mvp.view.list.RecipesListView;
import by.wiskiw.studentfood.ui.activity.FoodAppActivity;
import by.wiskiw.studentfood.ui.adapter.ListItemOnClickListener;
import by.wiskiw.studentfood.ui.adapter.recipe.main.RecipeMainListAdapter;

public abstract class ListActivity<V extends RecipesListView, P extends RecipesListPresenter<V>>
        extends FoodAppActivity<V, P> implements RecipesListView, ListItemOnClickListener<SimpleRecipe> {

    private StaticRecipeRepositoryKt staticRecipeRepository;
    private MyRecipeRepositoryKt myRecipeRepository;
    private FavoriteRecipeRepositoryKt favoriteRecipeRepository;

    private RecipeMainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableToolbarBackArror(true);
    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(getAdapter());
    }

    @Override
    public StaticRecipeRepositoryKt getStaticRecipeRep() {
        if (staticRecipeRepository == null) {
            staticRecipeRepository = new StaticRecipeRepositoryKt(this);
        }
        return staticRecipeRepository;
    }

    @Override
    public MyRecipeRepositoryKt getMyRecipeRep() {
        if (myRecipeRepository == null) {
            myRecipeRepository = new MyRecipeRepositoryKt(this);
        }
        return myRecipeRepository;
    }

    @Override
    public FavoriteRecipeRepositoryKt getFavoriteRecipeRep() {
        if (favoriteRecipeRepository == null) {
            favoriteRecipeRepository = new FavoriteRecipeRepositoryKt(this);
        }
        return favoriteRecipeRepository;
    }

    protected void enableListClickListener(boolean enable){
        if (enable){
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListItemUpdateEvent(ListItemUpdateAction action) {
        presenter.onListItemUpdateEvent(action);
    }
}
