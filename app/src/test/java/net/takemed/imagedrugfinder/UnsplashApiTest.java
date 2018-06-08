package net.takemed.imagedrugfinder;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static junit.framework.Assert.fail;
import static net.takemed.imagedrugfinder.PackageHelper.hasClass;
//import static org.junit.Assert.fail;

public class UnsplashApiTest {

    private Class apiClass;

    @Before
    public void setUp() throws Exception {
        apiClass = hasClass("data.retrofit.UnsplashApi");
    }

    @Test
    public void doesUnsplashApiContainRequiredMethod() {
        Method[] methods = apiClass.getDeclaredMethods();

        if(methods.length != 1) {
            fail("UnsplashApi requires one method!");
        }

        if(!methods[0].getName().equals("searchPhotos")) {
            fail("UnsplashApi must implement \"searchPhotos\" method!");
        }
    }

    @Test
    public void doesUnsplashApiMethodHasExpectedSignature() {
        Method[] methods = apiClass.getDeclaredMethods();

        if(methods[0].getReturnType() != Call.class) {
            fail("\"searchPhotos\" method must return Call<JsonObject>!");
        }

        if(methods[0].getDeclaredAnnotations().length != 1) {
            fail("\"searchPhotos\" method must declare only one annotation!");
        }

        if(methods[0].getDeclaredAnnotations()[0].getClass() == GET.class) {
            fail("\"searchPhotos\" method must declare only @GET annotation!");
        }

        String value = methods[0].getAnnotation(GET.class).value();
        if(!value.equals("/search/photos") &&
                !value.equals("/search/photos/")) {
            fail("\"searchPhotos\" method must declare value \"/search/photos\"" +
                    " into @GET annotation!");
        }

        Parameter[] parameters = methods[0].getParameters();
        if(parameters.length != 2) {
            fail("\"searchPhotos\" method must declare two parameters!");
        }

        if(!parameters[0].getName().equals("clientId")) {
            fail("\"searchPhotos\" method must declare first parameters as \"clientId\"!");
        }

        if(!parameters[1].getName().equals("query")) {
            fail("\"searchPhotos\" method must declare second parameters as \"query\"!");
        }

        Annotation[] annotations = parameters[0].getAnnotations();
        if(annotations.length != 1) {
            fail("\"searchPhotos\" method must declare first parameters with only one annotation!");
        }

        if(annotations[0].getClass() != Query.class) {
            fail("\"searchPhotos\" method must declare first parameters with @Query annotation!");
        }

        if(!((Query) annotations[0]).value().equals("client_id")) {
            fail("\"searchPhotos\" method must declare first parameters with @Query annotation that contain \"clientId\" value!");
        }

        annotations = parameters[1].getAnnotations();
        if(annotations.length != 1) {
            fail("\"searchPhotos\" method must declare second parameters with only one annotation!");
        }

        if(annotations[0].getClass() != Query.class) {
            fail("\"searchPhotos\" method must declare second parameters with @Query annotation!");
        }

        if(!((Query) annotations[0]).value().equals("query")) {
            fail("\"searchPhotos\" method must declare second parameters with @Query annotation that contain \"query\" value!");
        }
    }

}
