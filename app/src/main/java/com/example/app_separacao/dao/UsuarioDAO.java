package com.example.app_separacao.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.app_separacao.dbhelper.DbGateway;
import com.example.app_separacao.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final String TABLE_Usuario = "Usuario";
    private DbGateway gw;

    public UsuarioDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);

    }

    public boolean salvar(Usuario usuario){
        ContentValues cv = new ContentValues();
       // cv.put("Usuario_ID", usuario.getUsuarioId());
        cv.put("Usuario_Login", usuario.getusuarioLogin());
        cv.put("Usuario_Senha", usuario.getUsuarioSenha());
       // cv.put("Usuario_DtUltimoAcesso", usuario.getUsuarioUltimoAcesso());
        cv.put("Usuario_DtExpiracao", usuario.getUsuarioDTExpiracao());
        cv.put("Usuario_IsAtivo", usuario.getUsuarioIsAtivo());
        return gw.getDatabase().insert(TABLE_Usuario, null, cv) > 0;
    }

    public  List<Usuario> getAllussers()
        {
            List<Usuario> list = new ArrayList<>();
            Usuario usr;
            Cursor cursor = gw.getDatabase().rawQuery(" SELECT * FROM Usuario ", null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                usr = new Usuario();
                usr.setUsuarioId(Integer.parseInt(cursor.getString(0)));
                usr.setUsuarioLogin(cursor.getString(1));
                usr.setUsuarioSenha(cursor.getString(2));
              // usr.getUsuarioUltimoAcesso(cursor.getString(3));
                usr.setUsuarioDTExpiracao(cursor.getString(4));
                usr.setUsuarioIsAtivo(cursor.getInt(5));
                list.add(usr);
                cursor.moveToNext();
            }
            cursor.close();
         //   gw.getDatabase().close();
            return list;
        }

        public Usuario getUserbyLogin(String Usuario_Login, String Usuario_Senha)
        {
            Usuario usr = null;
            Cursor cursor = gw.getDatabase().rawQuery("SELECT * from Usuario where Usuario_Login='"+Usuario_Login +"' " + " AND Usuario_Senha='" +Usuario_Senha + "'"   , null);
            cursor.moveToFirst();
            boolean exists = (cursor.getCount() > 0);
            if(exists) {
                usr= new Usuario();
                usr.setUsuarioId(cursor.getInt(0));
                usr.setUsuarioLogin(cursor.getString(1));
                usr.setUsuarioSenha(cursor.getString(2));
                // usr.getUsuarioUltimoAcesso(cursor.getString(3));
                usr.setUsuarioDTExpiracao(cursor.getString(4));
                usr.setUsuarioIsAtivo(cursor.getInt(5));
            }
            cursor.close();
         //   gw.getDatabase().close();
            return usr;
        }

}