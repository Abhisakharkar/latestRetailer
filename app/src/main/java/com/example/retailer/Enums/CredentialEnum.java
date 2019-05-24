package com.example.retailer.Enums;

public enum CredentialEnum {
    OK,EMAIL_EMPTY{
        @Override
        public String toString() {
            return "Email cannot be empty";
        }
    },EMAIL_WRONG_FORMAT{
        @Override
        public String toString() {
            return "Email is in wrong format";
        }
    },PASS_EMPTY{
        @Override
        public String toString() {
            return "password cannot be empty";
        }
    },PASS_WRONG_FORMAT{
        @Override
        public String toString() {
            return "your password must contain one upper and lowercase, numeric and one of there special character !,@,#,$,%";
        }
    },PASS_NOT_SAME{
        @Override
        public String toString() {
            return "password and confirm password does not match";
        }
    };
}
