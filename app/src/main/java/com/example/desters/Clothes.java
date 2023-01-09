package com.example.desters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Clothes extends AppCompatActivity {
    //mesajfinal va fi trimis ca si hint la fragment
    String mesajFinal;
    //rezultatul
    int scorClothes;
    TextView textView;
    String raspunsCorect;
    // url cu ghicitori
    String jsonURL = "https://api.jsonbin.io/v3/qs/63bc236901a72b59f24638d0";

    int id = 0;
    private static final String TAG = "Clothes";

    private SharedPreferences mySharedPref;
    private SharedPreferences.Editor mySharedPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        textView = findViewById(R.id.text_inserted2);
        textView.setBackgroundResource(R.color.white);
        mySharedPref = getSharedPreferences("ansDetails", Context.MODE_PRIVATE);
        mySharedPrefEditor = mySharedPref.edit();

        textView.setText(mySharedPref.getString("myAns",""));

        //intent care porneste un serviciu care contorizeaza cat timp am jucat
        Intent serviceIntent = new Intent(Clothes.this, MyService.class);
        startService(serviceIntent);

        scorClothes = getIntent().getIntExtra("scor", 0);
        View view = null;
        onClickNextC(view);
    }

    public void onClickX(View v) {
        mySharedPrefEditor.putString("myAns", textView.getText().toString());
        mySharedPrefEditor.commit();

        Intent intent_2 = new Intent(getApplicationContext(), MyService.class);
        stopService(intent_2);

        Intent intent = new Intent();
        intent.putExtra("result", scorClothes);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void verificaC(View view) {

        if (textView.getText().toString().equals(raspunsCorect)) {
            scorClothes = scorClothes + 1;
            Toast.makeText(getApplicationContext(), "Good job", Toast.LENGTH_SHORT).show();

        }
    }

    public void onClickCluesC(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("cheie", mesajFinal);
        Tips blank = new Tips();
        blank.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainerView5, blank).commit();
    }

    public void onClickNextC(View view) {
        mySharedPrefEditor.putString("myAns", textView.getText().toString());
        mySharedPrefEditor.commit();

        if (id < 3) {
            id = id + 1;
            System.out.println(id);
            setGhicitoareC(view, String.valueOf(id));

        } else {
            id = 1;
            System.out.println(id);
            setGhicitoareC(view, String.valueOf(id));
        }
    }

    public void onClickPrevC(View view) {
        mySharedPrefEditor.putString("myAns", textView.getText().toString());
        mySharedPrefEditor.commit();

        if (id > 0) {
            System.out.println(id);
            setGhicitoareC(view, String.valueOf(id));
            id = id - 1;
        } else {
            id = 3;
            System.out.println(id);
            setGhicitoareC(view, String.valueOf(id));
            id = id - 1;

        }
    }

    public void setGhicitoareC(View view, String idDeTrimis) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(jsonURL)
                .build();

        Log.i(TAG, "onClickGetData: " + jsonURL);
        okhttp3.Call call = client.newCall(request);
        ((okhttp3.Call) call).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                String recData = response.body().string();
                JSONObject obj;
                TextView ghicitoare = findViewById(R.id.ghicitoare2);
                try {
                    obj = new JSONObject(recData);
                    JSONObject b = obj.getJSONObject("record");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ghicitoare.setText(b.getString("question" + idDeTrimis).toString());
                                mesajFinal = b.getString("hint" + idDeTrimis).toString();
                                raspunsCorect = b.getString("answer" + idDeTrimis).toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }
}