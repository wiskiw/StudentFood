package by.wiskiw.studentfood.utils.diff.util;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * List Adapter с функцией определения изменений в списке элементов.
 *
 * @param <I>  элемент списка
 * @param <VH> ViewHolder для элементов списка
 */
public abstract class DiffCalculatorListAdapter<I extends DiffUtilItem, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected ArrayList<I> list;

    protected DiffCalculatorListAdapter() {
        list = new ArrayList<>();
    }

    public final void setAll(List<I> newList) {
        DiffUtilCallback<I> diffCallback = new DiffUtilCallback<>(list, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        list.clear();
        list.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public final int getItemCount() {
        return list.size();
    }

}
