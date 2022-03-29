package iut.s4.myapplication;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentParametre extends Fragment {
    //listener sur le Fragment
    private OnFragmentInteractionListener mListener;

    public FragmentParametre(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parametre, container, false);

        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view; }

}
