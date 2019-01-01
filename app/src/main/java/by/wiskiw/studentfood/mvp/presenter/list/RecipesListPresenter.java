package by.wiskiw.studentfood.mvp.presenter.list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.Comparator;
import java.util.List;

import by.wiskiw.studentfood.di.bus.RecipeUpdateAction;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.mvp.model.SortBy;
import by.wiskiw.studentfood.mvp.view.list.RecipesListView;

public abstract class RecipesListPresenter<V extends RecipesListView>
        extends MvpBasePresenter<V> {

    private SortBy currentSortBy = SortBy.NAME;

    private Comparator<SimpleRecipe> comparatorByName =
            Comparator.comparing(SimpleRecipe::getTitle);

    private Comparator<SimpleRecipe> comparatorByCookTime =
            (o1, o2) -> (int) (o1.getCookTime() - o2.getCookTime());

    public void onListItemUpdateEvent(RecipeUpdateAction action) {

    }

    public void onShortActionCalled(SortBy sortBy) {
        this.currentSortBy = sortBy;
        loadList();
    }

    protected abstract void loadList();


    void sort(List<SimpleRecipe> recipes) {
        switch (currentSortBy) {
            case NAME:
                recipes.sort(comparatorByName);
                break;
            case COOK_TIME:
                recipes.sort(comparatorByCookTime);
                break;
        }
    }
}
