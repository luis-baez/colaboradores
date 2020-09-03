package com.example.colaboradores.login;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginPresenter {
    private Context context;
    private LoginCallback loginCallback;

    public LoginPresenter(Context context, LoginCallback loginCallback) {
        this.context = context;
        this.loginCallback = loginCallback;
    }

    public void signInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            loginCallback.updateUI(account);
        } catch (ApiException e) {
            loginCallback.updateUI(null);
        }
    }

    public void onStart(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        loginCallback.updateUI(account);
    }
}
