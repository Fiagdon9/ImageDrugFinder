package net.takemed.imagedrugfinder.data.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ForismaticQuoteApi {

    String BASE_URL = "https://api.forismatic.com/api/";

    @GET("1.0/?method=getQuote&format=json&lang=ru")
    Call<Quote> getQuote();
}
