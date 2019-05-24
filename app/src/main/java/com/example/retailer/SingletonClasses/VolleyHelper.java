package com.example.retailer.SingletonClasses;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.retailer.Callbacks.JsonDataCallback;
import com.example.retailer.Callbacks.SuccessFailureCallback;
import com.example.retailer.R;
import com.example.retailer.di.Scopes.ApplicationContext;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.content.ContentValues.TAG;


@Singleton
public class VolleyHelper {
    private RequestQueue requestQueue;
    private InputStream inputStream ;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private FirebaseAuthHelper firebaseAuthHelper;

    private static final String BaseUrl="https://192.168.1.100";

    @Inject
    public VolleyHelper(@ApplicationContext Context context,SharedPreferencesHelper sharedPreferencesHelper,FirebaseAuthHelper firebaseAuthHelper) {
        requestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        inputStream = context.getResources().openRawResource(R.raw.myrootca);
        this.sharedPreferencesHelper=sharedPreferencesHelper;
        this.firebaseAuthHelper=firebaseAuthHelper;
    }

    public void sendRequest(String path, JSONObject params, final JSONObject requestBody, final JsonDataCallback callback) {
        String token=sharedPreferencesHelper.getToken();
        if (token==null){
            firebaseAuthHelper.fetchToken(new SuccessFailureCallback() {
                @Override
                public void onSuccess() {
                    String tokenLatest=sharedPreferencesHelper.getToken();
                    makeRequest(path,params,requestBody,callback,tokenLatest);
                }
                @Override
                public void onFailure() {
                    callback.onFailure(null);
                }
            });
        }else
            makeRequest(path,params,requestBody,callback,token);
    }


        public void makeRequest(String path, JSONObject params, final JSONObject requestBody, final JsonDataCallback callback, String token) {

        String URL = BaseUrl + path;
        int method = 0;

        Log.d(TAG, "sendRequest: headers ::: "+params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, URL, params
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
                Log.d(TAG, "onResponse: Response : "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Error : "+error.getMessage());
                callback.onFailure(null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headerMap = new HashMap<>();
                headerMap.put("Content-Type","application/json");
                headerMap.put("Authorization", token);
                return headerMap;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        requestQueue.add(jsonObjectRequest);
    }


    private SSLSocketFactory getSocketFactory() {

        CertificateFactory cf = null;
        Certificate ca=null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            ca = cf.generateCertificate(inputStream);

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);


            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {

                    Log.e("CipherUsed", session.getCipherSuite());
                    return hostname.compareTo("ubuntu") == 0; //The Hostname of your server

                }
            };

            HostnameVerifier hostnameVerifierTEMP = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };


            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifierTEMP);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            SSLSocketFactory sf = context.getSocketFactory();


            return sf;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }



}
