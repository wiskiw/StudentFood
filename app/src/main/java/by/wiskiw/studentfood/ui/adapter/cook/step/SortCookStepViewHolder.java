package by.wiskiw.studentfood.ui.adapter.cook.step;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.mvp.model.CookStep;

class SortCookStepViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageIv;
    private Button deleteBtn;
    private Button editBtn;
    private TextView textTv;
    private TextView timeTv;


    SortCookStepViewHolder(@NonNull View itemView) {
        super(itemView);
        imageIv = itemView.findViewById(R.id.image_view_image);
        deleteBtn = itemView.findViewById(R.id.button_delete);
        editBtn = itemView.findViewById(R.id.button_edit);
        textTv = itemView.findViewById(R.id.text_view_text);
        timeTv = itemView.findViewById(R.id.text_view_time);
    }

    void bind(@Nullable SortCookStepListAdapter.ClickListener clickListener, CookStep cookStep) {
        if (clickListener != null) {
            deleteBtn.setOnClickListener(v -> clickListener.onCookStepDeleteStep(getAdapterPosition(), cookStep));
            editBtn.setOnClickListener(v -> clickListener.onCookStepEditStep(getAdapterPosition(), cookStep));
        }
        textTv.setText(cookStep.getText());
        timeTv.setText(cookStep.getTimeString());
        // todo finish bind cookStep : imageIv
    }


}
