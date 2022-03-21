package iut.s4.myapplication;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class FragmentNav extends Fragment {

    //listener sur le Fragment
    private OnFragmentInteractionListener mListener;

    public FragmentNav(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view; }





}
