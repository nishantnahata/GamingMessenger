package com.games.gamingmessenger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;


public class GoogleSignIn{
    private static final String TAG = "SignInActivity";
    SharedPreferences userDetails;
    GoogleSignInAccount account;
    GoogleApiClient apiClient;
    Context c;

    public GoogleSignIn(SharedPreferences userDetails, GoogleApiClient apiClient, Context c) {
        this.userDetails = userDetails;
        this.apiClient = apiClient;
        this.c = c;
    }

    public Intent signIn()
    {
        Intent signIntent= Auth.GoogleSignInApi.getSignInIntent(apiClient);
        return signIntent;
    }
//    private void signOut()
//    {
//        Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(new ResultCallback<Status>() {
//            @Override
//            public void onResult(Status status) {
//                Toast.makeText(c, "Signed out Succesfully!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
    public void handleSignInResult(Intent data)
    {
        GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if(result.isSuccess())
        {
            account=result.getSignInAccount();
            Toast.makeText(c, "Hello "+account.getDisplayName()+"!", Toast.LENGTH_LONG).show();
        }
    }

}
