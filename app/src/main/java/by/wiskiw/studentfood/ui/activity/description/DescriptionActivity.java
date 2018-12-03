package by.wiskiw.studentfood.ui.activity.description;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import by.wiskiw.studentfood.R;
import by.wiskiw.studentfood.data.db.model.CookStep;

public class DescriptionActivity extends AppCompatActivity {

    private LinearLayout cookingLevelsLl;

    private ImageView headerIv;
    private ImageView action1Iv;
    private ImageView action2Iv;
    private ImageView action3Iv;

    private TextView descriptionTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        cookingLevelsLl = findViewById(R.id.linear_layout_cooking_levels);
        headerIv = findViewById(R.id.image_view_header);

        action1Iv = findViewById(R.id.image_view_action_1);
        action2Iv = findViewById(R.id.image_view_action_2);
        action3Iv = findViewById(R.id.image_view_action_3);

        descriptionTv = findViewById(R.id.text_view_description);


        setupToolbar();


        debug();
    }

    private void debug() {
        setupToolbarTitle("Debug Блюдо!");
        setDescription("\n" +
                "Один из самых популярных десертов в мире — брауни — был придуман в 1893 году на кухне легендарного отеля Palmer House в Чикаго. ");

        CookStep level1 = new CookStep();
        level1.setText("Доведите воду до кипения");
        level1.setTimeMin(7);
        addCookLevel(level1);


        CookStep level2 = new CookStep();
        level2.setText("Нарежте сыр");
        level2.setTimeMin(3);
        addCookLevel(level2);

        CookStep level3 = new CookStep();
        level3.setText("Всыпьте сыра");
        level3.setTimeMin(1);
        addCookLevel(level3);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupToolbarTitle(@NotNull String title) {
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    private void setDescription(@NotNull String description) {
        descriptionTv.setText(description);
    }

    private void addCookLevel(CookStep cookStep) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemView = inflater.inflate(R.layout.content_cook_level, cookingLevelsLl, false);

        TextView textTv = itemView.findViewById(R.id.text_view_text);
        TextView timeTv = itemView.findViewById(R.id.text_view_time);
        ImageView imageView = itemView.findViewById(R.id.image_view_image);

        textTv.setText(cookStep.getText());
        timeTv.setText(cookStep.getTimeString());
        // todo : show image imageView.set...

        cookingLevelsLl.addView(itemView);
    }
}
