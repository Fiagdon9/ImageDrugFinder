package net.takemed.imagedrugfinder;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
//import retrofit2.http.Query;

public interface APIUnsplash {

    @GET("/search/photos?client_id=11bee5ebf6e392875ecee649ef9f4a6953e3f4bb623112d9bc3fbf4651e277d8&query=computer")
    Call<JsonObject> loadPhotos();
}
