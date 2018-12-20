package by.wiskiw.studentfood.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;

import by.wiskiw.studentfood.BuildConfig;
import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.RecipeCategory;
import by.wiskiw.studentfood.mvp.model.RecipeGroup;
import by.wiskiw.studentfood.mvp.presenter.MainPresenter;
import by.wiskiw.studentfood.mvp.view.MainView;
import by.wiskiw.studentfood.ui.activity.FoodAppActivity;
import by.wiskiw.studentfood.ui.activity.list.FavoriteRecipesListActivity;
import by.wiskiw.studentfood.ui.activity.list.MyRecipesListActivity;
import by.wiskiw.studentfood.ui.activity.list.StaticRecipesListActivity;

public class MainActivity extends FoodAppActivity<MainView, MainPresenter> implements MainView {

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

        // Возврат стандартного стиля после Splash Screen
        setTheme(R.style.AppTheme);

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
                presenter.onCategoryClicked(RecipeGroup.MINE, null));

        categoryFavoriteCv.setOnClickListener(v ->
                presenter.onCategoryClicked(RecipeGroup.FAVORITE, null));
    }

    @Override
    public void startListActivity(RecipeGroup group, @Nullable RecipeCategory recipeCategory) {
        Intent intent = null;
        switch (group) {
            case STATIC:
                intent = new Intent(this, StaticRecipesListActivity.class);
                if (recipeCategory != null) {
                    StaticRecipesListActivity.putParams(intent, recipeCategory);
                }
                break;
            case MINE:
                intent = new Intent(this, MyRecipesListActivity.class);
                break;
            case FAVORITE:
                intent = new Intent(this, FavoriteRecipesListActivity.class);
                break;
            default:
                if (BuildConfig.DEBUG) throw new IllegalStateException("Process all groups in the switch!");
        }

        startActivity(intent);
    }
}
