package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.content.res.Configuration;

public class MainActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        // Following gets called when the user clicks on an item in the drawer's ListView.
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            // Code to run when the item gets clicked.
            selectItem(position);
        }
    };

    private void selectItem(int position) {
        // Update the main content by replacing fragments.
        currentPosition = position;   // Update the current position.
        Fragment fragment;
        switch(position) {
            case 1:
            //    fragment = new PizzaFragment();
                fragment = new PizzaMaterialFragment();
                break;
            case 2:
            //    fragment = new PastaFragment();
                fragment = new PastaMaterialFragment();
                break;
            case 3:
            //    fragment = new StoresFragment();
                fragment = new StoreMaterialFragment();
                break;
            default:
                fragment = new TopFragment();
        }
        // Display the fragment.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");   // Add a tag to the fragment.
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        // Set the action bar title. Display the right title in the action bar.
        setActionBarTitle(position);

        // Close the drawer
        drawerLayout.closeDrawer(drawerList);
    }

    // Set the action bar title so it reflects the fragment that's displayed.
    private void setActionBarTitle(int position) {
        String title;
        if (position == 0){
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView)findViewById(R.id.drawer);

      //  DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Populate the ListView
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
//        if (savedInstanceState == null) {
//            selectItem(0);
//        }
        // Display the correct fragment. If the activity has been destroyed and re-created,
        // set the correct action bar title.
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);   // Display TopFragment by default.
        }

        // Create the ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);   // deprecated.

        // Enable the Up icon on the action bar so we can use it to open the drawer.
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Find the Fragment using its tag.
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        // Set currentPosition accordingly.
                        if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                        }
         //               if (fragment instanceof PizzaFragment) {
                        if (fragment instanceof PizzaMaterialFragment) {
                            currentPosition = 1;
                        }
         //               if (fragment instanceof PastaFragment) {
                        if (fragment instanceof PastaMaterialFragment) {
                            currentPosition = 2;
                        }
         //               if (fragment instanceof StoresFragment) {
                        if (fragment instanceof StoreMaterialFragment) {
                            currentPosition = 3;
                        }
                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );
    }

    // Save the state of currentPosition if the activity is going to be destroyed.
    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

    // Called whenever we call invalidateOptionsMenu().
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view (i.e. Share action).
        // If the drawer is close, display action items related to the content view.
        boolean drawerOpen = drawerLayout.isDrawerOpen (drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    // Pass details of any configuration changes to the ActionBarDrawer Toggle.
    @Override
    public void onConfigurationChanged (Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");    // Text to share.
        return super.onCreateOptionsMenu(menu);
    }

    // Pass the Share action an intent for it to share.
    private void setIntent (String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    // This is called when the user clicks on an item in the action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {   // If the ActionBarDrawerToggle is clicked,
            return true;                                  // let it handle what happens.
        }
        switch (item.getItemId()) {
            case R.id.action_create_order:
                // Code to run when the Create Order item is clicked
                Intent intent = new Intent (this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                // Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
