package de.ur.aue.discuss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegionsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        Button mUserSignInButton = (Button) findViewById(R.id.regionsForwardButton);
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
