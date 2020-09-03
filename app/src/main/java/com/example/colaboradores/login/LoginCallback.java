package com.example.colaboradores.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface LoginCallback {

    void updateUI(GoogleSignInAccount account);
}
