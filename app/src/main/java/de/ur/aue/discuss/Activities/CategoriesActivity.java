package de.ur.aue.discuss.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.ur.aue.discuss.R;

public class CategoriesActivity extends AppCompatActivity {

    private ToggleButton buttonGesundheit;
    private ToggleButton buttonVerbrechen;
    private ToggleButton buttonWirtschaft;
    private ToggleButton buttonUmwelt;
    private ToggleButton buttonInfrastruktur;
    private ToggleButton buttonArbeit;
    private ToggleButton buttonGesellschaft;

    private Button mUserContinueButton;

    private HashMap<String, Boolean> SelectedCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        SelectedCategories = new HashMap<>();

        logActiveRegions();
        fillCategoriesMap();

        buttonGesundheit = findViewById(R.id.catSelectHealth);
        buttonVerbrechen = findViewById(R.id.catSelectCrime);
        buttonWirtschaft = findViewById(R.id.catSelectEconomy);
        buttonUmwelt = findViewById(R.id.catSelectEnvironment);
        buttonInfrastruktur = findViewById(R.id.catSelectInfrastructure);
        buttonArbeit = findViewById(R.id.catSelectWork);
        buttonGesellschaft = findViewById(R.id.catSelectSocial);

        setSelectionChangedListener();

        mUserContinueButton = (Button) findViewById(R.id.categoriesForwardButton);
        mUserContinueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.shared_prevs_filename), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                Iterator it = SelectedCategories.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    editor.putBoolean((String) pair.getKey(), (Boolean) pair.getValue());
                }

                editor.commit();

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CheckUserMayContinue() {
        for (Boolean region : SelectedCategories.values())
        {
            if(region) {
                mUserContinueButton.setEnabled(true);
                return;
            }
        }
        mUserContinueButton.setEnabled(false);
    }


    private void fillCategoriesMap() {
        SelectedCategories.put("Gesundheit", false);
        SelectedCategories.put("Verbrechen", false);
        SelectedCategories.put("Wirtschaft", false);
        SelectedCategories.put("Umwelt", false);
        SelectedCategories.put("Infrastruktur", false);
        SelectedCategories.put("Arbeit", false);
        SelectedCategories.put("Gesellschaft", false);
    }


    private void setSelectionChangedListener() {

        buttonGesundheit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedCategories.put("Gesundheit", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonVerbrechen.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedCategories.put("Verbrechen", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonWirtschaft.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedCategories.put("Wirtschaft", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonUmwelt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedCategories.put("Umwelt", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonInfrastruktur.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedCategories.put("Infrastruktur", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonArbeit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedCategories.put("Arbeit", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonGesellschaft.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedCategories.put("Gesellschaft", isChecked);
                CheckUserMayContinue();
            }
        });
    }


    private void logActiveRegions() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_prevs_filename), Context.MODE_PRIVATE);

        boolean defaultValue = false;
        boolean isActive = sharedPref.getBoolean("Landkreis", defaultValue);
        Log.w("REGIONS", "Landkreis: " + isActive);

        isActive = sharedPref.getBoolean("Deutschland", defaultValue);
        Log.w("REGIONS", "Deutschland: " + isActive);

        isActive = sharedPref.getBoolean("Europa", defaultValue);
        Log.w("REGIONS", "Europa: " + isActive);

        isActive = sharedPref.getBoolean("Welt", defaultValue);
        Log.w("REGIONS", "Welt: " + isActive);
    }
}
