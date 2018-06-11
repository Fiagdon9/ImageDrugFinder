package net.takemed.imagedrugfinder.data.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashApi {

    String BASE_URL = "https://api.unsplash.com";

    @GET("/search/photos")
    Call<JsonObject> searchPhotos(@Query("client_id") String clientId, @Query("query") String query);

}
