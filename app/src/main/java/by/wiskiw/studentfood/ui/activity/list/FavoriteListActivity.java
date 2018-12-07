package by.wiskiw.studentfood.ui.activity.list;

import android.os.Bundle;
import android.support.annotation.NonNull;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.presenter.list.RecipesListPresenter;

public class FavoriteListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
    }

    @NonNull
    @Override
    public RecipesListPresenter createPresenter() {
        // todo createPresenter
        return null;
    }


}
