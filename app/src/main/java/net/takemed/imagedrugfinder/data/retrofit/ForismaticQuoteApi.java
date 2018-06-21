package net.takemed.imagedrugfinder.data.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ForismaticQuoteApi {

    // https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=ru
    String BASE_ULR = "https://api.forismatic.com/";

    @GET("/api/1.0/?method=getQuote&format=json&lang=ru")
    Call<JsonObject> getQuote();
}
