package by.wiskiw.studentfood.ui.adapter.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DialogDisableItemsAdapter extends BaseAdapter {

    private String[] items;
    private Boolean[] enableMask;
    private LayoutInflater layoutInflater;

    public DialogDisableItemsAdapter(Context context, String[] items, Boolean[] enableMask) {
        this.items = items;
        this.enableMask = enableMask;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public String getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) convertView;
        if (textView == null) {
            textView = (TextView) layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
            textView.setText(getItem(position));

            textView.setEnabled(isEnabled(position));
        }
        return textView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return enableMask[position];
    }
}
