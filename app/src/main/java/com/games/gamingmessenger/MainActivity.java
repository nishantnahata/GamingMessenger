package com.games.gamingmessenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    //TODO:COMPLETE THIS APP GUYS :P

    //TODO: LOGIN ACTIVITY

    //TODO: USER'S FRIENDLIST LIST ACTIVITY

    //TODO: CHAT

    //TODO: GAMES - PENALTY SHOOT OUT, JUGGLING, TIC TAC TOE,BASKETBALL AND SOME MORE GAMES...

    //TODO : DO NOT WRITE BIG CODES IN ACTIVITIES ...RATHER MAKE SEPARATE CLASSES

    GoogleSignIn signIn;
    private static final String TAG = "SignInActivity";
    public static final int RC_SIGN_IN=2401;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSignIn();
            }
        });
    }

    public void getSignIn()
    {
        SharedPreferences userDetails;
        GoogleApiClient apiClient;
        userDetails=getPreferences(Context.MODE_PRIVATE);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        signIn=new GoogleSignIn(userDetails,apiClient,this);

        startActivityForResult(signIn.signIn(),RC_SIGN_IN);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            signIn.handleSignInResult(data);
        }

    }
}

