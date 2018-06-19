package de.ur.aue.discuss.Global;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DiscussApplication extends Application {

    private RequestQueue mRequestQueue;

    public RequestQueue getVolleyRequestQueue() {
        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        return mRequestQueue;
    }
}