package net.takemed.imagedrugfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.takemed.imagedrugfinder.data.retrofit.UnsplashApi;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.SSLContext;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<String> imagesUrl = new ArrayList<>();
    //TODO: инкапсуляция? Что такое инкапсуляция?!
    //TODO: делай реализацию ТОЛЬКО через список
    ImageView image0;
//            image1, image2, image3, image4, image5;
    List<ImageView> myImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: RTL не нужен здесь
//        myImages.add(image0 = findViewById(R.id.image0));
//        myImages.add(image1 = findViewById(R.id.image1));
//        myImages.add(image2 = findViewById(R.id.image2));
//        myImages.add(image3 = findViewById(R.id.image3));
//        myImages.add(image4 = findViewById(R.id.image4));
//        myImages.add(image5 = findViewById(R.id.image5));
        image0 = findViewById(R.id.image0);

        //--
        //TODO: вынеси в отдельную ф-цию и оберни условием, что Android API <= 19
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException |
                KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //--

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Log
        Log.d("mlg", "retrofit.baseUrl(): " + String.valueOf(retrofit.baseUrl()));

        UnsplashApi unsplashApi = retrofit.create(UnsplashApi.class);
        Call<JsonObject> call = unsplashApi.searchPhotos("11bee5ebf6e392875ecee649ef9f4a6953e3f4bb623112d9bc3fbf4651e277d8", "computer");
        // Log
        Log.d("mlg", "Call<JSONObject> isExecuted(): " + String.valueOf(call.isExecuted()));
        Log.d("mlg", "Call<JSONObject> url: " + String.valueOf(call.request().url()));
//        Log.d("mlg", "Call<JSONObject> url: " + String.valueOf(call.request().header("small")));


        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject results = response.body().getAsJsonArray("results").get(0).getAsJsonObject();
                Gson gson = new Gson();

                // get image url
//                Log.d("mlg", "length " + results.getAsJsonObject("urls").get("small"));
                for (int i=0; i < 6; i++){
                    imagesUrl.add(response.body()
                            .getAsJsonArray("results")
                            .get(i).getAsJsonObject()
                            .getAsJsonObject("urls").get("small").toString());
                }

                Iterator imagesIterator = imagesUrl.iterator();
//                Iterator myImgsIterator = myImages.iterator();
//                while (imagesIterator.hasNext()){
//                    Log.d("mlg", "imgUrl: " + imagesIterator.next());
////                    Glide
////                            .with(getApplicationContext())
////                            .load(imagesIterator.next())
////                            .into((ImageView) myImgsIterator.next());
////                    ((ImageView) myImgsIterator.next()).setVisibility(View.VISIBLE);
//                }
//                String url = imagesIterator.next().toString();
                String url = ""+ response.body()
                        .getAsJsonArray("results")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("urls").get("small").toString();
                Log.d("mlg", url);

                Glide
                        .with(MainActivity.this)
                        .load(url)
                        .centerCrop()
                        .into(image0);
//                image0.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("mlg", "onFailure() " + t);
            }
        });
    }
}
