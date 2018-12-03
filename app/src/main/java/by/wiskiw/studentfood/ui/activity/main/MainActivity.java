package by.wiskiw.studentfood.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import by.wiskiw.studentfood.R;
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

    private void onClick(RecipeCategory recipeCategory) {
        presenter.onCategoryClicked(recipeCategory);
    }

    private void setupButtonClickListeners() {
        categoryACv.setOnClickListener(v -> onClick(RecipeCategory.CAT_A));
        categoryBCv.setOnClickListener(v -> onClick(RecipeCategory.CAT_B));
        categoryCCv.setOnClickListener(v -> onClick(RecipeCategory.CAT_C));
        categoryMyCv.setOnClickListener(v -> onClick(RecipeCategory.MY));
        categoryFavoriteCv.setOnClickListener(v -> onClick(RecipeCategory.FAVORITE));
    }

    @Override
    public void startListActivity(RecipeCategory recipeCategory) {
        Class activityClass = null;
        switch (recipeCategory) {
            case CAT_A:
            case CAT_B:
            case CAT_C:
                activityClass = StaticCategoryListActivity.class;
                break;
            case MY:
                activityClass = ListActivity.class;
                break;
            case FAVORITE:
                activityClass = FavoriteListActivity.class;
                break;
            default:
                assert true : "Process all categories!";
        }

        Intent intent = new Intent(this, activityClass);
        ListActivity.putParams(intent, recipeCategory);
        startActivity(intent);
    }
}
