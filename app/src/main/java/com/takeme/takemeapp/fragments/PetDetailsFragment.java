package com.takeme.takemeapp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.takeme.services.Constants;
import com.takeme.takemeapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PetDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PetDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetDetailsFragment extends Fragment {

//    private static final String ARG_VIEW_MODE = "VIEW_MODE";

//    private int mViewMode;
    private OnFragmentInteractionListener mListener;
    private Menu mMenu;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PetDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetDetailsFragment newInstance() {
        PetDetailsFragment fragment = new PetDetailsFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_VIEW_MODE, mViewMode);
        fragment.setArguments(args);
        return fragment;
    }

    public PetDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_details, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(!mNavigationDrawerFragment.isDrawerOpen())
        {
            inflater.inflate(R.menu.menu_pet_details, menu);
            this.mMenu = menu;
            getActivity().getActionBar().setTitle("Pet Details");
            return;
        }

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.edit_pet_action) {
            this.mMenu.findItem(R.id.edit_pet_action).setVisible(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
