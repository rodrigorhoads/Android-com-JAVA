package com.example.rodrigosilva.projetodoestudo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rodrigosilva.projetodoestudo.Data.PessoasDao;
import com.example.rodrigosilva.projetodoestudo.Model.Pessoas;

import java.util.List;
import java.util.zip.Inflater;

public class ListaPessoasActivity extends AppCompatActivity {

    private ListView listViewPessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);

        carregaListaPessoas();

        listViewPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Pessoas pessoa = (Pessoas) lista.getItemAtPosition(position);
                Intent intentPreenchForm = new Intent(ListaPessoasActivity.this,FormularioActivity.class);
                intentPreenchForm.putExtra("pessoa",pessoa);
                startActivity(intentPreenchForm);
            }
        });

        Button btn_formulario= (Button)findViewById(R.id.btn_adicionar_pessoa);
        btn_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaipFormulario = new Intent(ListaPessoasActivity.this,FormularioActivity.class);
                startActivity(vaipFormulario);
            }
        });

        registerForContextMenu(listViewPessoas);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        final Pessoas pessoa= (Pessoas) listViewPessoas.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                PessoasDao dao = new PessoasDao(ListaPessoasActivity.this);
                dao.deletarPessoa(pessoa);
                dao.close();
                carregaListaPessoas();
                return false;
            }
        });

        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(ListaPessoasActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ListaPessoasActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);
                }else{
                Intent intentligar = new Intent(Intent.ACTION_VIEW);
                intentligar.setData(Uri.parse("tel:"+pessoa.getTelefone()));
                startActivity(intentligar);
                }
                return false;
            }
        });


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pessoa,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_mapa:
                Intent intent1 = new Intent(ListaPessoasActivity.this, MapsActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_busca:
                Intent intentGit = new Intent(ListaPessoasActivity.this,BuscagGitHubActivity.class);
                startActivity(intentGit);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaListaPessoas() {
        PessoasDao dao = new PessoasDao(this);

        List<Pessoas> pessoasList = dao.buscaPessoa();

        listViewPessoas = (ListView)findViewById(R.id.lista_pessoa);

        ArrayAdapter<Pessoas> arrayAdapter = new ArrayAdapter<Pessoas>(ListaPessoasActivity.this,android.R.layout.simple_list_item_1,pessoasList);

        listViewPessoas.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaPessoas();

    }
}
