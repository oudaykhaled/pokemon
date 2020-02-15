package se.appshack.android.refactoring.main.data.model;

import com.google.gson.annotations.SerializedName;

public class PokemonTypeModel {

    @SerializedName("slot")
    public int slot;

    @SerializedName("type")
    public NamedResponseModel type;
}
