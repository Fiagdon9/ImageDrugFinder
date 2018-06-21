package net.takemed.imagedrugfinder.data.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleCustomSearchApi {

    String BASE_URL = "https://www.googleapis.com/customsearch/";

    @GET("v1?imgType=photo&searchType=image&cx=006383497312255553337:uj-tpjzmzj8")
    Call<JsonObject> searchPhotos(@Query("q") String query, @Query("num") int num,
                                  @Query("key")String key);

}
