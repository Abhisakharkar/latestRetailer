package com.example.retailer.implementation.Profile;

public interface ProfileContract {
    interface view{
        void showSnackbar(String message, int length);

        void gotoHomeActivity();

        void goBackToSignInAndClearData();
    }
    interface presenter{}
}
