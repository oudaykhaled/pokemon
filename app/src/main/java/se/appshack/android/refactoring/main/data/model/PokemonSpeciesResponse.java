package se.appshack.android.refactoring.main.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonSpeciesResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("genera")
    public List<GenusResponseModel> genera;
}
