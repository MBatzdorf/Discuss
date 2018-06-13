package de.ur.aue.discuss.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.ur.aue.discuss.Adapter.DiscussionsRecyclerViewAdapter;
import de.ur.aue.discuss.Fragments.CreateDiscussionFragment;
import de.ur.aue.discuss.Fragments.DiscussionsFragment;
import de.ur.aue.discuss.Fragments.DiscussionsFragment.OnListFragmentInteractionListener;
import de.ur.aue.discuss.Models.DiscussionItemElement.DiscussionItem;
import de.ur.aue.discuss.R;

public class HomeActivity extends AppCompatActivity implements OnListFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View mNavigationHeader;
    private Menu mNavigationMenu;

    private HashMap<String, Boolean> RegionsMap;
    private HashMap<String, Boolean> CategoriesMap;

    private DiscussionsRecyclerViewAdapter mDiscussionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RegionsMap = new HashMap<>();
        CategoriesMap = new HashMap<>();
        FragmentManager fragmentManager = getSupportFragmentManager();
        DiscussionsFragment discFrag = (DiscussionsFragment) fragmentManager.findFragmentById(R.id.fragmentDiscussion);
        RecyclerView discussionList = discFrag.getRecyclerView();
        mDiscussionsAdapter = (DiscussionsRecyclerViewAdapter) discussionList.getAdapter();
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setItemIconTintList(null);
        mNavigationMenu = mNavigationView.getMenu();
        mNavigationHeader = mNavigationView.getHeaderView(0);

        initMapsAndMenuFromSharedPrevs();

        mDiscussionsAdapter.setEmptyDiscussionsLayout((LinearLayout) findViewById(R.id.layoutNoDiscussions));
        mDiscussionsAdapter.setFilterMaps(RegionsMap, CategoriesMap);
        mDiscussionsAdapter.getFilter().filter(null);


        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        boolean isChecked = menuItem.isChecked();
                        if(isChecked) {
                            menuItem.setChecked(false);
                        }else{
                            menuItem.setChecked(true);
                        }

                        if(RegionsMap.containsKey(menuItem.getTitle()))
                            RegionsMap.put(menuItem.getTitle().toString(), !isChecked);
                        if(CategoriesMap.containsKey(menuItem.getTitle()))
                            CategoriesMap.put(menuItem.getTitle().toString(), !isChecked);

                        mDiscussionsAdapter.getFilter().filter(null);


                        updateSharedPrevs(menuItem.getTitle().toString(), !isChecked);

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return false;
                    }
                });

        mDrawerLayout = findViewById(R.id.homeDraweLayout);
        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        FloatingActionButton fab = findViewById(R.id.addDiscussionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new CreateDiscussionFragment();
                dialog.show(getSupportFragmentManager(), "CreateDiscussionDialogFragment");
            }
        });
    }


    private void initMapsAndMenuFromSharedPrevs() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_prevs_filename), Context.MODE_PRIVATE);

        TextView headerUsername = mNavigationHeader.findViewById(R.id.textHeaderUsername);
        String usernameString = "Default User";
        usernameString = sharedPref.getString(getString(R.string.saved_username), usernameString);
        headerUsername.setText(usernameString);

        boolean defaultValue = false;
        Menu catItems = mNavigationMenu.findItem(R.id.itemMenuCategories).getSubMenu();
        Menu regItems = mNavigationMenu.findItem(R.id.itemMenuRegion).getSubMenu();

        MenuItem currentItem;
        // Categories
        boolean isActive = sharedPref.getBoolean("Gesundheit", defaultValue);
        CategoriesMap.put("Gesundheit", isActive);
        currentItem = catItems.findItem(R.id.itemCatGesundheit);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Verbrechen", defaultValue);
        CategoriesMap.put("Verbrechen", isActive);
        currentItem = catItems.findItem(R.id.itemCatVerbrechen);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Wirtschaft", defaultValue);
        CategoriesMap.put("Wirtschaft", isActive);
        currentItem = catItems.findItem(R.id.itemCatWirtschaft);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Umwelt", defaultValue);
        CategoriesMap.put("Umwelt", isActive);
        currentItem = catItems.findItem(R.id.itemCatUmwelt);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Infrastruktur", defaultValue);
        CategoriesMap.put("Infrastruktur", isActive);
        currentItem = catItems.findItem(R.id.itemCatInfrastruktur);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Arbeit", defaultValue);
        CategoriesMap.put("Arbeit", isActive);
        currentItem = catItems.findItem(R.id.itemCatArbeit);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Gesellschaft", defaultValue);
        CategoriesMap.put("Gesellschaft", isActive);
        currentItem = catItems.findItem(R.id.itemCatGesellschaft);
        currentItem.setChecked(isActive);


        //Regions
        isActive = sharedPref.getBoolean("Landkreis", defaultValue);
        RegionsMap.put("Landkreis", isActive);
        currentItem = regItems.findItem(R.id.itemRegionLokal);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Deutschland", defaultValue);
        RegionsMap.put("Deutschland", isActive);
        currentItem = regItems.findItem(R.id.itemRegionDeutschland);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Europa", defaultValue);
        RegionsMap.put("Europa", isActive);
        currentItem = regItems.findItem(R.id.itemRegionEu);
        currentItem.setChecked(isActive);

        isActive = sharedPref.getBoolean("Welt", defaultValue);
        RegionsMap.put("Welt", isActive);
        currentItem = regItems.findItem(R.id.itemRegionWelt);
        currentItem.setChecked(isActive);

        //printMap(CategoriesMap);
        //printMap(RegionsMap);
    }


    private void updateSharedPrevs(String title, boolean newValue)
    {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_prevs_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(title, newValue);
        editor.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListFragmentInteraction(DiscussionItem item) {
        Intent intent = new Intent(getApplicationContext(), DiscussionActivity.class);
        intent.putExtra("DiscussionItem", item);
        startActivity(intent);
    }


    private void printMap(HashMap<String, Boolean> MapToPrint) {
        Iterator it = MapToPrint.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.w("FILTERING", pair.getKey() + ":" + pair.getValue());
        }
    }
}
