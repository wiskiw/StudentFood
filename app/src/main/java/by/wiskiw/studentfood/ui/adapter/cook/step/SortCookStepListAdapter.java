package by.wiskiw.studentfood.ui.adapter.cook.step;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.CookStep;
import by.wiskiw.studentfood.utils.diff.util.DiffCalculatorListAdapter;

public class SortCookStepListAdapter extends DiffCalculatorListAdapter<CookStep, SortCookStepViewHolder>
        implements DragDropItemTouchHelperCallback.DragDropItemTouchHelperAdapter {

    public List<CookStep> getList() {
        return list;
    }

    @Nullable
    private ClickListener clickListener = null;

    public void setClickListener(@Nullable ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SortCookStepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cook_step_edit_item, viewGroup, false);
        return new SortCookStepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SortCookStepViewHolder viewHolder, int i) {
        viewHolder.bind(clickListener, list.get(i));
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < list.size() && toPosition < list.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--)
                    Collections.swap(list, i, i - 1);
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    public interface ClickListener {
        void onCookStepDeleteStep(int listPos, CookStep cookStep);

        void onCookStepEditStep(int listPos, CookStep cookStep);
    }
}
