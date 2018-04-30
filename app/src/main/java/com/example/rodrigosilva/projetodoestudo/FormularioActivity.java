package com.example.rodrigosilva.projetodoestudo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rodrigosilva.projetodoestudo.Data.PessoasDao;
import com.example.rodrigosilva.projetodoestudo.Helpers.FormularioHelper;
import com.example.rodrigosilva.projetodoestudo.Model.Pessoas;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class FormularioActivity extends AppCompatActivity {
private FormularioHelper helperFormulario;
    private Button btn_editarSenha;
    private Button btn_foto;
    private static final int REQUEST_IMAGE_CAPTURE=1;
    private  ImageView fotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helperFormulario = new FormularioHelper(this);

        Intent intentPessoa = (Intent) getIntent();

        Pessoas pessoa = (Pessoas) intentPessoa.getSerializableExtra("pessoa");

        if(pessoa!=null) {
            helperFormulario.preencheFormulario(pessoa);
        }

        Intent veioLogin = getIntent();

        fotoImageView = (ImageView)findViewById(R.id.iv_foto_formulario);
        EditText email = (EditText) findViewById(R.id.edt_email_pessoa);
        if(veioLogin.hasExtra("email")) {
            email.setText(veioLogin.getSerializableExtra("email").toString());
        }
        EditText nome = (EditText) findViewById(R.id.edt_nome_pessoa);
        if(veioLogin.hasExtra("nome")) {
            nome.setText(veioLogin.getSerializableExtra("nome").toString());
        }
        if(veioLogin.hasExtra("foto")) {
            Picasso.with(this).load(veioLogin.getSerializableExtra("foto").toString()).into(fotoImageView);
            fotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }


        btn_foto=(Button)findViewById(R.id.btn_adicionar_foto);
        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE);
            }
        });

        btn_editarSenha = (Button)findViewById(R.id.btn_editarSenha);
        btn_editarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFormulario.alteraSenha();
            }
        });
    }
    private void dispatchTakePictureIntent(int REQUEST_IMAGE_CAPTURE){
        Intent tiraFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tiraFoto.resolveActivity(getPackageManager())!=null){
        File arquivoFoto = null;
            try {
                arquivoFoto = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(arquivoFoto!=null){
                Uri utiFoto = FileProvider.getUriForFile(this,"com.example.android.fileprovider",arquivoFoto);
                tiraFoto.putExtra(MediaStore.EXTRA_OUTPUT,utiFoto);
            }
            startActivityForResult(tiraFoto,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE&& resultCode==RESULT_OK){
            Bundle extras= data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            fotoImageView =(ImageView) findViewById(R.id.iv_foto_formulario);
            fotoImageView.setImageBitmap(image);
            fotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);

        return super.onCreateOptionsMenu(menu);
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_confirma_formulario:

                Pessoas pessoa = helperFormulario.pegaPessoa();

                Toast.makeText(FormularioActivity.this,"Salvou garoto :" +pessoa.getNome() ,Toast.LENGTH_LONG).show();

                PessoasDao dao = new PessoasDao(this);

                if(pessoa.getId()!=null){
                    dao.altera(pessoa);
                }else {
                    dao.insere(pessoa);
                }
                dao.close();
                Intent vaiLista = new Intent(FormularioActivity.this,ListaPessoasActivity.class);
                startActivity(vaiLista);
                finish();

                break ;
        }
        return super.onOptionsItemSelected(item);
    }
}
