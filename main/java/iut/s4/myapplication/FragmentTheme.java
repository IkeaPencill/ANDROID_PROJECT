package iut.s4.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

    List<Themes> LThemes = new ArrayList<>();

    public FragmentTheme(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_theme, container, false);
        ListView LV = view.findViewById(R.id.LV_themes);

        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view; }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Themes> ARThemes = new ArrayList<>();

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1,ARThemes);

        try {
            JSONArray jsonArray = new JSONArray(getJSONFromAsset(getContext()));
            for(int i=0;i< jsonArray.length();i++){
                LThemes.add(new Themes(jsonArray.getJSONObject(i).getString("label"),jsonArray.getJSONObject(i).getString("url")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("yourfilename.json");
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
