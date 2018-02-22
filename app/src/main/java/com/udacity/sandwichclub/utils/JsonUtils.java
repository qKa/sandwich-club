package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        JSONObject name;
        String mainName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        try {
            JSONObject root = new JSONObject(json);
            name = root.getJSONObject("name");
            mainName = name.getString("mainName");

            JSONArray alsoKnownAs_JA = name.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();

            if (alsoKnownAs_JA.length() != 0){
                for (int i = 0; i < alsoKnownAs_JA.length(); i++) {
                    alsoKnownAs.add(alsoKnownAs_JA.getString(i));
                }
            }

            placeOfOrigin = root.getString("placeOfOrigin");
            description = root.getString("description");
            image = root.getString("image");

            JSONArray ingredients_JA = root.getJSONArray("ingredients");
            ingredients = new ArrayList<>();

            if (ingredients_JA.length() != 0) {
                for (int i = 0; i < ingredients_JA.length(); i++) {
                    ingredients.add(ingredients_JA.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }
}
