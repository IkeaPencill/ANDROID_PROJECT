package iut.s4.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import iut.s4.project.MainActivity;
import iut.s4.project.R;

public class MainMenu extends AppCompatActivity {

    List<Themes> LThemes = new ArrayList<>();
    List<String> nameThemes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ListView LV = findViewById(R.id.LV_themes);
        getJSON();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, nameThemes );

        LV.setAdapter(adapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), QuestionnaireActivity.class);
                intent.putExtra("Themes",LThemes.get(position));
                startActivity(intent);
            }
        });
    }

    public void getJSON(){
        try {
            JSONArray jsonArray = new JSONArray(getJSONFromAsset(getApplicationContext()));

            //lecture du JSON + ajout des thèmes dans la liste de thèmes
            for(int i=0;i< jsonArray.length();i++){
                LThemes.add(new Themes(jsonArray.getJSONObject(i).getString("label"),jsonArray.getJSONObject(i).getString("picture")));
            }

            //on sépare les
            for(int i=0;i<LThemes.size();i++){
                Log.i("infos",LThemes.get(i).getLabel());
                nameThemes.add(LThemes.get(i).label);
            }

        } catch (JSONException e) {e.printStackTrace();}
    }

    //lecture du fichier en string
    private  String getJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("themes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

}