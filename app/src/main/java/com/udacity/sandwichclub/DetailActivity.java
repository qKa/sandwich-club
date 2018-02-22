package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.nio.charset.Charset;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView image;
    private TextView origin_label;
    private TextView origin;
    private TextView alsoKnown_label;
    private TextView alsoKnown;
    private TextView ingredients;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = findViewById(R.id.image_iv);
        origin_label = findViewById(R.id.origin_label);
        origin = findViewById(R.id.origin_tv);
        alsoKnown_label = findViewById(R.id.also_known_label);
        alsoKnown = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        description = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        setTitle(sandwich.getMainName());
        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(image);

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            // origin.setText(R.string.detail_error_message);
            // if we don't have origin, hide it.
            origin_label.setVisibility(View.GONE);
            origin.setVisibility(View.GONE);
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            // alsoKnown.setText(R.string.detail_error_message);
            // if we don't have aka, hide it.
            alsoKnown_label.setVisibility(View.GONE);
            alsoKnown.setVisibility(View.GONE);
        } else {
            List<String> aka = sandwich.getAlsoKnownAs();
            String aka_str = TextUtils.join(", ", aka);
            alsoKnown.setText(aka_str);
        }

        description.setText(sandwich.getDescription());

        List<String> ing = sandwich.getIngredients();
        String ing_str = TextUtils.join(", ", ing);
        ingredients.setText(ing_str);
    }
}
