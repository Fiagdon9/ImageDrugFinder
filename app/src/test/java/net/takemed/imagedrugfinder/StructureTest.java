package net.takemed.imagedrugfinder;

import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import static net.takemed.imagedrugfinder.PackageHelper.hasClass;
import static org.junit.Assert.assertTrue;

public class StructureTest {

    @Test
    public void containUnsplashApiClass() throws Exception {
        Class apiClass = hasClass("data.retrofit.UnsplashApi");
        assertTrue("UnsplashApi must be Interface!", apiClass.isInterface());
    }

    @Test
    public void containSplashActivityClass() throws Exception {
        Class activityClass = hasClass("ui.activity.SplashActivity");

        assertTrue("SplashActivity must extends AppCompatActivity!",
                activityClass.getSuperclass() == AppCompatActivity.class);
    }

    @Test
    public void containStartActivityClass() throws Exception {
        Class activityClass = hasClass("ui.activity.StartActivity");

        assertTrue("StartActivity must extends AppCompatActivity!",
                activityClass.getSuperclass() == AppCompatActivity.class);
    }

    @Test
    public void containQuizActivityClass() throws Exception {
        Class activityClass = hasClass("ui.activity.QuizActivity");

        assertTrue("QuizActivity must extends AppCompatActivity!",
                activityClass.getSuperclass() == AppCompatActivity.class);
    }

}
