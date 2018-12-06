package by.wiskiw.studentfood.ui.adapter.recipe.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.ui.adapter.ListItemOnClickListener;
import by.wiskiw.studentfood.utils.diff.util.DiffCalculatorListAdapter;

public class RecipeMainListAdapter extends DiffCalculatorListAdapter<SimpleRecipe, RecipeMainViewHolder> {

    private Context context;

    @Nullable
    private ListItemOnClickListener<SimpleRecipe> onClickListener;

    public RecipeMainListAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(@Nullable ListItemOnClickListener<SimpleRecipe> onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void setupOnClickListener(View itemView, RecipeMainViewHolder viewHolder) {
        itemView.setOnLongClickListener(v -> {
            if (onClickListener != null) {
                int position = viewHolder.getAdapterPosition();
                return onClickListener.onListItemLongClick(position, list.get(position));
            }
            return false;
        });

        itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                int position = viewHolder.getAdapterPosition();
                onClickListener.onListItemClick(position, list.get(position));
            }
        });
    }

    @NonNull
    @Override
    public RecipeMainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_list_item, viewGroup, false);
        RecipeMainViewHolder viewHolder = new RecipeMainViewHolder(itemView);
        setupOnClickListener(itemView, viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeMainViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

}
