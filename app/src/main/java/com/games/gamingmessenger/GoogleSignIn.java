package com.games.gamingmessenger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;


public class GoogleSignIn{
    GoogleSignInAccount account;
    GoogleApiClient apiClient;
    Context c;
    ProgressDialog dialog;
    Intent i=null;
    public static FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public GoogleSignIn( GoogleApiClient apiClient, final Context c) {
        this.apiClient = apiClient;
        this.c = c;
        Log.d(MainActivity.TAG, "GoogleSignIn: show progress dialog");
        showProgressDialog();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    hideProgressDialog();
                    i=new Intent(c,ChatList.class);
                    i.putExtra(MainActivity.USER_NAME,user.getDisplayName());
                    c.startActivity(i);
                    // User is signed in
                } else {
                    // User is signed out
                }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
        mAuth.addAuthStateListener(mAuthListener);
    }

    public Intent signIn()
    {
        Log.d(MainActivity.TAG, "signIn: ");
        Intent signIntent= Auth.GoogleSignInApi.getSignInIntent(apiClient);
        return signIntent;
    }
    public void handleSignInResult(Intent data)
    {
        GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if(result.isSuccess())
        {
            Log.d(MainActivity.TAG, "handleSignInResult: "+result.isSuccess());
            account=result.getSignInAccount();
            firebaseAuthWithGoogle(account);
            Toast.makeText(c, "Hello "+account.getDisplayName()+"!", Toast.LENGTH_LONG).show();

        } else {
            hideProgressDialog();
            Toast.makeText(c, "handleSignReturns null code:"
                   + result.getStatus().getStatusCode(), Toast.LENGTH_SHORT).show();
            Log.d(MainActivity.TAG, "handleSignInResult msg: " + result.getStatus().getStatusMessage());
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) c, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(c, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }
    public void showProgressDialog()
    {
        if(dialog==null)
        {
            dialog=new ProgressDialog(c);
            dialog.setMessage("Loading");
            dialog.setIndeterminate(true);
        }
        if(!dialog.isShowing())
            dialog.show();
    }
    public void hideProgressDialog()
    {
        if(dialog!=null)
        {
            Log.d(MainActivity.TAG, "hideProgressDialog: ");
            if(dialog.isShowing())
                dialog.hide();
        }
    }

}
