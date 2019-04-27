package com.zxning.library.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import org.apache.http.HttpStatus;

/**
 * Created by Administrator on 2015/12/22.
 */
public class CustomRequest extends Request<byte[]>{
    private final Response.Listener<byte[]> mListener;

    public CustomRequest(int method, String url, Response.Listener<byte[]> listener,Response.ErrorListener errListener) {
        super(method, url, errListener);
        mListener = listener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        if(response == null){
            return null;
        }
        if(response.statusCode != HttpStatus.SC_OK){
            return null;
        }
        byte[] bytes = response.data;
        return Response.success(bytes, null);
    }

    @Override
    protected void deliverResponse(byte[] response) {
        if (mListener != null){
            mListener.onResponse(response);
        }
    }
}
