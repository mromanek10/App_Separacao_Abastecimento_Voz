package com.example.FactoryJSONRequest;

import android.content.Context;
import com.android.volley.toolbox.JsonRequest;
import com.example.app_separacao_abastecimento_voz.GlobalApplication;
import org.json.JSONObject;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class DelegateJSONRequest {

    public interface Func
    {
        void execute(String urlHttp);
    }

    public static void doAnyJsonRequest(Func jsonDelegate) {
        jsonDelegate.execute("http://192.168.106.23/APIAbastecimento/separador/14359/separacao");
    }


}
