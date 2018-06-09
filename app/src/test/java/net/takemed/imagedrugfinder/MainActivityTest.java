package net.takemed.imagedrugfinder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class MainActivityTest {

    private List<JsonObject> urls;

    @Before
    public void setUp() {
        urls = new ArrayList<>();
        Gson gson = new Gson();

        urls.add(gson.fromJson("{\"raw\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=54be518cd3066afbb91d8e0cec1ca95a\",\"full\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=308458917963ae6f2edbffb4f0f16441\",\"regular\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=ba941321837bdbe13ad7dfeefacbdd22\",\"small\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=c84338ff9d73b32340fbb0e407d79206\",\"thumb\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=1162d451cacfe7cfcd14262e68a65c82\"}", JsonObject.class));
        urls.add(gson.fromJson("{\"raw\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=54be518cd3066afbb91d8e0cec1ca95a\",\"full\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=308458917963ae6f2edbffb4f0f16441\",\"regular\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=ba941321837bdbe13ad7dfeefacbdd22\",\"small\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=c84338ff9d73b32340fbb0e407d79206\",\"thumb\":\"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=1162d451cacfe7cfcd14262e68a65c82\"}", JsonObject.class));
        urls.add(gson.fromJson("{\"raw\":\"https://images.unsplash.com/photo-1515343480029-43cdfe6b6aae?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=9460a168eb28a1667c5426314df73bfb\",\"full\":\"https://images.unsplash.com/photo-1515343480029-43cdfe6b6aae?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=ecf7ca3d475fc3b226706efc65ed14e3\",\"regular\":\"https://images.unsplash.com/photo-1515343480029-43cdfe6b6aae?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=29f3d9ff65dc5d0bd9da96df23a35fbc\",\"small\":\"https://images.unsplash.com/photo-1515343480029-43cdfe6b6aae?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=f5ce0b053cde13fc540038510e6b8b54\",\"thumb\":\"https://images.unsplash.com/photo-1515343480029-43cdfe6b6aae?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=666f3b2254f0d5119bb820d2e988df85\"}", JsonObject.class));
        urls.add(gson.fromJson("{\"raw\":\"https://images.unsplash.com/photo-1511376777868-611b54f68947?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=6985807b093c680bb1865809e8fb657c\",\"full\":\"https://images.unsplash.com/photo-1511376777868-611b54f68947?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=5fc1c638b3761a38e44bb2443a30db73\",\"regular\":\"https://images.unsplash.com/photo-1511376777868-611b54f68947?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=6805ac333c48a59d901c2c3d8ed02028\",\"small\":\"https://images.unsplash.com/photo-1511376777868-611b54f68947?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=2dad3a9f01e9dd56c3e82557d18bb4e8\",\"thumb\":\"https://images.unsplash.com/photo-1511376777868-611b54f68947?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI4NDk2fQ&s=e3f84e2b571621295db7c571fe11e113\"}", JsonObject.class));
    }

    @Test
    public void doesTakeUrlReturnsValidResult() {
        for (JsonObject url : urls) {
            String imageUrl = MainActivity.takeUrl(url);

            if (!imageUrl.matches("http://images\\.unsplash\\.com/photo-.+")) {

                fail("MainActivity.takeUrl(...) must return url string in format \"http://images.unsplash.com/photo-...\"");
            }
        }
    }

}