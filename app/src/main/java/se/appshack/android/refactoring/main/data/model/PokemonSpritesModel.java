package se.appshack.android.refactoring.main.data.model;

import com.google.gson.annotations.SerializedName;

public class PokemonSpritesModel {

    @SerializedName("front_default")
    public String urlFront;

    @SerializedName("back_default")
    public String urlBack;
}
