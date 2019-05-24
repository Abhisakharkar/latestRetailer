package com.example.retailer.SingletonClasses;

import android.util.Patterns;

import com.example.retailer.Enums.CredentialEnum;

import java.util.regex.Pattern;

import javax.inject.Inject;

import static com.example.retailer.Enums.CredentialEnum.EMAIL_EMPTY;
import static com.example.retailer.Enums.CredentialEnum.EMAIL_WRONG_FORMAT;
import static com.example.retailer.Enums.CredentialEnum.PASS_EMPTY;
import static com.example.retailer.Enums.CredentialEnum.PASS_NOT_SAME;
import static com.example.retailer.Enums.CredentialEnum.PASS_WRONG_FORMAT;

public class CredentialValidator {
    @Inject
    public CredentialValidator(){}

    public CredentialEnum ValidateForSignIn(String mail, String pass){
        //String mail = mailEditable.toString().trim();
        if (mail.isEmpty()) {
            return EMAIL_EMPTY;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            return EMAIL_WRONG_FORMAT;
        }

        //String pass = passEditable.toString().trim();
        if (pass.isEmpty()) {
            return PASS_EMPTY;
        }

        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$\\%]{8,24}");
        if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            return PASS_WRONG_FORMAT;
        }
        return CredentialEnum.OK;

    }

    public CredentialEnum ValidateForSignUp(String mail, String pass, String confirmPass){
        if (mail.isEmpty()) {
            return EMAIL_EMPTY;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            return EMAIL_WRONG_FORMAT;
        }

        if (pass.isEmpty() || confirmPass.isEmpty()) {
            return PASS_EMPTY;
        }

        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$\\%]{8,24}");
        if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            return PASS_WRONG_FORMAT;
        }

        if (!pass.equals(confirmPass)) {
            return PASS_NOT_SAME;
        }


        return CredentialEnum.OK;
    }


}
