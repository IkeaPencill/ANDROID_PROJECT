package iut.s4.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FragmentTheme extends Fragment {

    //listener sur le Fragment
    private OnFragmentInteractionListener mListener;
    private View view;

    private FragmentThemeListener qqun;

    List<Themes> LThemes = new ArrayList<>();
    List<String> nameThemes = new ArrayList<>();

    public FragmentTheme(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_theme, container, false);
        ListView LV = view.findViewById(R.id.LV_themes);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, nameThemes );

        LV.setAdapter(adapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Fragment frag = new FragmentQuestionnaire();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.body_container, frag);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return view; }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        try {
            JSONArray jsonArray = new JSONArray(getJSONFromAsset(getContext()));

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


    //PARTIE COMMUNICATION AVEC ACTIVITE

    OnHeadlineSelectedListener mCallback;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //This makes sure that the container activity has implemented
        //the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}
