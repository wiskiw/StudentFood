package by.wiskiw.studentfood.ui.activity;

import android.support.v7.app.ActionBar;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public abstract class FoodAppActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {

    protected void setToolbarTitle(String title) {
        setTitle(title);
    }

    protected void enableToolbarBackArror(boolean enable) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enable);
            actionBar.setDisplayShowHomeEnabled(enable);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
