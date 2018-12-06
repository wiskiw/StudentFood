package by.wiskiw.studentfood.ui.activity.list;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.presenter.list.CategoryListPresenter;

public class MyListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
    }

    @NonNull
    @Override
    public CategoryListPresenter createPresenter() {
        // todo createPresenter
        return null;
    }

}
