package com.example.rodrigosilva.projetodoestudo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.net.URL;

/**
 * Created by rodrigo.silva on 28/03/2018.
 */

public class AcessaInBackGroud extends AsyncTask<URL,Void,String> {
    private Activity activity;

    public AcessaInBackGroud(BuscagGitHubActivity activity){
        this.activity=activity;
    }
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onPreExecute() {
        mLoadingIndicator = (ProgressBar)activity.findViewById(R.id.progress_bar);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }



    @Override
    protected String doInBackground(URL... parametro) {
        URL busscaUrl = parametro[0];
        String gitHubResultado = null;
        try{
           gitHubResultado = WebClient.getResponseFromHttpUrl(busscaUrl);
        }catch(IOException e){
            e.printStackTrace();
        }
        return gitHubResultado;
    }

    @Override
    protected void onPostExecute(String s) {
        mLoadingIndicator = (ProgressBar)activity.findViewById(R.id.progress_bar);

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (s!=null && !s.equals("")){
            TextView  mSearchResultsTextView=(TextView)activity.findViewById(R.id.recebe_busca);
            mSearchResultsTextView.setVisibility(View.VISIBLE);
            mSearchResultsTextView.setText(s);
        }else{

        }

    }
}
