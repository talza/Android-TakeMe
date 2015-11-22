package com.takeme.takemeapp.fragments;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.takeme.models.User;
import com.takeme.services.UserGetDetailsTask;
import com.takeme.services.PostActionResponse;
import com.takeme.services.UserUpdateTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;
import com.takeme.takemeapp.activities.StartTakeMeActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PetDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PetDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailsFragment extends Fragment implements  UserGetDetailsTask.UserGetDetailsResponse, UserUpdateTask.UserUpdateResponse{

    private OnFragmentInteractionListener mListener;
    private Menu mMenu;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Button mSignOutButton;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mPhoneNumberEditText;
    private TakeMeApplication mApp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PetDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetailsFragment newInstance() {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        this.mApp =  (TakeMeApplication)getActivity().getApplication();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        this.mSignOutButton = (Button) view.findViewById(R.id.btnSignOut);
        this.mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });

        this.mFirstNameEditText = (EditText) view.findViewById(R.id.etFirstName);
        this.mLastNameEditText = (EditText) view.findViewById(R.id.etLastName);
        this.mPhoneNumberEditText = (EditText) view.findViewById(R.id.etPhoneNumber);

        mApp.showProgress(this.getActivity());
        UserGetDetailsTask userGetDetailsTask = new UserGetDetailsTask(this.mApp.getCurrentUser(),this);
        userGetDetailsTask.getUserDetails();
        return view;
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
            inflater.inflate(R.menu.menu_user_details, menu);
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

        if (id == R.id.edit_user_action) {
            this.mMenu.findItem(R.id.edit_user_action).setVisible(false);
            this.mMenu.findItem(R.id.save_user_action).setVisible(true);

            this.mFirstNameEditText.setEnabled(true);
            this.mFirstNameEditText.setFocusable(true);
            this.mFirstNameEditText.setFocusableInTouchMode(true);

            this.mLastNameEditText.setEnabled(true);
            this.mLastNameEditText.setFocusable(true);
            this.mLastNameEditText.setFocusableInTouchMode(true);

            this.mPhoneNumberEditText.setEnabled(true);
            this.mPhoneNumberEditText.setFocusable(true);
            this.mPhoneNumberEditText.setFocusableInTouchMode(true);


            return true;
        }
        else if (id == R.id.save_user_action)
        {
            updateUser();
            this.mMenu.findItem(R.id.edit_user_action).setVisible(true);
            this.mMenu.findItem(R.id.save_user_action).setVisible(false);


            this.mFirstNameEditText.setEnabled(false);
            this.mFirstNameEditText.setFocusable(false);

            this.mLastNameEditText.setEnabled(false);
            this.mLastNameEditText.setFocusable(false);

            this.mPhoneNumberEditText.setEnabled(false);
            this.mPhoneNumberEditText.setFocusable(false);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserGetDetailsSuccess(User user) {
        mApp.hideProgress();
        this.mFirstNameEditText.setText(user.getFirstName());
        this.mLastNameEditText.setText(user.getLastName());
        this.mPhoneNumberEditText.setText(user.getPhoneNumber());
    }

    @Override
    public void onUserGetDetailsFailed() {
        mApp.hideProgress();
        Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpdateSuccess(PostActionResponse PostActionResponse) {
        mApp.hideProgress();
        Toast.makeText(getActivity().getApplicationContext(), "User updated successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateFailed(PostActionResponse PostActionResponse) {
        mApp.hideProgress();
        Toast.makeText(getActivity(), "Error occurred while trying to update user", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestCallError(Throwable t) {
        mApp.hideProgress();
        Toast.makeText(getActivity(), "Connection failed", Toast.LENGTH_SHORT).show();
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

    private void updateUser()
    {
        mApp.showProgress(this.getActivity());
        UserUpdateTask userUpdateTask =
                new UserUpdateTask(this.mApp.getCurrentUser(),
                                   this.mFirstNameEditText.getText().toString(),
                                   this.mLastNameEditText.getText().toString(),
                                   this.mPhoneNumberEditText.getText().toString(),
                                   this);
        userUpdateTask.updateUser();
    }

    private void signOut(View v)
    {
        mApp.setCurrentUser(null);
        Intent intentToStart = new Intent(getActivity(), StartTakeMeActivity.class);
        startActivity(intentToStart);
        LoginManager.getInstance().logOut();
        getActivity().finish();
    }
}
