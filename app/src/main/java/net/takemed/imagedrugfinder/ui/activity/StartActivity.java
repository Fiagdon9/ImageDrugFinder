package net.takemed.imagedrugfinder.ui.activity;

import android.content.Intent;
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
        setContentView(R.layout.start_activity);

        ProgressBar pb = findViewById(R.id.progressBarImages);
        TextView tvAverageTime = findViewById(R.id.tvAverageTime);

        tvAverageTime.append(" " + 30+"мс");

        // TextView with ProgressBar data
        ((TextView) findViewById(R.id.tvProgressNum))
                .setText(pb.getProgress() + "/" + pb.getMax());


    }

    // button listener click
    public void onStartActivity(View view) {
        startActivity(new Intent(StartActivity.this, QuizActivity.class));
    }

}
