package com.takeme.takemeapp.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.takeme.models.Pet;
import com.takeme.services.Constants;
import com.takeme.services.PetAdd2WishListTask;
import com.takeme.services.PetDeleteAdTask;
import com.takeme.services.PetDeleteFromWishListTask;
import com.takeme.services.PetGetAdTask;
import com.takeme.services.TakeMeUtil;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PetDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PetDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetDetailsFragment extends Fragment implements
        PetGetAdTask. PetGetAdResponse,
        PetDeleteAdTask.PetDeleteAdResponse{

    private OnFragmentInteractionListener mListener;
    private Menu mMenu;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String mTitle = "Pet Details";

    private Pet pet;
    boolean mIsPetOwner = false;
    private TakeMeApplication meApplication;

    private TextView petDesc;
    private ImageView petImage;
    private TextView petSize;
    private TextView petGender;
    private TextView petAge;
    private CheckBox petWishListCheckBox;
    private TextView ownerName;
    private TextView ownerEmail;
    private TextView ownerPhone;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PetDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetDetailsFragment newInstance(Long petId) {
        PetDetailsFragment fragment = new PetDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(Constants.PET_DETAILS_ID, petId);
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

        pet = new Pet();

        if(savedInstanceState != null){
            pet.setId(savedInstanceState.getLong(Constants.PET_DETAILS_ID));
        }else {

            pet.setId(getArguments().getLong(Constants.PET_DETAILS_ID));
        }


        meApplication = (TakeMeApplication)getActivity().getApplication();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_details, container, false);

        petImage = (ImageView) view.findViewById(R.id.det_petimage);
        petSize = (TextView) view.findViewById(R.id.tvSize);
        petGender = (TextView) view.findViewById(R.id.tvGender);
        petAge = (TextView) view.findViewById(R.id.tvAge);
        petDesc = (TextView) view.findViewById(R.id.tvPetDescription);
        petWishListCheckBox = (CheckBox) view.findViewById(R.id.cbWishList);

        // Set on click wish list button.
        petWishListCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View)v.getParent();
                if (parent != null ) {

                    wishListClicked(pet);
                }
            }
        });

        ownerName = (TextView) view.findViewById(R.id.tvOwnerName);
        ownerEmail = (TextView) view.findViewById(R.id.tvOwnerEmail);
        ownerPhone = (TextView) view.findViewById(R.id.tvOwnerPhone);

        if(this.pet.getId() != null) {
            meApplication.showProgress(this.getActivity());
            PetGetAdTask petGetAdTask =
                    new PetGetAdTask(meApplication.getCurrentUser(),
                    this.pet.getId(),
                    this);
            petGetAdTask.getPetAd();
        }


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putLong(Constants.PET_DETAILS_ID,this.pet.getId());
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
            getActivity().getActionBar().setTitle(mTitle);


            this.mMenu.findItem(R.id.delete_pet_action).setVisible(mIsPetOwner);
            this.mMenu.findItem(R.id.edit_pet_action).setVisible(mIsPetOwner);

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

        switch (id){
            case R.id.edit_pet_action:
                Fragment fragment = PetNewFragment.newInstance(
                        pet.getId(),
                        pet.getPetName(),
                        pet.getPetType(),
                        pet.getPetSize(),
                        pet.getPetAge(),
                        pet.getPetGender(),
                        pet.getPetDescription(),
                        pet.getPetPhotoUrl());

                // Create new  transaction
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                return true;
            case R.id.delete_pet_action:
                meApplication.showProgress(this.getActivity());
                PetDeleteAdTask petDeleteAdTask = new PetDeleteAdTask(meApplication.getCurrentUser(),pet.getId(),this);
                petDeleteAdTask.deletePetAd();
             return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPetGetAdSuccess(Pet pet) {
        this.pet = pet;
        mTitle = pet.getPetName();
        petAge.setText(pet.getPetAge().toString());
        petGender.setText(TakeMeUtil.getInstance().getGenderByIndex(Integer.valueOf(pet.getPetGender())));
        petSize.setText(TakeMeUtil.getInstance().getSizeByIndex(Integer.valueOf(pet.getPetSize())));
        petDesc.setText(pet.getPetDescription());
        petWishListCheckBox.setChecked(pet.isInWishlist());

        if(!TextUtils.isEmpty(pet.getPetPhotoUrl())) {
            Picasso.with(getActivity()).load(pet.getPetPhotoUrl()).error(getActivity().getDrawable(R.drawable.ic_take_me)).into(petImage);
        }
        ownerEmail.setText(pet.getPetOwner().getOwnerEmail());
        ownerName.setText(pet.getPetOwner().getOwnerFirstName() + " " + pet.getPetOwner().getOwnerLastName());
        ownerPhone.setText(pet.getPetOwner().getOwnerPhone());

        if(this.pet != null               &&
           this.pet.getPetOwner() != null &&
           meApplication.getCurrentUser().equals(this.pet.getPetOwner().getOwnerId()))
        {
            mIsPetOwner = true;
        }

        if(getActivity() != null) {
            getActivity().getActionBar().setTitle(mTitle);
        }

        if(this.mMenu != null) {
            if (this.mMenu.findItem(R.id.delete_pet_action) != null) {
                this.mMenu.findItem(R.id.delete_pet_action).setVisible(mIsPetOwner);
            }
            if (this.mMenu.findItem(R.id.edit_pet_action) != null) {
                this.mMenu.findItem(R.id.edit_pet_action).setVisible(mIsPetOwner);
            }
        }

        meApplication.hideProgress();


    }

    @Override
    public void onPetGetAdFailed() {
        meApplication.hideProgress();
        Toast.makeText(this.getActivity().getApplicationContext(), "An error occurred while getting pet ad", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();

    }

    @Override
    public void onPetDeleteAdSuccess() {
        meApplication.hideProgress();
        Toast.makeText(this.getActivity().getApplicationContext(), "Pet ad deleted successfully", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onPetDeleteAdFailed() {
        meApplication.hideProgress();
        Toast.makeText(this.getActivity().getApplicationContext(), "An error occurred while tried to delete pet ad", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRestCallError(Throwable t) {
        meApplication.hideProgress();
        Toast.makeText(this.getActivity().getApplicationContext(), "An error occurred while getting pet ad", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
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

    private void wishListClicked(Pet pet) {

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

}
