package by.wiskiw.studentfood.ui.adapter.recipe.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.utils.diff.util.DiffCalculatorListAdapter;

public class RecipeMainListAdapter extends DiffCalculatorListAdapter<SimpleRecipe, RecipeMainViewHolder> {

    private Context context;

    public RecipeMainListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeMainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeMainViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeMainViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

}
