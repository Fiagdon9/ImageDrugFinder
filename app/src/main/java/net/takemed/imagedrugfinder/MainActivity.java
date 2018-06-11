package net.takemed.imagedrugfinder;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.JsonArray;
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

    public static final int CELLS_COUNT = 6;

    private List<String> imagesUrl = new ArrayList<>();
    private List<ImageView> cellsIvs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initIvsList();

        //bug in SDK 19 or less with using TLS 1.2
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            makeSslGreateAgain();
        }

        loadImageFromApi();

        findViewById(R.id.btnSearch).setOnClickListener(v -> {
            showToast("Next pages");
        });
    }

    private void loadImageFromApi(){
        String clientId = getString(R.string.client_id);
        UnsplashApi unsplashApi = new Retrofit.Builder()
                .baseUrl(UnsplashApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UnsplashApi.class);
        unsplashApi.searchPhotos(clientId, "computer")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray results = response.body()
                                .getAsJsonArray("results");

                        //get image url
                        for (int i = 0; i < CELLS_COUNT; i++) {

                                String url  = takeUrl(results
                                    .get(i).getAsJsonObject()
                                    .getAsJsonObject("urls"));

                            imagesUrl.add(url);
                        }

                        if (cellsIvs.size() <= imagesUrl.size()) {
                            for (int i = 0;  i < cellsIvs.size(); i++) {
                                setImage(cellsIvs.get(i), imagesUrl.get(i));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        showToast(R.string.have_not_enough_image_views);
                    }
                });
    }

    static String takeUrl(JsonObject urls) {
        return urls.get("small").getAsString();
    }

    private void setImage(ImageView iv, String url) {
        Glide
                .with(getApplicationContext())
                .load(url)
                .into(iv);
        iv.setVisibility(View.VISIBLE);
    }

    public void showToast(String text) {
        Toast.makeText(this,
                text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int id) {
        Toast.makeText(this,
                getString(id), Toast.LENGTH_SHORT).show();
    }

    private void initIvsList() {
        TableLayout tl = findViewById(R.id.tlImages);

        for (int i = 0; i < tl.getChildCount(); i++) {
            View child = tl.getChildAt(i);

            if (child instanceof ImageView) {
                cellsIvs.add((ImageView) child);
            }
            else if (child instanceof TableRow) {
                TableRow row = (TableRow) child;

                for (int j = 0; j < row.getChildCount(); j++) {
                    child = row.getChildAt(j);

                    if (child instanceof ImageView) {
                        cellsIvs.add((ImageView) child);
                    }
                }
            }
        }
    }

    private void makeSslGreateAgain() {
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException |
                KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
