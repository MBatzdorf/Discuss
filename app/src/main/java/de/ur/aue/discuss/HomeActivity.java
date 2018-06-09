package de.ur.aue.discuss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import de.ur.aue.discuss.DiscussionsFragment.OnListFragmentInteractionListener;
import de.ur.aue.discuss.dummy.DummyContent.DummyItem;

public class HomeActivity extends AppCompatActivity implements OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onListFragmentInteraction(DummyItem item) {
        Toast toast = Toast.makeText(this, item.title, Toast.LENGTH_SHORT);
        toast.show();
    }
}
