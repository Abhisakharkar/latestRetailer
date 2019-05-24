package com.example.retailer.implementation.SignIn;

public interface SignInContract {
    interface view{
        void gotoProfileActivity();
        void gotoSignUpActivity();
        void gotoHomeActivity();
    }
    interface presenter{
        public void signIn(String mail, String password);

    }
}
