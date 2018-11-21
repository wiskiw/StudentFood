package by.wiskiw.studentfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CardView categoryACv;
    private CardView categoryBCv;
    private CardView categoryCCv;
    private CardView categoryMyCv;
    private CardView categoryFavoriteCv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryACv = findViewById(R.id.category_a_card_view);
        categoryBCv = findViewById(R.id.category_b_card_view);
        categoryCCv = findViewById(R.id.category_c_card_view);
        categoryMyCv = findViewById(R.id.category_my_card_view);
        categoryFavoriteCv = findViewById(R.id.category_favorite_card_view);

        debug();
    }

    private void debug() {
        View.OnClickListener clickListener = (v) ->
                startActivity(new Intent(this,
                        DescriptionActivity.class));

        categoryACv.setOnClickListener(clickListener);
        categoryBCv.setOnClickListener(clickListener);
        categoryCCv.setOnClickListener(clickListener);
        categoryMyCv.setOnClickListener(clickListener);
        categoryFavoriteCv.setOnClickListener(clickListener);
    }


}
