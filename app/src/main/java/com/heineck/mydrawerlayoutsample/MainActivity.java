package com.heineck.mydrawerlayoutsample;

import java.util.ArrayList;
import java.util.Locale;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, DrawerDatasource {

    private ArrayList<CategoryItem> mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private OnDrawerItemListener planetsListener = null;
    private OnDrawerItemListener carsListener = null;

    private FragmentManager fm = getSupportFragmentManager();

    private PlanetsFragment planetsFragment = null;
    private CarsFragment carsFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        planetsFragment = PlanetsFragment.newInstance("p1", "p2", MainActivity.this);
        planetsListener = planetsFragment;

        Locale l = Locale.getDefault();
        actionBar.addTab(
                actionBar.newTab()
                        .setText(getString(R.string.title_section1).toUpperCase(l))
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setText(getString(R.string.title_section2).toUpperCase(l))
                        .setTabListener(this));

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_fragment, planetsFragment, PlanetsFragment.TAG_NAME);
        ft.commit();


        mPlanetTitles = new ArrayList<CategoryItem>();

        CategoryItem item0 = new CategoryItem(0,"Mercurio");
        CategoryItem item1 = new CategoryItem(0,"Venus");
        CategoryItem item2 = new CategoryItem(0,"Terra");
        CategoryItem item3 = new CategoryItem(0,"Marte");
        mPlanetTitles.add(item0);
        mPlanetTitles.add(item1);
        mPlanetTitles.add(item2);
        mPlanetTitles.add(item3);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new MenuArrayAdapter(this, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("listview menu", "clicked on item position " + position);

                ((MenuArrayAdapter)parent.getAdapter()).setSelectedPosition(position);

                String data = parent.getAdapter().getItem(position).toString();

                Log.d("listview menu", "clicked on item data - " + data);

                if (planetsListener != null) {
                    Log.d("listview menu", "planets delegate succesfull");
                    planetsListener.onItemClick(position, data);
                }

                if (carsListener != null) {
                    Log.d("listview menu", "planets delegate succesfull");
                    carsListener.onItemClick(position, data);
                }

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

       mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.ns_menu_open,  /* "open drawer" description */
                R.string.ns_menu_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("OPEN");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("CLOSE");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        Log.d("MainActivity", "[onTabSelected] - INIT");

        FragmentTransaction ft = fm.beginTransaction();

        hideFragment(ft, planetsFragment);
        hideFragment(ft, carsFragment);

        int pos = tab.getPosition();

        Log.d("MainActivity", "[onTabSelected] - position: " + pos);

        switch (pos) {

            case 0://planets
                ft.show(planetsFragment);
                break;
            case 1://cars
                if (carsFragment == null) {
                    carsFragment = CarsFragment.newInstance("p1", "p2", MainActivity.this);
                    carsListener = carsFragment;
                    ft.add(R.id.content_fragment, carsFragment, CarsFragment.TAG_NAME);
                }

                ft.show(carsFragment);

                break;

        }

        ft.commit();

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void setDatasource(ArrayList adapter) {
        MenuArrayAdapter adp = new MenuArrayAdapter(this, adapter);
        mDrawerList.setAdapter(adp);
        adp.notifyDataSetChanged();
    }

    /**Hides the fragment safely.
     * This method don't commit the transaction
     *
     * @param ft Fragment transaction
     * @param fr Fragment
     */
    private void hideFragment(FragmentTransaction ft, Fragment fr) {
        if (fr != null)
            ft.hide(fr);
    }

}
