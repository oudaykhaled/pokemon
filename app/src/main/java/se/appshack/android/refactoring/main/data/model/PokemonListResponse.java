package se.appshack.android.refactoring.main.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonListResponse {

    @SerializedName("count")
    public int count;

    @SerializedName("next")
    public String next;

    @SerializedName("previous")
    public String previous;

    @SerializedName("results")
    public List<NamedResponseModel> results;
}
