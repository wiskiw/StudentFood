package by.wiskiw.studentfood.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.RecipeGroup;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.presenter.MainPresenter;
import by.wiskiw.studentfood.mvp.view.MainView;
import by.wiskiw.studentfood.ui.activity.list.FavoriteListActivity;
import by.wiskiw.studentfood.ui.activity.list.ListActivity;
import by.wiskiw.studentfood.ui.activity.list.StaticCategoryListActivity;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    private CardView categoryACv;
    private CardView categoryBCv;
    private CardView categoryCCv;
    private CardView categoryMyCv;
    private CardView categoryFavoriteCv;

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryACv = findViewById(R.id.category_a_card_view);
        categoryBCv = findViewById(R.id.category_b_card_view);
        categoryCCv = findViewById(R.id.category_c_card_view);
        categoryMyCv = findViewById(R.id.category_my_card_view);
        categoryFavoriteCv = findViewById(R.id.category_favorite_card_view);

        setupButtonClickListeners();
    }

    private void setupButtonClickListeners() {
        categoryACv.setOnClickListener(v ->
                presenter.onCategoryClicked(RecipeGroup.STATIC, RecipeCategory.CATEGORY_A));

        categoryBCv.setOnClickListener(v ->
                presenter.onCategoryClicked(RecipeGroup.STATIC, RecipeCategory.CATEGORY_B));

        categoryCCv.setOnClickListener(v ->
                presenter.onCategoryClicked(RecipeGroup.STATIC, RecipeCategory.CATEGORY_C));

        categoryMyCv.setOnClickListener(v ->
                presenter.onCategoryClicked(RecipeGroup.STATIC, null));

        categoryFavoriteCv.setOnClickListener(v ->
                presenter.onCategoryClicked(RecipeGroup.STATIC, null));
    }

    @Override
    public void startListActivity(RecipeGroup group, @Nullable RecipeCategory recipeCategory) {
        Class activityClass = null;
        switch (group) {
            case STATIC:
                activityClass = StaticCategoryListActivity.class;
                break;
            case MINE:
                activityClass = ListActivity.class;
                break;
            case FAVORITE:
                activityClass = FavoriteListActivity.class;
                break;
            default:
                assert true : "Process all groups!";
        }

        Intent intent = new Intent(this, activityClass);
        if (recipeCategory != null) {
            ListActivity.putParams(intent, recipeCategory);
        }
        startActivity(intent);
    }
}
