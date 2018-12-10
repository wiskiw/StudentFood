package by.wiskiw.studentfood.ui.adapter.cook.step;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class DragDropItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private SortCookStepListAdapter adapter;

    public DragDropItemTouchHelperCallback(SortCookStepListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder targetViewHolder) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), targetViewHolder.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    interface DragDropItemTouchHelperAdapter {
        boolean onItemMove(int fromPosition, int toPosition);
    }
}
