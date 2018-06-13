package net.takemed.imagedrugfinder.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.takemed.imagedrugfinder.R;
import net.takemed.imagedrugfinder.data.retrofit.UnsplashApi;
import net.takemed.imagedrugfinder.data.retrofit.callback.ToastErrorCallback;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends BaseActivity {


    private List<ImageView> cellsIvs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initIvsList();
        loadImagesFromApi();
    }


    //[UI code]

    private void initIvsList() {
        TableLayout tl = findViewById(R.id.tlImages);

        for (int i = 0; i < tl.getChildCount(); i++) {
            View child = tl.getChildAt(i);

            if (child instanceof ImageView) {
                cellsIvs.add((ImageView) child);
            } else if (child instanceof TableRow) {
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

    private void fillImages(List<String> imagesUrl) {
        if (cellsIvs.size() <= imagesUrl.size()) {
            for (int i = 0; i < cellsIvs.size(); i++) {
                setImage(cellsIvs.get(i), imagesUrl.get(i));
            }
        } else {
            showToast("Hey man, you have not enough urls");
        }
    }

    private void setImage(ImageView iv, String url) {
        Glide
                .with(getApplicationContext())
                .load(url)
                .into(iv);
        iv.setVisibility(View.VISIBLE);
    }

    //[/UI code]


    //[Data code]

    private void loadImagesFromApi() {
        String clientId = getString(R.string.client_id);

        //bug in SDK 19 or less with using TLS 1.2
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            makeSslGreatAgain();
        }

        UnsplashApi unsplashApi = new Retrofit.Builder()
                .baseUrl(UnsplashApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UnsplashApi.class);

        unsplashApi
                .searchPhotos(clientId, "computer")
                .enqueue(new ToastErrorCallback<>(this,
                        jo -> {
                            List<String> imagesUrl = mapData(jo);
                            fillImages(imagesUrl);
                        },
                        getString(R.string.have_not_enough_image_views)));
    }


    /**
     * Method take JsonObject and map it's data to list of images urls
     * or empty list if input has wrong data
     *
     * @param input - input JsonObject that contain all urls for images
     * @return list of image urls mapped from JsonObject or empty list
     */
    private List<String> mapData(JsonObject input) {
        List<String> result = new ArrayList<>();

        if (input.has("results") &&
                input.get("results").isJsonArray()) {
            JsonArray results = input.getAsJsonArray("results");

            //get image url
            for (int i = 0; i < 6; i++) {
                JsonElement element = results.get(i);

                if (element.isJsonObject() &&
                        element.getAsJsonObject()
                                .has("urls") &&
                        element.getAsJsonObject()
                                .get("urls").isJsonObject() &&
                        element.getAsJsonObject()
                                .get("urls").getAsJsonObject()
                                .has("small")) {

                    String url = element
                            .getAsJsonObject()
                            .getAsJsonObject("urls")
                            .get("small").getAsString();

                    result.add(url);
                }
            }
        }

        return result;
    }

    /**
     * this function fix ssl+tls v1.2 bug on devices with API <= 19
     */
    private void makeSslGreatAgain() {
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException |
                KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            //we need to finish activity if device has no play services :c
            finish();
        }
    }

    //[/Data code]
}
