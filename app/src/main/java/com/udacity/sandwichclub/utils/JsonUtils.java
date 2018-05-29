package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String JSON_KEY_NAME = "name";
    public static final String JSON_KEY_MAIN_NAME = "mainName";
    public static final String JSON_KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String JSON_KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String JSON_KEY_DESCRIPTION = "description";
    public static final String JSON_KEY_IMAGE = "image";
    public static final String JSON_KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        try {
            JSONObject jsonObject = new JSONObject(json);


            if (jsonObject.has(JSON_KEY_NAME)) {
                //parsing json object name
                JSONObject jsonObjectName = jsonObject.getJSONObject(JSON_KEY_NAME);

                //setting mainName to model
                sandwich.setMainName(jsonObjectName.optString(JSON_KEY_MAIN_NAME));

                List<String> alsoKnownAsList = new ArrayList<>();
                JSONArray jsonArrayAlsoKnownAs = jsonObjectName.getJSONArray(JSON_KEY_ALSO_KNOWN_AS);
                for (int i = 0; i < jsonArrayAlsoKnownAs.length(); i++) {
                    alsoKnownAsList.add(jsonArrayAlsoKnownAs.getString(i));
                }

                //setting alsoKnownAs list to model
                sandwich.setAlsoKnownAs(alsoKnownAsList);
            }

            if (jsonObject.has(JSON_KEY_PLACE_OF_ORIGIN)) {
                //setting placeOfOrigin to model
                sandwich.setPlaceOfOrigin(jsonObject.optString(JSON_KEY_PLACE_OF_ORIGIN));
            }

            if (jsonObject.has(JSON_KEY_DESCRIPTION)) {

                //setting description to model
                sandwich.setDescription(jsonObject.optString(JSON_KEY_DESCRIPTION));
            }

            if (jsonObject.has(JSON_KEY_IMAGE)) {
                //setting image url to model
                sandwich.setImage(jsonObject.optString(JSON_KEY_IMAGE));
            }

            if (jsonObject.has(JSON_KEY_INGREDIENTS)) {
                List<String> ingredientsList = new ArrayList<>();
                JSONArray jsonArrayIngredients = jsonObject.getJSONArray(JSON_KEY_INGREDIENTS);

                for (int j = 0; j < jsonArrayIngredients.length(); j++) {
                    ingredientsList.add(jsonArrayIngredients.getString(j));
                }
                //setting ingredients list to model
                sandwich.setIngredients(ingredientsList);
            }

            Log.d("jsonObject", jsonObject.toString());

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
