package iut.s4.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnHeadlineSelectedListener{
    BottomNavigationView navigationView;
    ListView LV_THEMES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new FragmentTheme()).commit();
        navigationView.setSelectedItemId(R.id.nav_themes);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.nav_account:
                        fragment = new FragmentAccount();
                        break;

                    case R.id.nav_settings:
                        fragment = new FragmentParametre();
                        break;

                    case R.id.nav_themes:
                        fragment = new FragmentTheme();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                //return false;
                return true;
            }
        });

    }


    //COMMUNICATION ENTRE FRAGEMENTS
    @Override
    public void onDataSend(int position,String s) {
        android.app.Fragment frag1;
        //SI 0, alors fragement themes
        if(position==0){
            frag1 = getFragmentManager().findFragmentById(R.id.FRAG_THEMES);
            //(frag1.getView().findViewById(R.id.textView)).setText("Text from Fragment 2:" + s);
        }

        if(position==1){
            frag1 = getFragmentManager().findFragmentById(R.id.FRAG_ACCOUNT);
        }

        if(position==2){
            frag1 = getFragmentManager().findFragmentById(R.id.FRAG_PARAMETER);
        }
    }


    //COMMUNICATION AVEC LES FRAGEMENTS

}
