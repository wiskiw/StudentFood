package by.wiskiw.studentfood.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import by.wiskiw.studentfood.R;

public class CookStepEditDialog extends DialogFragment {

    public static final String ARGS_KEY_TEXT = "b-text";
    public static final String ARGS_KEY_TIME = "b-time";

    private EditText descriptionEt;
    private EditText timeEt;

    private String cookStepText = "";
    private String cookStepTime = "";

    public static CookStepEditDialog newInstance(String text, String time) {
        CookStepEditDialog dialog = new CookStepEditDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_TEXT, text);
        args.putString(ARGS_KEY_TIME, time);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            cookStepText = args.getString(ARGS_KEY_TEXT);
            cookStepTime = args.getString(ARGS_KEY_TIME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            cookStepText = args.getString(ARGS_KEY_TEXT);
            cookStepTime = args.getString(ARGS_KEY_TIME);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Enter Players")
                .setPositiveButton("OK",
                        (dialog, whichButton) -> {
                            // do something...
                        }
                )
                .setNegativeButton("Cancel",
                        (dialog, whichButton) -> dialog.dismiss()
                );


        LayoutInflater i = getActivity().getLayoutInflater();
        View rootView = i.inflate(R.layout.fragment_cook_step_edit, null);
        initViews(rootView);
        fillViews();

        builder.setView(rootView);
        return builder.create();
    }

    private void fillViews() {
        timeEt.setText(cookStepTime);
        timeEt.setSelection(cookStepTime.length());

        descriptionEt.setText(cookStepText);
        descriptionEt.setSelection(cookStepText.length());
    }


    private void initViews(View rootView) {
        timeEt = rootView.findViewById(R.id.edit_text_time);
        descriptionEt = rootView.findViewById(R.id.edit_text_description);
    }
}
