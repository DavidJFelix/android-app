package com.dinnersolutions.instameal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dinnersolutions.instameal.api.AuthManager;
import com.dinnersolutions.instameal.api.FeastedApi;
import com.dinnersolutions.instameal.api.response.TokenUserResponse;
import com.dinnersolutions.instameal.application.FeastedApplication;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    @Inject
    FeastedApi feastedApi;
    @Inject
    AuthManager authManager;

    @Bind(R.id.google_plus_button)
    SignInButton googleButton;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inject(this);
        init();
    }

    private void init() {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleButton.setSize(SignInButton.SIZE_STANDARD);
        googleButton.setScopes(gso.getScopeArray());
    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            Snackbar.make(googleButton, "Google token grabbed", Snackbar.LENGTH_SHORT).show();

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            authManager.saveBearerToken(acct.getIdToken());

            feastedApi.getTokenAndUser(new Callback<TokenUserResponse>() {
                @Override
                public void onResponse(Response<TokenUserResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Snackbar.make(googleButton, "Signed in!", Snackbar.LENGTH_SHORT).show();
                        Log.d(TAG, response.body().token.text);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(TAG, t.getMessage());
                    Snackbar.make(googleButton, "Network problem", Snackbar.LENGTH_SHORT).show();

                }
            });
        } else {
            Snackbar.make(googleButton, "Sign in problem", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //todo
                    }
                });
    }

    @OnClick(R.id.google_plus_button)
    public void onGoogleSignInClicked() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void inject(Context context) {
        FeastedApplication app = FeastedApplication.from(context);
        app.component().inject(this);
    }
}
