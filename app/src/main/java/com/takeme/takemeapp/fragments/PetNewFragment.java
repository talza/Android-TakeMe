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
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;

import com.squareup.picasso.Picasso;
import com.takeme.services.AwsS3Provider;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PetNewFragment extends Fragment implements View.OnClickListener, AwsS3Provider.FileUploadCallBack{ //AddAdListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView petPicture;
    private String mCurrentPhotoPath;
    private File pictureFile = null;
    private Button btnSave;

    private EditText petNameEditText;
    private EditText petAgeEditText;
    private EditText petDescEditText;

    private Spinner spSize;
    private Spinner spType;
    private Spinner spGender;

    private View mPetNewView;
    private View progressView;
    private View newAdFormView;

    private boolean pictureTaken = false;

    private TakeMeApplication meApplication;

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
        petAgeEditText = (EditText)mPetNewView.findViewById(R.id.spPetAge);
        petDescEditText = (EditText)mPetNewView.findViewById(R.id.etPetDescription);

        setAnimalAdapter();
        setSizeAdapter();
        setGenderAdapter();

        return mPetNewView;
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
    private void setAnimalAdapter()
    {
        spType = (Spinner)mPetNewView.findViewById(R.id.spPetType);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> animalAdapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.new_ad_types, android.R.layout.simple_spinner_dropdown_item);

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
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.new_ad_sizes, android.R.layout.simple_spinner_dropdown_item);

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
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.new_ad_genders, android.R.layout.simple_spinner_dropdown_item);

        //Specify the layout to use when the list of choices appears
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        spGender.setAdapter(animalAdapter);
    }

    private void startCamera(){

            dispatchTakePictureIntent();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
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
        if (petAgeEditText.getText().toString().isEmpty()){
            petAgeEditText.setError(getString(R.string.msg_error_pet_age));
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
    }

    private String calcPicFileName(){

        java.util.Date date= new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        String currTimestamp =  new SimpleDateFormat("MMddyyyyHHmmss").format(timestamp);

        return (meApplication.getCurrentUser() + currTimestamp + ".jpg");
    }
    @Override
    public void onUploadToS3Completed(UploadResult uploadResult) {

        //After image uploaded, saving the ad
//        AddAdTask addAdTask = new AddAdTask(this,petDescEditText.getText().toString(),
//                sizeSpinner.getSelectedItem().toString().toUpperCase(),typeSpinner.getSelectedItem().toString().toUpperCase(),
//                app.getCurrentUser(),petNameEditText.getText().toString(),petStoryEditText.getText().toString(),
//                app.getPicUrl(uploadResult),genderSpinner.getSelectedItem().toString().toUpperCase(),petAgeEditText.getText().toString());
//
//        addAdTask.addAd();
    }

//    @Override
//    public void onPetAdCreated() {
//
//        showProgress(false);
//        Toast.makeText(this, getString(R.string.msg_success_pet_ad_created), Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onPetAdCreatedFailed() {
//        showProgress(false);
//        Toast.makeText(this, R.string.msg_error_pet_ad_created, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onRestCallError(Throwable error) {
//        showProgress(false);
//        Toast.makeText(this, getString(R.string.msg_error_pet_ad_created), Toast.LENGTH_LONG).show();
//    }
}
