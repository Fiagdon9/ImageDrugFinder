package net.takemed.imagedrugfinder.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.takemed.imagedrugfinder.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setData();
    }

    private void setData() {
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

}
