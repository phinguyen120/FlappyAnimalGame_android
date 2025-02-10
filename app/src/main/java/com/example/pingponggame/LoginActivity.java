package com.example.pingponggame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pingponggame.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login_with_gmail,btn_loginFB;
    private LoginButton btn_login_with_facebook;
    private CallbackManager callbackManager;
    FirebaseAuth auth;
    int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnPlaynow = findViewById(R.id.btn_playnow);
        btn_login_with_gmail = findViewById(R.id.btnLoginGmail);
        btn_login_with_facebook = findViewById(R.id.btnLoginfacebook);
        btn_loginFB = findViewById(R.id.btnLoginfb);
        auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        /*
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomePageActivity.class));
            finish();
        }*/

        btnPlaynow.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            Variable.login = false;
            startActivity(intent);
        });
        btn_loginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_login_with_facebook.performClick();
            }
        });

        btn_login_with_gmail.setOnClickListener(view -> signInWithGoogle());
        btn_login_with_facebook.setReadPermissions("email", "public_profile");
        btn_login_with_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookLogin", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FacebookLogin", "facebook:onCancel");
                Toast.makeText(LoginActivity.this, "Facebook login cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FacebookLogin", "facebook:onError", error);
                Toast.makeText(LoginActivity.this, "Facebook login failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Intent signInIntent = GoogleSignIn.getClient(this, gso).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                String idToken = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class).getIdToken();
                firebaseAuthWithGoogle(idToken);
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FacebookLogin", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("FacebookLogin", "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(this, "Signed in as " + (user != null ? user.getDisplayName() : "unknown"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, HomePageActivity.class);
                        Variable.login = true;
                        startActivity(intent);
                        finish();
                    } else {
                        Log.w("FacebookLogin", "signInWithCredential:failure", task.getException());
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(this, "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            // Pass the user name to the HomePageActivity
                            Intent intent = new Intent(this, HomePageActivity.class);
                            Variable.login = true;
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
