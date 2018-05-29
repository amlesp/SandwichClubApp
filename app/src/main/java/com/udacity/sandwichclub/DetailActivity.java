package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Toolbar toolbar;
    private ImageView ingredientsIv;
    private TextView tvAlsoKnownAs, tvPlaceOfOrigin, tvDescription, tvIngredients,
            tvAlsoKnownAsLabel, tvPlaceOfOriginLabel,
            tvDescriptionLabel, tvIngredientsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        initUI();
        populateUI(sandwich);
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ingredientsIv = findViewById(R.id.image_iv);
        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvAlsoKnownAsLabel = findViewById(R.id.also_known_label_tv);
        tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        tvPlaceOfOriginLabel = findViewById(R.id.place_of_origin_label_tv);
        tvDescription = findViewById(R.id.description_tv);
        tvDescriptionLabel = findViewById(R.id.description_label_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvIngredientsLabel = findViewById(R.id.ingredients_label_tv);
    }

    private void populateUI(Sandwich sandwich) {

        toolbar.setTitle(sandwich.getMainName());

        //loading image from url
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.mipmap.ic_launcher_round)
                .into(ingredientsIv);


        //checking alsoknown list size and loading data, if there is no data hiding a layout
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList.size() > 0) {
            StringBuilder sbAKA = new StringBuilder();
            for (int i = 0; i < alsoKnownList.size(); i++) {

                sbAKA.append(alsoKnownList.get(i));
                if (i != alsoKnownList.size() - 1) {
                    sbAKA.append(", ");
                }
            }
            tvAlsoKnownAs.setText(sbAKA);
        } else {
            tvAlsoKnownAsLabel.setVisibility(View.GONE);
            tvAlsoKnownAs.setVisibility(View.GONE);
        }

        if (sandwich.getPlaceOfOrigin() != null && sandwich.getPlaceOfOrigin().length() != 0) {
            tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            tvPlaceOfOrigin.setVisibility(View.GONE);
            tvPlaceOfOriginLabel.setVisibility(View.GONE);
        }

        if (sandwich.getDescription() != null && sandwich.getDescription().length() != 0) {
            tvDescription.setText(sandwich.getDescription());
        } else {
            tvDescription.setVisibility(View.GONE);
            tvDescriptionLabel.setVisibility(View.GONE);
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.size() > 0) {
            StringBuilder sbIngredients = new StringBuilder();
            for (int i = 0; i < ingredients.size(); i++) {

                sbIngredients.append(ingredients.get(i));
                if (i != ingredients.size() - 1) {
                    sbIngredients.append(", ");
                }
            }
            tvIngredients.setText(sbIngredients);
        } else {
            tvIngredients.setVisibility(View.GONE);
            tvIngredientsLabel.setVisibility(View.GONE);
        }

        //simple log to check json parsing
        log(sandwich);
    }

    private void log(Sandwich sandwich){
        Log.d("sandwich: ",
                "\nmainName: " + sandwich.getMainName()
                        + "\nalsoKnownAs: " + sandwich.getAlsoKnownAs()
                        + "\nplaceOfOrigin" + sandwich.getPlaceOfOrigin()
                        + "\ndescription" + sandwich.getDescription()
                        + "\nimage" + sandwich.getImage()
                        + "\ningredients" + sandwich.getIngredients());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
