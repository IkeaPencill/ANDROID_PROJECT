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
import iut.s4.project.R;
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

    ArrayList<Question> ARQuestions= new ArrayList<>();
    ArrayList<Integer> ARalreadypass = new ArrayList<Integer>();

    int score;
    int nbQ;
    int choice=-1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        intent = getIntent();
        T = intent.getExtras().getParcelable("Themes");

        TextView TV1 = findViewById(R.id.TV_THEMES);
        ImageView IV_THEME = findViewById(R.id.IV_THEMES);
        TV1.setText(T.getLabel());

        new DownloadImageTask(IV_THEME).execute(T.getUrl());

        Button btn = findViewById(R.id.BTN_BACK);
        Button BTN_1 = findViewById(R.id.BTN_true);
        Button BTN_2 = findViewById(R.id.BTN_false);

        //reccueil des infos du JSON dans une ARList
        new GetQuestionTheme(T.getLabel()).execute();
        TextView TV_QUESTION = findViewById(R.id.TV_QUESTION);

        int iter = (int)(Math.random()*nbQ);
        Log.i("TAILLE AR", String.valueOf(ARQuestions.size()));
        for(int i=0;i<ARQuestions.size();i++){
            //tant que le num de la question tirée est déjà passé
            if(ARalreadypass.size()==nbQ){
                Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }

            while(ARalreadypass.contains(iter)){
                //on tire un nouveau numéro
                iter = (int)(Math.random()*nbQ);
            }
            TV_QUESTION.setText(ARQuestions.get(iter).getQuestion());
            ARalreadypass.add(iter);
            while(choice==-1){}

            if(ARQuestions.get(iter).getAnswer()==String.valueOf(choice)){
                score++;
                updateScore();
            }
            choice=-1;
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });
        BTN_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {choice=0;}
        });

        BTN_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {choice=1;}
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
            urls.put("Code de la route","https://raw.githubusercontent.com/IkeaPencill/ANDROID_PROJECT/PROJETV2/main/assets/jsonCODEDELAROUTE.json");
            urls.put("Espace","https://raw.githubusercontent.com/IkeaPencill/ANDROID_PROJECT/main/main/assets/jsonESPACE.json");
            urls.put("Jardin","https://raw.githubusercontent.com/IkeaPencill/ANDROID_PROJECT/main/main/assets/jsonJARDIN.json");

            HttpHandler sh = new HttpHandler();
            //Log.i("CHOICE",choice);
            String jsonStr = sh.makeServiceCall(urls.get(choice));

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject questions = jsonObj.getJSONObject("qcm");
                    nbQ = questions.length();

                    for(int i=1;i<=nbQ;i++){
                        Question tmpQ=new Question();
                        tmpQ.question=questions.getJSONObject(String.valueOf(i)).getString("question");
                        tmpQ.reponse=questions.getJSONObject(String.valueOf(i)).getInt("correct_answer");
                        ARQuestions.add(tmpQ);
                        Log.i("QUESTION ->",ARQuestions.get(i-1).toString());
                    }
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
                updateScore();
                super.onPostExecute(result);
                //BTN_1.setText(Q.reponses.get(0));
                //BTN_2.setText(Q.reponses.get(1));

            }
        }

        public void updateScore(){
            TextView TV_score = findViewById(R.id.TV_SCORE);
            TV_score.setText("Score :"+score);
        }

    }
