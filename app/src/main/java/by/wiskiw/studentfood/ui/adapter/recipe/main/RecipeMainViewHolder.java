package by.wiskiw.studentfood.ui.adapter.recipe.main;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.image.RecipeImageFileManager;
import by.wiskiw.studentfood.mvp.model.SimpleRecipe;
import by.wiskiw.studentfood.ui.adapter.SimpleViewHolder;
import by.wiskiw.studentfood.utils.RecipeTimeUtil;

public class RecipeMainViewHolder extends RecyclerView.ViewHolder
        implements SimpleViewHolder<SimpleRecipe> {

    private ImageView previewIv;
    private TextView titleTv;
    private TextView descriptionTv;
    private TextView cookTimeTv;


    public RecipeMainViewHolder(@NonNull View itemView) {
        super(itemView);
        previewIv = itemView.findViewById(R.id.recipe_image_view);
        titleTv = itemView.findViewById(R.id.recipe_title_text_view);
        descriptionTv = itemView.findViewById(R.id.recipe_description_text_view);
        cookTimeTv = itemView.findViewById(R.id.cock_time_text_view);
    }


    @Override
    public void bind(SimpleRecipe item) {
        titleTv.setText(item.getTitle());
        descriptionTv.setText(item.getDescription());

        long cookTime = item.getCookTime();
        if (cookTime != 0) {
            String cookTimeStr = RecipeTimeUtil.INSTANCE.formatToString(cookTime);
            cookTimeTv.setText(cookTimeStr);
            cookTimeTv.setVisibility(View.VISIBLE);
        } else {
            cookTimeTv.setVisibility(View.GONE);
        }

        RecipeImageFileManager recipeImageFm = new RecipeImageFileManager(itemView.getContext());
        Bitmap bitmap = recipeImageFm.getImageBitmapByName(item.getHeaderImageFileName());
        if (bitmap != null) {
            previewIv.setImageBitmap(bitmap);
        }
    }
}
