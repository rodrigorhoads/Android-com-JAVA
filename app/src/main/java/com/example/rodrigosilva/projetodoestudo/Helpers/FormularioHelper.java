package com.example.rodrigosilva.projetodoestudo.Helpers;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.rodrigosilva.projetodoestudo.FormularioActivity;
import com.example.rodrigosilva.projetodoestudo.Model.Pessoas;
import com.example.rodrigosilva.projetodoestudo.R;

/**
 * Created by rodrigo.silva on 28/03/2018.
 */

public class FormularioHelper {

    private final EditText campo_telefone;
    private final EditText campo_site;
    private final RatingBar campo_nota;
    private final EditText campo_email;
    private final EditText campo_nome;
    private final EditText campo_endereco;
    private final EditText campo_senha;
    private final Button btn_alterarSenha;
    private Pessoas pessoa;



    public FormularioHelper(FormularioActivity activity){
         campo_nome=(EditText)activity.findViewById(R.id.edt_nome_pessoa);

         campo_email=(EditText)activity.findViewById(R.id.edt_email_pessoa);

         campo_site=(EditText)activity.findViewById(R.id.edt_site_pessoa);

         campo_nota = (RatingBar)activity.findViewById(R.id.edt_nota_pessoa);

         campo_telefone=(EditText)activity.findViewById(R.id.edt_telefone_pessoa);

        campo_endereco=(EditText)activity.findViewById(R.id.edt_endereco_pessoa);

        campo_senha = (EditText)activity.findViewById(R.id.edt_senha_pessoa);

        btn_alterarSenha =(Button) activity.findViewById(R.id.btn_editarSenha);

        pessoa=new Pessoas();
    }

    public Pessoas pegaPessoa() {

        pessoa.setEmail(campo_email.getText().toString());

        pessoa.setNome(campo_nome.getText().toString());

        pessoa.setNota(Double.valueOf(campo_nota.getProgress()));

        pessoa.setTelefone(campo_telefone.getText().toString());

        pessoa.setSite(campo_site.getText().toString());

        pessoa.setEndereco(campo_endereco.getText().toString());

        pessoa.setSenha(campo_senha.getText().toString());

        return pessoa;
    }

    public void preencheFormulario(Pessoas pessoa) {

        campo_nome.setText(pessoa.getNome());
        campo_endereco.setText(pessoa.getEndereco());
        campo_email.setText(pessoa.getEmail());
        campo_nota.setProgress(pessoa.getNota().intValue());
        campo_site.setText(pessoa.getSite());
        campo_telefone.setText(pessoa.getTelefone());
        campo_senha.setText(pessoa.getSenha());
        campo_senha.setVisibility(View.INVISIBLE);
        btn_alterarSenha.setVisibility(View.VISIBLE);
        this.pessoa=pessoa;
    }
    public void alteraSenha(){
        btn_alterarSenha.setVisibility(View.INVISIBLE);
        campo_senha.setVisibility(View.VISIBLE);
    }
}
