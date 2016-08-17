package com.games.gamingmessenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    GoogleSignIn signIn;
    private static final String TAG = "SignInActivity";
    public static final int RC_SIGN_IN=2429;
    SharedPreferences userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: ");
        Intent i2;
        i2 = getIntent();
        if (i2 != null) {
            if(i2.getBooleanExtra("EXIT", false))
                finish();
            else if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            {
                Intent i=new Intent(this,ChatList.class);
                startActivity(i);
            }
        }
        else if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent i=new Intent(this,ChatList.class);
            startActivity(i);
        }
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
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

        GoogleApiClient apiClient;
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
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
            Intent i=signIn.handleSignInResult(data);
            startActivity(i);
        }

    }

    @Override
    protected void onStop() {

        super.onStop();
        if(signIn!=null)
        {
            if (signIn.mAuthListener != null) {
                signIn.mAuth.removeAuthStateListener(signIn.mAuthListener);
            }
        }
    }
}

