package by.wiskiw.studentfood.ui.adapter;

public interface ListItemOnClickListener<I> {

    void onListItemClick(int listPos, I item);

    boolean onListItemLongClick(int listPos, I item);

}
