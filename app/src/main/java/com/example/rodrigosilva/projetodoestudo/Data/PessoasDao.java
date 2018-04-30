package com.example.rodrigosilva.projetodoestudo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.rodrigosilva.projetodoestudo.Model.Pessoas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo.silva on 28/03/2018.
 */

public class PessoasDao extends SQLiteOpenHelper {
    public PessoasDao(Context context) {
        super(context, "ProjetoEstudo", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE Pessoa(id INTEGER PRIMARY KEY,nome TEXT NOT NULL,telefone TEXT,site TEXT,email TEXT,nota REAL,senha TEXT,endereco TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Pessoa";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Pessoas pessoa) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValues(pessoa);

        db.insert("Pessoa",null,dados);
    }

    @NonNull
    private ContentValues getContentValues(Pessoas pessoa) {
        ContentValues dados = new ContentValues();

        dados.put("endereco",pessoa.getEndereco());
        dados.put("nome",pessoa.getNome());
        dados.put("email",pessoa.getEmail());
        dados.put("nota",pessoa.getNota());
        dados.put("telefone",pessoa.getTelefone());
        dados.put("site",pessoa.getSite());
        dados.put("senha",pessoa.getSenha());
        return dados;
    }

    public List<Pessoas> buscaPessoa() {

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM Pessoa;";

        Cursor c = db.rawQuery(sql,null);

        List<Pessoas> pessoas = new ArrayList<>();

        while(c.moveToNext()){

            Pessoas p = new Pessoas();

            p.setNome(c.getString(c.getColumnIndex("nome")));

            p.setEmail(c.getString(c.getColumnIndex("email")));

            p.setSite(c.getString(c.getColumnIndex("site")));

            p.setEndereco(c.getString(c.getColumnIndex("endereco")));

            p.setTelefone(c.getString(c.getColumnIndex("telefone")));

            p.setNota(c.getDouble(c.getColumnIndex("nota")));

            p.setId(c.getLong(c.getColumnIndex("id")));

            p.setSenha(c.getString(c.getColumnIndex("senha")));

            pessoas.add(p);

        }
        c.close();

        return pessoas;
    }

    public void deletarPessoa(Pessoas pessoa) {

        SQLiteDatabase db= getWritableDatabase();

        String[] params = {pessoa.getId().toString()};

        db.delete("Pessoa","id=?",params);
    }

    public void altera(Pessoas pessoa) {

     SQLiteDatabase db = getWritableDatabase();

     ContentValues dados=getContentValues(pessoa);

     String[] params = {pessoa.getId().toString()};

     db.update("Pessoa",dados,"id = ?",params);

    }

}
