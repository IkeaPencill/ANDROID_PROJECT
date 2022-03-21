package iut.s4.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //CHANGEMENT DU FRAGMENT CONTENT
    Fragment fragment;
    @Override
    public void changeFragment(int id) {
        if (id == 1) {
            fragment = new FragmentParametre();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_content, fragment);
            ft.commit();
        }
        else if (id == 2) {
            fragment = new FragmentTheme();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_content, fragment);
            ft.commit();
        }
    }
}
