package net.takemed.imagedrugfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.JsonObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException |
                KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Log
        Log.d("mlg", "retrofit.baseUrl(): " + String.valueOf(retrofit.baseUrl()));

        APIUnsplash apiUnsplash = retrofit.create(APIUnsplash.class);
        Call<JsonObject> call = apiUnsplash.loadPhotos();
        // Log
        Log.d("mlg", "Call<JSONObject> isExecuted(): " + String.valueOf(call.isExecuted()));
        Log.d("mlg", "Call<JSONObject> url: " + String.valueOf(call.request().url()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("mlg", "length " + String.valueOf(response.body().size()));

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("mlg", "onFailure() " + t);
            }
        });
    }
}
