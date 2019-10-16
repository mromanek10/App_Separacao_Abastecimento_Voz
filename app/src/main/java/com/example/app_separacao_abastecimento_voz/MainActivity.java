package com.example.app_separacao_abastecimento_voz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.FactoryJSONRequest.DelegateJSONRequest;
import com.example.app_separacao.dao.UsuarioDAO;
import com.example.app_separacao.entities.Usuario;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btn_login);

        final EditText etdUsuario = (EditText) findViewById(R.id.editText2);
        final EditText etdLogin = (EditText) findViewById(R.id.editText3);

        dao = new UsuarioDAO(getBaseContext());



        //Usuario(int usuario_ID, String usuario_Login, String usuario_DtExpiracao, int usuario_IsAtivo)
      //  Usuario usr = new Usuario(33,"HUGO","pswd123",null,1);


       // Usuario usr2=  dao.getUserbyLogin("HUGO", "pswd123");

      //  boolean sucesso=false;

      //  if(usr2==null) {
      //      Toast.makeText(getApplicationContext(), "Usuário Cadastrado", Toast.LENGTH_SHORT).show();
      //  }

        //if(usr2==null) {
         //   sucesso = dao.salvar(usr);
       // }

       // if (sucesso)
       // {
      //     Log.e("sucesso","sim");
      //  }
       // else
       // {
       //     Log.d("sucesso","nao");
       // }

      //  List<Usuario> add= dao.getAllussers();

      //  Toast.makeText(getApplicationContext(), usr2.getUsuarioId(), Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usuario usr2=  dao.getUserbyLogin( etdUsuario.getText().toString(), etdLogin.getText().toString());
                if(usr2==null) {
                    Toast.makeText(MainActivity.this, "Usuário nâo cadastrado", Toast.LENGTH_SHORT).show();
                }
              //  else {
                    Intent myIntent = new Intent(view.getContext(), PecasActivity.class);
                    startActivityForResult(myIntent, 0);
              // }
            }
        });
    }
}

