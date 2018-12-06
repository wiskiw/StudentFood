package by.wiskiw.studentfood.utils.diff.util;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffUtilCallback<T extends DiffUtilItem> extends DiffUtil.Callback {

    private final List<T> oldList;
    private final List<T> newList;

    DiffUtilCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPos, int newPos) {
        return oldList.get(oldPos).getId() == newList.get(newPos).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldPos, int newPos) {
        return oldList.get(oldPos).equals(newList.get(newPos));
    }

}
