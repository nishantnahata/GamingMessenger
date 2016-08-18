package com.games.gamingmessenger;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SIGN_IN = "com.games.gamingmessenger.action.FOO";
    private static final String ACTION_INITIATE = "com.games.gamingmessenger.action.BAZ";

    ProgressDialog dialog;

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFoo(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_SIGN_IN);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INITIATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SIGN_IN.equals(action)) {

                handleActionFoo();
            } else if (ACTION_INITIATE.equals(action)) {

                handleActionBaz();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo() {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent i=new Intent(this,ChatList.class);
            startActivity(i);
        }
    }





}
