package com.takeme.takemeapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;

import com.squareup.picasso.Picasso;
import com.takeme.models.Pet;
import com.takeme.services.AwsS3Provider;
import com.takeme.services.Constants;
import com.takeme.services.PetCreateAdTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PetNewFragment extends Fragment
        implements View.OnClickListener,
                   AwsS3Provider.FileUploadCallBack,
                   PetCreateAdTask.PetCreateAdResponse{

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView petPicture;
    private String mCurrentPhotoPath;
    private File pictureFile = null;
    private Button btnSave;

    private EditText petNameEditText;
    private EditText petDescEditText;

    private Spinner spAge;
    private Spinner spSize;
    private Spinner spType;
    private Spinner spGender;

    private View mPetNewView;
    private View progressView;
    private View newAdFormView;

    private boolean pictureTaken = false;

    private TakeMeApplication meApplication;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PetDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetNewFragment newInstance() {
        PetNewFragment fragment = new PetNewFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_VIEW_MODE, mViewMode);
        fragment.setArguments(args);
        return fragment;
    }

    public PetNewFragment()
    {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        meApplication = (TakeMeApplication)getActivity().getApplication();

        this.mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mPetNewView = inflater.inflate(R.layout.fragment_pet_new, container, false);
        progressView = mPetNewView.findViewById(R.id.new_ad_progress);
        newAdFormView = mPetNewView.findViewById(R.id.new_ad_form);

        petPicture = (ImageView)mPetNewView.findViewById(R.id.picture);
        petPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
        petPicture.setOnClickListener(this);

        btnSave = (Button)mPetNewView.findViewById(R.id.save_new_ad);
        btnSave.setOnClickListener(this);

        petNameEditText = (EditText)mPetNewView.findViewById(R.id.etPetName);
        petDescEditText = (EditText)mPetNewView.findViewById(R.id.etPetDescription);

        setAgeAdapter();
        setAnimalAdapter();
        setSizeAdapter();
        setGenderAdapter();

        getActivity().getActionBar().setTitle("Add Pet");

        return mPetNewView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if(!mNavigationDrawerFragment.isDrawerOpen())
        {
            getActivity().getActionBar().setTitle("Create Ad");
            menu.clear();
            return;
        }

        super.onCreateOptionsMenu(menu, inflater);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            newAdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            newAdFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    newAdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            newAdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    //Set the animal types values set.
    private void setAgeAdapter()
    {
        spAge = (Spinner)mPetNewView.findViewById(R.id.spPetAge);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        for (int i = Constants.MIN_AGE; i <= Constants.MAX_AGE; i++) {
            adapter.add(String.valueOf(i));
        }
        adapter.add("Select Age");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        spAge.setAdapter(adapter);
        spAge.setSelection(adapter.getCount()); //display hint
    }

    //Set the animal types values set.
    private void setAnimalAdapter()
    {
        spType = (Spinner)mPetNewView.findViewById(R.id.spPetType);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> animalAdapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.new_pet_type, android.R.layout.simple_spinner_dropdown_item);

        //Specify the layout to use when the list of choices appears
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        spType.setAdapter(animalAdapter);
    }

    //Set the animal types values set.
    private void setSizeAdapter()
    {
        spSize = (Spinner)mPetNewView.findViewById(R.id.spPetSize);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> animalAdapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.new_pet_size, android.R.layout.simple_spinner_dropdown_item);

        //Specify the layout to use when the list of choices appears
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        spSize.setAdapter(animalAdapter);
    }

    //Set the animal types values set.
    private void setGenderAdapter()
    {
        spGender = (Spinner)mPetNewView.findViewById(R.id.spPetGender);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> animalAdapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.new_pet_gender, android.R.layout.simple_spinner_dropdown_item);

        //Specify the layout to use when the list of choices appears
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        spGender.setAdapter(animalAdapter);
    }

    private void startCamera(){

        dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {

                handleError(ex.getMessage());
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(pictureFile));

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode,data );

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            //Load the picture to view and cache.
            petPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.with(getActivity().getApplicationContext()).load(mCurrentPhotoPath)
                    .placeholder(R.drawable.ic_take_me)
                    .error(R.drawable.ic_take_me)
                    .centerCrop().fit()
                    .into(petPicture);

            pictureTaken = true;
        }

    }

    private File createImageFile() throws IOException {

        // Create an image unique file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }



    private void handleError(String errorMessage){
        showProgress(false);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.picture)
            startCamera();
        else if (view.getId() == R.id.save_new_ad)

            //Check data is valid
            if (validateDetails())
                saveNewAd();
    }

    private boolean validateDetails(){

        if (petNameEditText.getText().toString().isEmpty()){
            petNameEditText.setError(getString(R.string.msg_error_pet_name));
            return false;
        }

        if (petDescEditText.getText().toString().isEmpty()){
            petDescEditText.setError(getString(R.string.msg_error_pet_description));
            return false;
        }

        if (!pictureTaken){

            Toast.makeText(getActivity(), getActivity().getString(R.string.msg_error_pet_picture), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void saveNewAd(){

        //Show progress bar until ad saved.
        showProgress(true);

        // First try to upload image to s3
        AwsS3Provider.getInstance().uploadImage(calcPicFileName(), pictureFile, this);
        //onUploadToS3Completed(null);
    }

    private String calcPicFileName(){

        java.util.Date date= new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        String currTimestamp =  new SimpleDateFormat("MMddyyyyHHmmss").format(timestamp);

        return (meApplication.getCurrentUser() + currTimestamp + ".jpg");
    }

    @Override
    public void onUploadToS3Completed(String uploadResult) {

        //After image uploaded, create the ad
        Pet pet = new Pet();
        pet.setPetName(petNameEditText.getText().toString());
        pet.setPetType(String.valueOf(spType.getSelectedItemPosition() + 1));
        pet.setPetSize(String.valueOf(spSize.getSelectedItemPosition() + 1));
        pet.setPetAge(String.valueOf(spAge.getSelectedItemPosition() + 1));
        pet.setPetGender(String.valueOf(spGender.getSelectedItemPosition() + 1));
        pet.setPetDescription(petDescEditText.getText().toString());
        pet.setPetPhotoUrl(uploadResult);

        PetCreateAdTask petCreateAdTask = new PetCreateAdTask(meApplication.getCurrentUser(),pet,this);
        petCreateAdTask.createPetAd();
    }

    @Override
    public void onUploadToS3Failed() {
        showProgress(false);
        Toast.makeText(meApplication.getApplicationContext(),"Error occurred trying to Created pet ad",Toast.LENGTH_LONG);
        getFragmentManager().popBackStack();
    }

    @Override
    public void onPetCreateAdSuccess() {
        showProgress(false);
        Toast.makeText(getActivity().getApplicationContext(), "Pet ad Created successfully", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onPetCreateAdFailed() {
        showProgress(false);
        Toast.makeText(getActivity().getApplicationContext(), "Error occurred trying to Created pet ad", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onRestCallError(Throwable t) {
        showProgress(false);
        Toast.makeText(getActivity().getApplicationContext(), "Error occurred trying to Created pet ad", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }


}
