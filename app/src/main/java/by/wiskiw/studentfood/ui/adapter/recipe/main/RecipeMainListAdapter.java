package by.wiskiw.studentfood.ui.adapter.recipe.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import by.wiskiw.studentfood.mvp.model.SimpleRecipe;

public class RecipeMainListAdapter extends RecyclerView.Adapter<RecipeMainViewHolder> {

    private Context context;
    private List<SimpleRecipe> recipeList = new ArrayList<>();

    public RecipeMainListAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<SimpleRecipe> list) {
        // обработать через diff util
        this.recipeList.addAll(list);
    }

    @NonNull
    @Override
    public RecipeMainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // todo fix onCreateViewHolder
        return new RecipeMainViewHolder();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeMainViewHolder viewHolder, int i) {
        viewHolder.bind(recipeList.get(i));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
