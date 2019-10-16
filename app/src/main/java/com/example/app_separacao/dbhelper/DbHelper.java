package com.example.app_separacao.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Mcar_Abastecimento_Voz.db";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE Usuario \n" +
            "(\n" +
            " Usuario_ID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            " Usuario_Login TEXT NOT NULL, \n" +
            " Usuario_Senha TEXT NOT NULL, \n" +
            " Usuario_DtUltimoAcesso DEFAULT CURRENT_DATE, \n" +
            " Usuario_DtExpiracao DATE,\n" +
            " Usuario_IsAtivo INTEGER DEFAULT 0\n" +
            " )";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}