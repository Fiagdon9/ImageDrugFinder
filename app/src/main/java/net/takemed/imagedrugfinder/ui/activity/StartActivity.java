package net.takemed.imagedrugfinder.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.JsonObject;

import net.takemed.imagedrugfinder.R;
import net.takemed.imagedrugfinder.data.retrofit.ForismaticQuoteApi;
import net.takemed.imagedrugfinder.data.retrofit.Quote;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import io.reactivex.Observable;

import javax.net.ssl.SSLContext;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// bla bla bla bla

public class StartActivity extends AppCompatActivity {

    private ForismaticQuoteApi forismaticQuoteApi;
    private TextView tq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setData();

        //bug in SDK 19 or less with using TLS 1.2
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            makeSslGreatAgain();
        }
        tq = findViewById(R.id.tvQuote);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ForismaticQuoteApi.BASE_ULR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        forismaticQuoteApi = retrofit.create(ForismaticQuoteApi.class);
        getApiQuote(new QuoteCallback());
    }



    private void setData() {
        //вапроапро
        ProgressBar pb = findViewById(R.id.progressBarImages);
        TextView tvAverageTime = findViewById(R.id.tvAverageTime);
        TextView progressNum = findViewById(R.id.tvProgressNum);

        //set average time to text view
        String averageTime = getString(R.string.tv_average_time, 30);
        tvAverageTime.setText(averageTime);
        //set pb max and current from db data
        pb.setMax(36000);
        pb.setProgress(300);
        //set current count of resolved items and all items in db
        String progress = getString(R.string.tv_progress_num, 300, 36000);
        progressNum.setText(progress);
    }

    // button listener click
    public void onStartButtonClicked(View view) {
        startActivity(QuizActivity.getIntent(this, "Hello"));
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

    void getApiQuote(QuoteCallback quoteCallback){
        forismaticQuoteApi.getQuote().enqueue(quoteCallback);
    }

    class QuoteCallback implements Callback<JsonObject>{

        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            if (!response.isSuccessful() || response.body() == null ||
                    !response.body().has("quoteText")){
                onFailure(call, null);
                return;
            }
            String text = response.body().get("quoteText").getAsString();
            if (text.length()>70){
                onFailure(call, new QuoteLengthException());
                return;
            }
            tq.setText(text);
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            if (t instanceof QuoteLengthException){
                getApiQuote(this);
            } else {
                Toast.makeText(StartActivity.this, "Wrong Length Quote", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class QuoteLengthException extends RuntimeException{

    }


}
