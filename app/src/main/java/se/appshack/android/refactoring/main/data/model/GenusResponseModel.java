package se.appshack.android.refactoring.main.data.model;

import com.google.gson.annotations.SerializedName;

public class GenusResponseModel {

    @SerializedName("genus")
    public String genus;

    @SerializedName("language")
    public NamedResponseModel language;
}
