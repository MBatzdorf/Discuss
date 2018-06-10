package de.ur.aue.discuss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RegionsActivity extends AppCompatActivity {


    private ToggleButton buttonLokal;
    private ToggleButton buttonDeutschland;
    private ToggleButton buttonEU;
    private ToggleButton buttonWelt;

    private Button mUserContinueButton;

    private HashMap<String, Boolean> SelectedRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        SelectedRegions = new HashMap<>();
        SelectedRegions.put("Landkreis", false);
        SelectedRegions.put("Deutschland", false);
        SelectedRegions.put("Europa", false);
        SelectedRegions.put("Welt", false);

        buttonLokal = findViewById(R.id.regionSelectLokal);
        buttonDeutschland = findViewById(R.id.regionSelectDeutschland);
        buttonEU = findViewById(R.id.regionSelectEU);
        buttonWelt = findViewById(R.id.regionSelectWelt);

        buttonLokal.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedRegions.put("Landkreis", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonDeutschland.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedRegions.put("Deutschland", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonEU.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedRegions.put("Europa", isChecked);
                CheckUserMayContinue();
            }
        });

        buttonWelt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SelectedRegions.put("Welt", isChecked);
                CheckUserMayContinue();
            }
        });

        mUserContinueButton = (Button) findViewById(R.id.regionsForwardButton);
        mUserContinueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.shared_prevs_filename), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                Iterator it = SelectedRegions.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    editor.putBoolean((String) pair.getKey(), (Boolean) pair.getValue());
                    it.remove(); // avoids a ConcurrentModificationException
                }

                editor.commit();

                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                startActivity(intent);
            }
        });

        /*
        TextView mTestText;
        mTestText = (TextView) findViewById(R.id.textTest);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_prevs_filename), Context.MODE_PRIVATE);
        String defaultValue = "None";
        String username = sharedPref.getString(getString(R.string.saved_username), defaultValue);

        mTestText.setText(username);
        */
    }

    private void CheckUserMayContinue() {
        for (Boolean region : SelectedRegions.values())
        {
            if(region) {
                mUserContinueButton.setEnabled(true);
                return;
            }
        }
        mUserContinueButton.setEnabled(false);
    }
}
