package com.example.retailer.implementation.SignIn;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.retailer.Callbacks.JsonDataCallback;
import com.example.retailer.Callbacks.SuccessFailureCallback;
import com.example.retailer.Enums.CredentialEnum;
import com.example.retailer.SingletonClasses.CredentialValidator;
import com.example.retailer.SingletonClasses.FirebaseAuthHelper;
import com.example.retailer.SingletonClasses.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class SignInPresenter implements SignInContract.presenter {
    private CredentialValidator credentialValidator;
    private FirebaseAuthHelper firebaseHelper;
    private VolleyHelper volleyHelper;
    private SignInActivity activity;

    public SignInPresenter(SignInActivity activity, CredentialValidator credentialValidator, FirebaseAuthHelper firebaseHelper, VolleyHelper volleyHelper) {
        this.credentialValidator=credentialValidator;
        this.firebaseHelper=firebaseHelper;
        this.volleyHelper=volleyHelper;
        this.activity=activity;

    }

    public void signIn(String mail, String password){
        CredentialEnum result=credentialValidator.ValidateForSignIn(mail,password);
        if (result==CredentialEnum.OK){
            firebaseHelper.signIn(mail, password, new SuccessFailureCallback() {
                @Override
                public void onSuccess() {
                    volleyHelper.sendRequest("/getProfile", null, null, new JsonDataCallback() {
                        @Override
                        public void onSuccess(JSONObject responseData) {
                            try {
                                boolean manData=responseData.getBoolean("mandatoryData");
                                if (manData){
                                    boolean verificationStatus=responseData.getBoolean("verificationStatus");
                                    if (verificationStatus){
                                        activity.gotoHomeActivity();
                                    }else
                                       activity.showSnackbar("your profile has not been verified yet. you will be allowed to login after verification", Snackbar.LENGTH_SHORT);
                                }else
                                    activity.gotoProfileActivity();
                            }catch (JSONException e){
                                activity.gotoProfileActivity();
                            }
                        }

                        @Override
                        public void onFailure(JSONObject errorData) {
                            Log.d(TAG, "onFailure: error getting profile");
                            activity.showSnackbar("error getting profile", Snackbar.LENGTH_LONG);
                        }
                    });
                }
                @Override
                public void onFailure() {
                    activity.showSnackbar("sign In failed", Snackbar.LENGTH_LONG);
                }
            });
        }else {
            activity.showSnackbar(result.toString(),Snackbar.LENGTH_SHORT);
        }
    }
}
