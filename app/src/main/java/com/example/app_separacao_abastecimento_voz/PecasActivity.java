package com.example.app_separacao_abastecimento_voz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.FactoryJSONRequest.DelegateJSONRequest;
import com.example.parsejson.Enderecos;
import com.example.parsejson.Itens;
import com.example.parsejson.Pedido;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PecasActivity extends AppCompatActivity implements
        RecognitionListener, TextToSpeech.OnInitListener {

    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextView returnedText;
    private TextView returnedError;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private static int countJson=0;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private TextToSpeech tts;
    private  String textTospeak;
    private AudioManager amanager;
    private boolean firstConnect=true;
    private  Pedido pedido;

    private void resetSpeechRecognizer() {

        if(speech != null)
            speech.destroy();
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        if(SpeechRecognizer.isRecognitionAvailable(this))
            speech.setRecognitionListener(this);
        else
            finish();
    }

    private void setRecogniserIntent() {

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                new Locale("pt", "BR"));
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
       // AudioManager audio = (AudioManager) getSystemService(this.AUDIO_SERVICE);
      //  audio.adjustVolume(AudioManager.ADJUST_MUTE,AudioManager.FLAG_SHOW_UI);


          amanager=(AudioManager)getSystemService(this.AUDIO_SERVICE);
     //   amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
      //  amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
     //   amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
     //   amanager.setStreamMute(AudioManager.STREAM_RING, true);
     //   amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);

      //  amanager.adjustVolume(AudioManager.ADJUST_MUTE,AudioManager.ADJUST_MUTE);
       //   amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);

    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("PT", "BR");
            int result = tts.setLanguage(locale);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    private void speakOut() {


      //  tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        // tts.stop();
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecas);
        tts = new TextToSpeech(this, this);
        // UI initialisation
        returnedText = findViewById(R.id.textView1);
        returnedError = findViewById(R.id.errorView1);
        progressBar =  findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);


        // start speech recogniser
        resetSpeechRecognizer();

        // start progress bar
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        // check for permission
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }

        setRecogniserIntent();
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                speech.startListening(recognizerIntent);
            } else {
                Toast.makeText(PecasActivity.this, "Permission Denied!", Toast
                        .LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    public void onResume() {
        Log.i(LOG_TAG, "resume");
        super.onResume();
        resetSpeechRecognizer();
        speech.startListening(recognizerIntent);
    }

    @Override
    protected void onPause() {
        Log.i(LOG_TAG, "pause");
        super.onPause();
        speech.stopListening();
    }

    @Override
    protected void onStop() {
        Log.i(LOG_TAG, "stop");
        super.onStop();
        if (speech != null) {
            speech.destroy();
        }
    }


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        speech.stopListening();
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

    //    returnedText.setText(text);


        DelegateJSONRequest.Func doJsonObjectRequest = new DelegateJSONRequest.Func()
        {
            @Override
            public void execute(String urlHttp) {


                RequestQueue queue = Volley.newRequestQueue(PecasActivity.this);

                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, urlHttp, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.e("Response", response.toString());

                                tts.setSpeechRate(0.9f);
                                tts.speak("buscando endereço", TextToSpeech.QUEUE_ADD, null);
                                tts.speak("aguarde", TextToSpeech.QUEUE_ADD, null);

                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                pedido = new Gson().fromJson(response.toString(), Pedido.class);

                                List<Itens> itens = pedido.getItens();
                                int pecaId = itens.get(countJson).getPecaID();
                                String pecaDescricao = itens.get(countJson).getPecaDescricao();

                                List<Enderecos> end = itens.get(0).getEnderecos();

                                String andar = end.get(0).getAndar();
                                String rua = end.get(0).getRua();
                                String prateleira = end.get(0).getPrateleira();
                                String locacao = end.get(0).getLocacao();

                                returnedText.append("\nnumero : " + pedido.getNumero());
                                returnedText.append("\npecaId : " + pecaId);
                                returnedText.append("\npecaDescricao : " + pecaDescricao);
                                returnedText.append("\nandar : " + andar);
                                returnedText.append("\nrua : " + rua);

                                tts.setSpeechRate(0.9f);
                                tts.speak("O numero do andar é " + andar, TextToSpeech.QUEUE_ADD, null);
                                tts.speak("O numero da rua é" + rua, TextToSpeech.QUEUE_ADD, null);

                                countJson++;

                                firstConnect=false;
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error.Response", error.toString());
                            }
                        }
                );
// add it to the RequestQueue
                queue.add(getRequest);

            }
        };

        String regex = "próximo";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if(matcher.find()) {
               amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            if(firstConnect) {
                DelegateJSONRequest.doAnyJsonRequest(doJsonObjectRequest);
            }
            else {

                tts.setSpeechRate(0.9f);
                tts.speak("buscando endereço", TextToSpeech.QUEUE_ADD, null);
                tts.speak("aguarde", TextToSpeech.QUEUE_ADD, null);

                try {
                     Thread.sleep(3000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                   }

                List<Itens> itens = pedido.getItens();
                int pecaId = itens.get(countJson).getPecaID();
                String pecaDescricao = itens.get(countJson).getPecaDescricao();

                List<Enderecos> end = itens.get(countJson).getEnderecos();

                String andar = end.get(0).getAndar();
                String rua = end.get(0).getRua();
                String prateleira = end.get(0).getPrateleira();
                String locacao = end.get(0).getLocacao();


                tts.setSpeechRate(0.9f);
                tts.speak("O numero do andar é " + andar, TextToSpeech.QUEUE_ADD, null);
                tts.speak("O numero da rua é" + rua, TextToSpeech.QUEUE_ADD, null);

                returnedText.append("\nnumero : " + pedido.getNumero());
                returnedText.append("\npecaId : " + pecaId);
                returnedText.append("\npecaDescricao : " + pecaDescricao);
                returnedText.append("\nandar : " + andar);
                returnedText.append("\nrua : " + rua);

                Log.e("countJson", String.valueOf(countJson));

                if(countJson==itens.size()-1){
                    tts.speak("lista de endereços concluída", TextToSpeech.QUEUE_ADD, null);
                    countJson=0;
                }
                countJson++;
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.i(LOG_TAG, "FAILED " + errorMessage);
        returnedError.setText(errorMessage);

        // rest voice recogniser
        resetSpeechRecognizer();
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
              //  message = "No match";
                message = "";

                tts.setSpeechRate(0.9f);
                tts.speak("não consegui entender", TextToSpeech.QUEUE_ADD, null);

                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}
