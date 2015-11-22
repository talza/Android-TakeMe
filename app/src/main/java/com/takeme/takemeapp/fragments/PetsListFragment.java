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
import android.widget.Toast;

import com.takeme.models.Pet;
import com.takeme.services.Constants;
import com.takeme.services.PetAdd2WishListTask;
import com.takeme.services.PetDeleteFromWishListTask;
import com.takeme.services.PetsFindAdTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;
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
public class PetsListFragment extends Fragment implements
        PetsSearchFragment.OnSearchClicked,
        PetsFindAdTask.PetsGetListResponse,
        PetsListAdapter.WishListClickListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Constants.PetsListMode petListMode;

    private static final String STATE_ACTIVATED_POSITION = "activated_position";


    private Long token;
    private int type;
    private int size;
    private int ageFrom;
    private int ageTo;
    private int gender;
    private boolean wishList;
    private boolean isMyPet;

    private List mPetsList;
    private ListView mPetsListView;
    private PetsListAdapter petsListAdapter;

    private OnFragmentInteractionListener mListener;
    private Menu mMenu;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PetsSearchFragment mPetsSearchFragment;

    private TakeMeApplication meApplication;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PetsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetsListFragment newInstance(Constants.PetsListMode petsListMode) {
        PetsListFragment fragment = new PetsListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PET_LIST_MODE, petsListMode.toString());
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
            petListMode = Constants.PetsListMode.valueOf(getArguments().getString(Constants.PET_LIST_MODE));
        }

        meApplication = (TakeMeApplication)getActivity().getApplication();

        switch (petListMode)
        {
            case PetsList:
                getActivity().setTitle(getString(R.string.title_pets_List));
                token = null;
                wishList = false;
                isMyPet = false;
                break;
            case MyPets:
                getActivity().setTitle(getString(R.string.title_my_pets_view));
                wishList = false;
                isMyPet = true;
                break;
            case WishList:
                getActivity().setTitle(getString(R.string.title_wish_list_view));

                this.wishList = true;
                isMyPet = false;
                break;
        }

        // populate data
        this.mPetsList = new ArrayList<Pet>();

        mPetsSearchFragment = new PetsSearchFragment();
        mPetsSearchFragment.setSearchClickedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pets_list, container, false);
        this.mPetsListView = (ListView) view.findViewById(R.id.lvPets);

        petsListAdapter = new PetsListAdapter(view.getContext(), this.mPetsList,this);
        this.mPetsListView.setAdapter(petsListAdapter);

        this.mPetsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivatedPosition = position;
                Pet item = (Pet) parent.getItemAtPosition(position);

                Fragment fragment = PetDetailsFragment.newInstance(item.getId());

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

        PetsFindAdTask petsFindTask =
                new PetsFindAdTask(meApplication.getCurrentUser(),
                        this.type,
                        this.size,
                        this.ageFrom,
                        this.ageTo,
                        this.gender,
                        this.wishList,
                        isMyPet,
                        this);

        petsFindTask.getPetsList();

        // Inflate the layout for this fragment
        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
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
            mPetsSearchFragment.show(fragmentManager, "PetSearch");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchClicked(int animalType,
                                int animalSize,
                                int animalGender,
                                int animalAgeFrom,
                                int animalAgeTo) {

       mPetsSearchFragment.dismiss();

        this.type = animalType;
        this.size = animalSize;
        this.gender = animalGender;
        this.ageFrom = animalAgeFrom;
        this.ageTo = animalAgeTo;
        PetsFindAdTask petsFindTask = new PetsFindAdTask(this.token,
                                                     this.type,
                                                     this.size,
                                                     this.ageFrom,
                                                     this.ageTo,
                                                     this.gender,
                                                     this.wishList,
                                                     this.isMyPet,
                                                     this);
        petsFindTask.getPetsList();
    }

    @Override
    public void onPetsGetListSuccess(List<Pet> lsPets) {

        this.mPetsList.clear();
        for (Pet pet: lsPets) {
            this.mPetsList.add(pet);
        }

        mPetsListView.invalidate();
        petsListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPetsGetListFailed() {
        Toast.makeText(this.getActivity().getApplicationContext(),"An error occurred while getting pets",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRestCallError(Throwable t) {
        Toast.makeText(this.getActivity().getApplicationContext(), "An error occurred while getting pets", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onWishListClicked(Pet pet) {
        pet.setIsWishInList(!pet.isInWishlist());

        if(pet.isInWishlist()){
            PetAdd2WishListTask petAdd2WishListTask =
                    new PetAdd2WishListTask(meApplication.getCurrentUser(),pet.getId());
            petAdd2WishListTask.add2WishList();
        }else{
            PetDeleteFromWishListTask petDeleteFromWishListTask =
                    new PetDeleteFromWishListTask(meApplication.getCurrentUser(),pet.getId());
            petDeleteFromWishListTask.deleteFromWishList();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
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

    private void setActivatedPosition(int position) {


        if (position == ListView.INVALID_POSITION) {
            this.mPetsListView.setItemChecked(mActivatedPosition, false);
        } else {
            this.mPetsListView.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

}
