package com.bettingtipsking.app.Helper;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mySingleton;
    private static Context context;
    private static RequestQueue requestQueue;

    private MySingleton(Context ctx)
    {
        context = ctx;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getmInstance(Context contxt)
    {
        if(mySingleton==null)
        {
            mySingleton = new MySingleton(contxt);
        }

        return mySingleton;
    }

    public <T>void addToRequestQueue(Request<T> request)
    {
        request.setShouldCache(false);

        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(request);
    }

    public void cancelRequests(String tag){
        requestQueue.cancelAll(tag);
        System.out.println("CANCELLED");
    }

    public static void rmoveAllRequestsFromQueue(){
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

}
