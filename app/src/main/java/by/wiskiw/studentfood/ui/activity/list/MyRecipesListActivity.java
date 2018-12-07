package by.wiskiw.studentfood.ui.activity.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.presenter.list.MyRecipesListPresenter;
import by.wiskiw.studentfood.mvp.view.list.MyRecipesListView;
import by.wiskiw.studentfood.ui.ActionDialogBuilder;

public class MyRecipesListActivity extends ListActivity<MyRecipesListView, MyRecipesListPresenter>
        implements MyRecipesListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        setToolbarTitle(getResources().getString(R.string.my_recipes_list_activity_title));

        RecyclerView recipesRv = findViewById(R.id.recipes_recycler_view);
        initRecyclerView(recipesRv);
        enableListClickListener(true);
    }

    @NonNull
    @Override
    public MyRecipesListPresenter createPresenter() {
        return new MyRecipesListPresenter();
    }

    @Override
    public boolean onListItemLongClick(int listPos, SimpleRecipe item) {
        ActionDialogBuilder dialogBuilder = new ActionDialogBuilder(this);
        dialogBuilder.setTitle(item.getTitle());
        dialogBuilder.setActions(getResources().getStringArray(R.array.my_recipes_list_item_actions));
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
