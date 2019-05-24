package com.example.retailer.SingletonClasses;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.retailer.Callbacks.SuccessFailureCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.GetTokenResult;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.content.ContentValues.TAG;

@Singleton
public class FirebaseAuthHelper {
    private FirebaseAuth firebaseAuth;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    public FirebaseAuthHelper(FirebaseAuth firebaseAuth, SharedPreferencesHelper sharedPreferencesHelper) {
        this.firebaseAuth = firebaseAuth;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    public void signIn(String mail, String pass, SuccessFailureCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                       fetchToken(callback);
                    } else {
                        task.getException().printStackTrace();
                        callback.onFailure();
                    }
                });
    }

    public void signUp(String mail, String pass, SuccessFailureCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        fetchToken(callback);
                    } else {
                        //getException(task);
                        task.getException().printStackTrace();
                        callback.onFailure();
                    }
                });
    }

    private void getException(Task task){

        try {
            throw task.getException();
        } catch(FirebaseAuthWeakPasswordException e) {
            e.printStackTrace();

        } catch(FirebaseAuthInvalidCredentialsException e) {
            e.printStackTrace();

        } catch(FirebaseAuthUserCollisionException e) {
            e.printStackTrace();

        } catch(Exception e) {
            e.printStackTrace();

        }
    }

    public void fetchToken(SuccessFailureCallback callback){
        firebaseAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    String idToken = task.getResult().getToken();
                    sharedPreferencesHelper.setToken(idToken);
                    callback.onSuccess();
                } else {
                    Log.d(TAG, "onComplete: firebase Get token failed");
                    callback.onFailure();
                }
            }
        });
    }


}
