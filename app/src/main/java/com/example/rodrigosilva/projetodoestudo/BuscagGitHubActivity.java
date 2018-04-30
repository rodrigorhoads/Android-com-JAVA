package com.example.rodrigosilva.projetodoestudo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class BuscagGitHubActivity extends AppCompatActivity {


    private TextView mostraResultado;
    private TextView url;
    private TextView erro;
    private EditText busca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscag_git_hub);

         mostraResultado = (TextView)findViewById(R.id.recebe_resultado);
         url = (TextView)findViewById(R.id.nao_sei);
         erro = (TextView)findViewById(R.id.erro);
        busca = (EditText)findViewById(R.id.recebe_busca);
    }

    private void makeGithubSearchQuery() {
        String githubQuery = busca.getText().toString();
        URL githubSearchUrl = WebClient.buildUrl(githubQuery);
        url.setText(githubSearchUrl.toString());
        new AcessaInBackGroud(this).execute(githubSearchUrl);
    }

    public void showJsonDataView() {
        // First, make sure the error is invisible
        erro.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mostraResultado.setVisibility(View.VISIBLE);
    }
    public void showErrorMessage() {
        // First, hide the currently visible data
        mostraResultado.setVisibility(View.INVISIBLE);
        // Then, show the error
        erro.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
