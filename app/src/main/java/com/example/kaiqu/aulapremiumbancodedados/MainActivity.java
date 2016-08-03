package com.example.kaiqu.aulapremiumbancodedados;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.print.PrintDocumentInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editNome, editContato, editTipo;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.editNome);
        editContato = (EditText) findViewById(R.id.editContato);
        editTipo = (EditText) findViewById(R.id.editTipo);

        db = openOrCreateDatabase("ContatosDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contatos (Nome VARCHAR, contato VARCHAR, Tipo VARCHAR);");

    }

    public void btnAdd(View view){
        if (editNome.getText().toString().trim().length() == 0 || editContato.getText().toString().trim().length() == 0 || editTipo.getText().toString().trim().length() == 0){
            showMessage("Erro", "Preencha Corretamente os Valores");
            return;
        }
        db.execSQL("INSERT INTO contatos VALUES('"+editNome.getText()+"','"+editContato.getText()+"','"+editTipo.getText()+"');");
        showMessage("Ok", "Dados Gravados");
        clearText();
    }

    public void btnDel (View view){
        if (editNome.getText().toString().trim().length() == 0){
            showMessage("Erro", "Entre com o Nome");
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (c.moveToFirst()){
            db.execSQL("DELETE FROM contatos WHERE Nome='"+editNome.getText()+"'");
            showMessage("Sucesso", "Dados Deletados");
        }
        else{
            showMessage("Erro", "Invalido");
        }
        clearText();
    }

    public void btnSalvarEdit (View view){
        if (editNome.getText().toString().trim().length() == 0){
            showMessage("Erro", "Favor entrar com o Nome");
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (c.moveToFirst()){
            db.execSQL("UPDATE contatos SET Nome='"+editNome.getText()+"', Contato='"+editContato.getText()+"', Tipo='"+editTipo.getText()+"' WHERE Nome='"+editNome.getText()+"'");
            showMessage("Sucesso", "Dados Editados");
        }
        else{
            showMessage("Erro", "Faça uma busca Primeiro");
            clearText();
        }

    }

    public void btnBuscarContato (View view){
        if (editNome.getText().toString().trim().length() == 0){
            showMessage("Erro", "Favor entrar com o Nome");
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (c.moveToFirst()){
            editNome.setText(c.getString(0));
            editContato.setText(c.getString(1));
            editTipo.setText(c.getString(2));
        }
        else{
            showMessage("Erro", "Nome Invalido");
            clearText();
        }

    }

    public void btnListarContatos (View view){

        Cursor c = db.rawQuery("SELECT * FROM contatos", null);
        if (c.getCount() == 0){
            showMessage("Erro", "Nada Encontrado");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()){
            buffer.append("Nome: " + c.getString(0) + "\n");
        }
        showMessage("Detalhes dos Contatos", buffer.toString());
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText(){
        editNome.setText("");
        editContato.setText("");
        editTipo.setText("");
    }

    /*
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings){
            return true
        }

        return super.onOptionsItemSelected(item);
    }*/
}
