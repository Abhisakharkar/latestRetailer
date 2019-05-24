package com.example.retailer.implementation.SignUp;

public interface SignUpContract {
    interface view{
        void showSnackbar(String message, int length);
        void gotoProfileActivity();
        void gotoSignInActivity();
    }

    interface presenter{}
}
