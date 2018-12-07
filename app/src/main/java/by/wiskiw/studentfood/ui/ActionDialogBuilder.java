package by.wiskiw.studentfood.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import java.util.Arrays;

import by.wiskiw.studentfood.ui.adapter.dialog.DialogDisableItemsAdapter;

public class ActionDialogBuilder {

    private Context context;
    private String[] actions;
    private Boolean[] enableItemsMask;
    private ActionDialogListener listener;
    private String title;

    private Boolean[] getEnableItemsMask() {
        if (enableItemsMask == null) {
            enableItemsMask = new Boolean[actions.length];
            Arrays.fill(enableItemsMask, true);
        }
        return enableItemsMask;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public void setEnableItemsMask(Boolean[] enableItemsMask) {
        this.enableItemsMask = enableItemsMask;
    }

    public void setItemEnable(int itemPos, boolean state) {
        if (itemPos < 0 || itemPos >= getEnableItemsMask().length) {
            throw new IllegalArgumentException("Enable items mask has only " + getEnableItemsMask().length
                    + " elements. " + itemPos + " is out of border!");
        } else {
            getEnableItemsMask()[itemPos] = state;
        }
    }

    public void setListener(ActionDialogListener listener) {
        this.listener = listener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ActionDialogBuilder(Context context) {
        this.context = context;
    }

    public Dialog build() {
        DialogDisableItemsAdapter adapter = new DialogDisableItemsAdapter(context, actions, getEnableItemsMask());
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setCancelable(true)
               .setAdapter(adapter, null)
                .setItems(actions, (dialog, which) -> listener.onActionSelect(which));
        return dialogBuilder.create();
    }

    public interface ActionDialogListener {

        void onActionSelect(int actionIndex);

    }

}
