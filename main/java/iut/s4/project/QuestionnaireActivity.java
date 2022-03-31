package iut.s4.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuestionnaireActivity extends AppCompatActivity {
    //recuperation du passage d'objet
    Intent intent;
    Themes T;

    int score;
    boolean choice;
    Question Q;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        intent = getIntent();
        T = intent.getExtras().getParcelable("Themes");

        TextView TV1 = findViewById(R.id.TV_THEMES);
        ImageView IV_THEME = findViewById(R.id.IV_THEMES);
        TV1.setText(T.getLabel());

        Log.i("IMAGE", T.getLabel());
        Log.i("IMAGE", T.getUrl());
        new DownloadImageTask(IV_THEME).execute(T.getUrl());

        Button btn = findViewById(R.id.BTN_BACK);

        new GetQuestionTheme(T.getLabel()).execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });
    }


    private class GetQuestionTheme extends AsyncTask<Void, Void, Void> {
        private HashMap<String,String> urls= new HashMap<>();
        Question Q= new Question();
        String choice;


        public GetQuestionTheme(String choice){
            this.choice=choice;
        }
        @Override
        protected Void doInBackground(Void... params) {
            urls.put("Code de la route","https://raw.githubusercontent.com/IkeaPencill/ANDROID_PROJECT/main/main/assets/jsonCODEDELAROUTE.json");
            urls.put("Espace","https://raw.githubusercontent.com/IkeaPencill/ANDROID_PROJECT/main/main/assets/jsonESPACE.json");
            urls.put("Jardin","https://raw.githubusercontent.com/IkeaPencill/ANDROID_PROJECT/main/main/assets/jsonJARDIN.json");

            HttpHandler sh = new HttpHandler();
            Log.i("CHOICE",choice);
            String jsonStr = sh.makeServiceCall(urls.get(choice));

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject questions = jsonObj.getJSONObject("qcm");
                    String iter = String.valueOf((int)(Math.random()*2)+1);

                    Q.question=questions.getJSONObject(iter).getString("question");


                    for(int i=0;i<questions.getJSONObject(iter).getJSONArray("answers").length();i++){
                        Q.reponses.add(String.valueOf(questions.getJSONObject(iter).getJSONArray("answers").getString(i)));
                    }

                    Q.reponse=questions.getJSONObject(iter).getInt("correct_answer");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.e("TAG", "Problème de connexion");
            }

                return null;
            }

            @Override
            protected void onPostExecute (Void result){
                super.onPostExecute(result);
                TextView TV_QUESTION = findViewById(R.id.TV_QUESTION);
                Button BTN_1 = findViewById(R.id.BTN_true);
                Button BTN_2 = findViewById(R.id.BTN_false);
                TV_QUESTION.setText(Q.getQuestion());

                //BTN_1.setText(Q.reponses.get(0));
                //BTN_2.setText(Q.reponses.get(1));

            }
        }

        ;
    }
