package com.example.mulyani.getsourcewebsite;


import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mulyani on 10/21/2017.
 */

public class ConnecttoInternet extends AsyncTaskLoader<String> {

    String result, Url;
    boolean cancel = false;

    public ConnecttoInternet(Context ctx, String url){
        super(ctx);
        Url = url;
    }

    @Override
    public String loadInBackground() {
        InputStream in = null;
        HttpURLConnection koneksi = null;

        try {
            URL myurl = new URL(Url);
            koneksi = (HttpURLConnection) myurl.openConnection();
            koneksi.setReadTimeout(10000);
            koneksi.setConnectTimeout(20000);
            koneksi.setRequestMethod("GET");
            koneksi.connect();

            if (koneksi.getResponseCode() == HttpURLConnection.HTTP_OK){
                in = koneksi.getInputStream();
                if (in != null){
                    BufferedReader buff = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line="";

                    while ((line = buff.readLine()) != null){
                        sb.append(line + "   \n");
                    }
                    return sb.toString();
                }
            }
            else {
                return "Error Response Code"+koneksi.getResponseCode();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "Unknown ERROR";
        }
        finally {
            if (in != null){
                try {
                    in.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            koneksi.disconnect();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        if (result != null || cancel){
            deliverResult(result);
        }
        else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        result = data;
    }

    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
        cancel = true;
    }
}
