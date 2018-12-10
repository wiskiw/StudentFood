package by.wiskiw.studentfood.ui.activity.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.list.FavoriteRecipesListPresenter;
import by.wiskiw.studentfood.mvp.view.list.FavoriteRecipesListView;
import by.wiskiw.studentfood.ui.ActionDialogBuilder;

public class FavoriteRecipesListActivity extends ListActivity<FavoriteRecipesListView, FavoriteRecipesListPresenter>
        implements FavoriteRecipesListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        setToolbarTitle(getResources().getString(R.string.favorite_recipes_list_activity_title));

        RecyclerView recipesRv = findViewById(R.id.recipes_recycler_view);
        initRecyclerView(recipesRv);
        enableListClickListener(true);
    }

    @NonNull
    @Override
    public FavoriteRecipesListPresenter createPresenter() {
        return new FavoriteRecipesListPresenter();
    }

    @Override
    public boolean onListItemLongClick(int listPos, SimpleRecipe item) {
        ActionDialogBuilder dialogBuilder = new ActionDialogBuilder(this);
        dialogBuilder.setTitle(item.getTitle());
        dialogBuilder.setActions(getResources().getStringArray(R.array.favorite_recipes_list_item_actions));

        boolean isMyRecipe = presenter.isMyRecipe(item.getId());
        dialogBuilder.setItemEnable(2, isMyRecipe);

        dialogBuilder.setListener(actionIndex -> {
            switch (actionIndex) {
                case 0:
                    // Изменить
                    startEditRecipeActivity(item.getId(), listPos);
                    break;
                case 1:
                    // Убрать из избранных
                    presenter.removeRecipeFromFavorite(listPos, item);
                    break;
                case 2:
                    // Удалить
                    presenter.deleteRecipe(listPos, item);
                    break;
            }
        });
        dialogBuilder.build().show();
        return true;
    }
}
