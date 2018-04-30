package com.example.rodrigosilva.projetodoestudo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
private CallbackManager callbackManager;
EditText email;
ProgressDialog mDialog;
ImageView imgAvatar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
         //new AcessaInBackGroud(this).execute();


        callbackManager = CallbackManager.Factory.create();
        imgAvatar=(ImageView)findViewById(R.id.iv_foto_formulario);
        email=(EditText)findViewById(R.id.edt_email_pessoa);

        final LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog=new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Obtendo dados");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                             mDialog.dismiss();
//                             getData(object);
                            Intent vaiForn = new Intent(MainActivity.this,FormularioActivity.class);
                        try {
                            vaiForn.putExtra("email",object.getString("email"));
                            URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
                            vaiForn.putExtra("id",object.getString("id"));
                            vaiForn.putExtra("foto",profile_picture);
                            vaiForn.putExtra("nome",object.getString("name"));

                            startActivity(vaiForn);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if(AccessToken.getCurrentAccessToken()!=null){
            Intent intent  = new Intent(MainActivity.this,ListaPessoasActivity.class);
            startActivity(intent);
            finish();
        }

    }

//    private void getData(JSONObject object) {
//        try{
//            URL profile_picture = new URL("htttp://graph.facebook.com/"+object.getString("id")+"/picture?width=250&heigth=250");
//
//            Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);
//            email.setText(object.getString("email"));
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    private void printHashKey() {
//        try{
//            PackageInfo info = getPackageManager().getPackageInfo("com.example.rodrigosilva.projetodoestudo", PackageManager.GET_SIGNATURES);
//            for(Signature signature:info.signatures){
//                MessageDigest digest = MessageDigest.getInstance("SHA");
//                digest.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(digest.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
}

