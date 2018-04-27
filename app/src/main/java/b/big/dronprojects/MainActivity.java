package b.big.dronprojects;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import b.big.dronprojects.ControlPanel.ControlPanel;
import b.big.dronprojects.Fragments.AuthorFragment;
import b.big.dronprojects.Fragments.ModulesFragment;
import b.big.dronprojects.Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


     BottomNavigationView bottomNavigationView;
    android.support.v4.app.Fragment settingsFragment = new SettingsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(settingsFragment);


    }



    public boolean loadFragment(android.support.v4.app.Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentConatiner, fragment)
                    .commit();
            System.out.println("laal");

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        android.support.v4.app.Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.gamepad:
                Intent intent = new Intent(getApplicationContext(), ControlPanel.class);
                startActivity(intent);
                break;
            case R.id.modules:
                fragment = new ModulesFragment();
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                break;
            case R.id.autors:
                fragment = new AuthorFragment();
                break;


        }
        return loadFragment(fragment);

    }
}
