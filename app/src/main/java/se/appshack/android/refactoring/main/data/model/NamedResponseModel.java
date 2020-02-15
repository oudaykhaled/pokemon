package se.appshack.android.refactoring.main.data.model;

import com.google.gson.annotations.SerializedName;

public class NamedResponseModel {

    @SerializedName("name")
    public String name;

    @SerializedName("url")
    public String url;
}
