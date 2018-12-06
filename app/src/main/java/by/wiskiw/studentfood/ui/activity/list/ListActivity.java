package by.wiskiw.studentfood.ui.activity.list;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.list.CategoryListPresenter;
import by.wiskiw.studentfood.mvp.view.list.CategoryListView;
import by.wiskiw.studentfood.ui.adapter.recipe.main.RecipeMainListAdapter;

public abstract class ListActivity<V extends CategoryListView, P extends CategoryListPresenter<V>>
        extends MvpActivity<V, P> implements CategoryListView {

    private RecipeMainListAdapter adapter;

    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(getAdapter());
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

}
