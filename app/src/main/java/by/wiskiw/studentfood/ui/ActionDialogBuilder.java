package by.wiskiw.studentfood.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

public class ActionDialogBuilder {

    private Context context;
    private String[] actions;
    private ActionDialogListener listener;
    private String title;

    public void setActions(String[] actions) {
        this.actions = actions;
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setCancelable(true)
                .setItems(actions, (dialog, which) -> listener.onActionSelect(which));
        return dialogBuilder.create();
    }

    public interface ActionDialogListener {

        void onActionSelect(int actionIndex);

    }

}
