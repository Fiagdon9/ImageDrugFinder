package net.takemed.imagedrugfinder.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.takemed.imagedrugfinder.R;
import net.takemed.imagedrugfinder.data.NetworkClient;
import net.takemed.imagedrugfinder.data.retrofit.GoogleCustomSearchApi;
import net.takemed.imagedrugfinder.data.retrofit.callback.ToastErrorCallback;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends BaseActivity {

    public static final String EXTRA_QUERY = "EXTRA_QUERY";
    private EditText etSearch;


    private List<ImageView> cellsIvs = new ArrayList<>();
    private Disposable clickSubscribe;

    public static Intent getIntent(Context context, String query) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.putExtra(EXTRA_QUERY, query);

        return intent;
    }

    //[Controller code]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        initData();
    }

    private void initUi() {
        initIvsList();

        etSearch = findViewById(R.id.etSearch);
        clickSubscribe = getButtonClickObservable()
                .subscribe(it -> {
                    String query = etSearch.getText().toString();

                    if (query.isEmpty()) {
                        showLongToast(R.string.error_empty_query);
                        return;
                    }

                    loadImagesFromApi(query);
                });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (clickSubscribe != null) {
            clickSubscribe.dispose();
        }
    }

    private Observable<View> getButtonClickObservable() {
        return new Observable<View>() {
            @Override
            protected void subscribeActual(Observer<? super View> observer) {
                findViewById(R.id.btnSearch)
                        .setOnClickListener(observer::onNext);
            }
        }
                .throttleFirst(2, TimeUnit.SECONDS);
    }

    //[/Controller code]

    //[UI code]

    private void initIvsList() {
        LinearLayout ll = findViewById(R.id.llImages);

        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);

            if (child instanceof ImageView) {
                cellsIvs.add((ImageView) child);
            } else if (child instanceof LinearLayout) {
                LinearLayout row = (LinearLayout) child;

                for (int j = 0; j < row.getChildCount(); j++) {
                    child = row.getChildAt(j);

                    if (child instanceof ImageView) {
                        cellsIvs.add((ImageView) child);
                    }
                }
            }
        }
    }

    private void fillImages(List<BitmapDrawable> images) {
        if (cellsIvs.size() <= images.size()) {
            for (int i = 0; i < cellsIvs.size(); i++) {
                setImage(cellsIvs.get(i), images.get(i));
            }
        } else {
            showToast(R.string.error_not_enought_urls);
        }
    }

    private void setImage(ImageView iv, Drawable drawable) {
        iv.setImageDrawable(drawable);
        iv.setVisibility(View.VISIBLE);
    }

    //[/UI code]


    //[Data code]

    private void initData() {
        Intent intent = getIntent();
        String query = intent.getStringExtra(EXTRA_QUERY);
        etSearch.setText(query);
        findViewById(R.id.btnSearch).performClick();
    }

    private void loadImagesFromApi(String textQuerySearch) {
        //bug in SDK 19 or less with using TLS 1.2
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            makeSslGreatAgain();
        }

        GoogleCustomSearchApi googleCustomSearchApi = new Retrofit.Builder()
                .baseUrl(GoogleCustomSearchApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleCustomSearchApi.class);

        googleCustomSearchApi
                .searchPhotos(textQuerySearch, 10, getString(R.string.google_key))
                .enqueue(new ToastErrorCallback<>(this,
                        jo -> mapData(jo)
                                .subscribe(this::fillImages),
                        getString(R.string.error_when_works_with_net)));
    }


    /**
     * Method take JsonObject and map it's data to list of images urls
     * or empty list if input has wrong data
     *
     * @param input - input JsonObject that contain all urls for images
     * @return list of image urls mapped from JsonObject or empty list
     */
    private Observable<List<BitmapDrawable>> mapData(JsonObject input) {
        return Observable
                .just(input)                                    //создаем поток данных из одного элемента input
                .filter(it -> it.has("items"))     //убираем из этого потока все элементы не содержащие внутри себя элемент "items"
                .map(it -> it.get("items"))                     //превращаем поток JsonObject в поток JsonElement(он один)
                .filter(JsonElement::isJsonArray)               //убираем из потока все JsonElement, которые не являются JsonArray
                .map(JsonElement::getAsJsonArray)               //превращаем поток JsonElement в JsonArray
                .flatMap(Observable::fromIterable)              //превращаем поток JsonArray в поток JsonElement(которых много)
                .filter(JsonElement::isJsonObject)              //убираем все JsonElement, которые не являются JsonObject
                .map(JsonElement::getAsJsonObject)
                .filter(it -> it.has("image"))
                .map(it -> it.get("image"))
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .filter(it -> it.has("thumbnailLink"))
                .map(it -> it.get("thumbnailLink"))
                .map(JsonElement::getAsString)
                .flatMap(it -> Observable.defer(() ->
                        NetworkClient.getInstance().loadImage(it)))
                .filter(it -> !it.isEmpty()
                        .blockingGet())
                .map(Maybe::blockingGet)
                .map(BitmapDrawable::new)
                .buffer(6)
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
