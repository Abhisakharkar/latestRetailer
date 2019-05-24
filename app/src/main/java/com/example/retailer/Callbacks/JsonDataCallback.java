package com.example.retailer.Callbacks;

import org.json.JSONObject;

public interface JsonDataCallback {
    void onSuccess(JSONObject responseData);
    void onFailure(JSONObject errorData);
}


