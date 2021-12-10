package com.example.silentworks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;

public class GoogleLogin extends OptionsMenu implements View.OnClickListener, Serializable
{
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "Login";
    // Build a GoogleSignInClient with the options specified by gso.
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionReadPhone = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int permissionWriteEx = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadEx = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionSendSMS = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.SEND_SMS);
        int permissionReceiveSMS = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.RECEIVE_SMS);
        int permissionReadContacts = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_CONTACTS);
        if(permissionReadPhone == PackageManager.PERMISSION_DENIED && permissionWriteEx == PackageManager.PERMISSION_DENIED &&
                permissionReadEx == PackageManager.PERMISSION_DENIED && permissionSendSMS == PackageManager.PERMISSION_DENIED &&
                permissionReceiveSMS == PackageManager.PERMISSION_DENIED && permissionReadContacts == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_CONTACTS}, 1); }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("407258174239-57eol2lsmhcs5e15pj9ri3ivvhmls73h.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        TextResponse autoTextResponse = new TextResponse();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        Log.v("SUCCESS!!", String.valueOf(account));
        if(account != null) {
            Intent intent = new Intent(this, CalendarActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            Log.v("SUCCESS!!", String.valueOf(account));
            Intent intent = new Intent(this, CalendarActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }
}