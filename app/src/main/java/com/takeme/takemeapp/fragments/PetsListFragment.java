package com.takeme.takemeapp.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.takeme.models.Pet;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.listadapters.PetsListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PetsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PetsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List mPetsList;
    private ListView mPetsListView;

    private OnFragmentInteractionListener mListener;
    private Menu mMenu;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PetsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetsListFragment newInstance(String param1, String param2) {
        PetsListFragment fragment = new PetsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PetsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // populate data
        this.mPetsList = new ArrayList<Pet>();
        mPetsList.add(new Pet("1","Princess","Female","1","Dog","Medium","http://farm5.staticflickr.com/4142/4787427683_3672f1db9a_s.jpg","desciption",true,null));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pets_list, container, false);
        this.mPetsListView = (ListView) view.findViewById(R.id.lvPets);

        this.mPetsListView.setAdapter(new PetsListAdapter(view.getContext(), this.mPetsList));

        this.mPetsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Pet item = (Pet) parent.getSelectedItem();

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = PetDetailsFragment.newInstance();

                // Create new  transaction
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });
        // Inflate the layout for this fragment
        return  view;
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

        if (!mNavigationDrawerFragment.isDrawerOpen())
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
        {
            inflater.inflate(R.menu.menu_pets_list, menu);
            this.mMenu = menu;
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

//        //noinspection SimplifiableIfStatement
        if (id == R.id.search_pets_action) {
            FragmentManager fragmentManager = getFragmentManager();

            PetsSearchFragment dialogFragment = new PetsSearchFragment();
            dialogFragment.show(fragmentManager, "PetSearch");

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
