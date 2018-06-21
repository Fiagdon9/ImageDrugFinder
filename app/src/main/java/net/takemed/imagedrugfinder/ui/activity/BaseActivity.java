package net.takemed.imagedrugfinder.ui.activity;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {


    public void showLongToast(String text) {
        Toast.makeText(this,
                text, Toast.LENGTH_LONG).show();
    }

    public void showLongToast(@StringRes int id) {
        showLongToast(getString(id));
    }

    public void showToast(String text) {
        Toast.makeText(this,
                text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int id) {
        showToast(getString(id));
    }

}
