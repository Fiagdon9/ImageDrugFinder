package net.takemed.imagedrugfinder.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * {@link #loadImage(String)} is where you should start.
 */
public class NetworkClient {

    private static final NetworkClient NETWORK_CLIENT = new NetworkClient();
    private final OkHttpClient mOkHttpClient;

    private NetworkClient() {
        mOkHttpClient = new OkHttpClient();
    }

    public static NetworkClient getInstance() {
        return NETWORK_CLIENT;
    }

    /**
     * Loads an Image from the internet.
     */
    public Observable<Maybe<Bitmap>> loadImage(String imageUrl) {
        return Observable.fromCallable(() -> {
            final Request loadRequest = new Request.Builder()
                    .url(imageUrl)
                    .build();

            try {
                final Response response = mOkHttpClient
                        .newCall(loadRequest)
                        .execute();

                return Maybe.just(BitmapFactory.decodeStream(response.body().byteStream()));
            } catch (Exception e) {
                return Maybe.empty();
            }
        });
    }

}