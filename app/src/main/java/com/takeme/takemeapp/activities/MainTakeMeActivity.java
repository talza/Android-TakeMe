package com.takeme.takemeapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.takeme.takemeapp.R;
import com.takeme.takemeapp.fragments.NavigationDrawerFragment;
import com.takeme.takemeapp.fragments.PetDetailsFragment;
import com.takeme.takemeapp.fragments.PetsListFragment;
import com.takeme.takemeapp.fragments.UserDetailsFragment;

public class MainTakeMeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        PetsListFragment.OnFragmentInteractionListener,
        PetDetailsFragment.OnFragmentInteractionListener,
        UserDetailsFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_take_me);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getString(R.string.title_search_view);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments

        Fragment fragment = null;

        switch(position) {
            default:
            case 0:
                mTitle = getString(R.string.title_search_view);
                fragment = PetsListFragment.newInstance(null,null);
                break;
            case 1:
                mTitle = getString(R.string.title_my_pets_view);
                fragment = PetsListFragment.newInstance(null,null);
              //  fragment = new MyPetsFragment();
                break;
            case 2:
                mTitle = getString(R.string.title_wish_list_view);
                fragment = PetsListFragment.newInstance(null,null);
           //     fragment = new WishListFregment();
                break;
            case 3:
                mTitle = getString(R.string.title_my_account_view);
                fragment = UserDetailsFragment.newInstance(null,null);
                //signOut();
//                return;

        }

        // Create new  transaction
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_take_me, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
