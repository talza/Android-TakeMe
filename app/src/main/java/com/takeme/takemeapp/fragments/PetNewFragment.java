package com.takeme.takemeapp.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.takeme.models.Pet;
import com.takeme.services.AwsS3Provider;
import com.takeme.services.Constants;
import com.takeme.services.PetCreateAdTask;
import com.takeme.services.PetUpdateAdTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represent fragment of Save new/update pets
 */
public class PetNewFragment extends Fragment implements
        View.OnClickListener,
        AwsS3Provider.FileUploadCallBack,
        PetCreateAdTask.PetCreateAdResponse,
        PetUpdateAdTask.PetUpdateAdResponse{

    private Constants.PetUpdateMode mPetUpdateMode;

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

    private boolean pictureTaken = false;
    private boolean existingPic = false;

    private TakeMeApplication meApplication;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Create new instance of update pets with parametes
     * @param petId -   pet id if exists
     * @param petName - pet name
     * @param petType - pet type
     * @param petSize - pet size
     * @param petAge -  pet age
     * @param petGender - pet gender
     * @param petDesc - pet desciption
     * @param petPicUrl - pet picture url
     * @return
     */
    public static PetNewFragment newInstance(Long petId,
                                             String petName,
                                             int petType,
                                             int petSize,
                                             int petAge,
                                             int petGender,
                                             String petDesc,
                                             String petPicUrl) {

        PetNewFragment fragment = new PetNewFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PET_UPDATE_MODE, Constants.PetUpdateMode.UPDATE.toString());
        args.putLong(Constants.PET_UPDATE_ID, petId);
        args.putString(Constants.PET_UPDATE_NAME, petName);
        args.putInt(Constants.PET_UPDATE_TYPE, petType);
        args.putInt(Constants.PET_UPDATE_SIZE, petSize);
        args.putInt(Constants.PET_UPDATE_AGE, petAge);
        args.putInt(Constants.PET_UPDATE_GENDER, petGender);
        args.putString(Constants.PET_UPDATE_DESC, petDesc);
        args.putString(Constants.PET_UPDATE_PIC_URL, petPicUrl);


        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PetDetailsFragment.
     */
    public static PetNewFragment newInstance() {
        PetNewFragment fragment = new PetNewFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PET_UPDATE_MODE, Constants.PetUpdateMode.CREATE.toString());
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

        if (getArguments() != null) {
            mPetUpdateMode = Constants.PetUpdateMode.valueOf(getArguments().getString(Constants.PET_UPDATE_MODE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mPetNewView = inflater.inflate(R.layout.fragment_pet_new, container, false);

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

        // For update pet mode , put the values of pet in the fields
        if(mPetUpdateMode != null && mPetUpdateMode.equals(Constants.PetUpdateMode.UPDATE)) {
            existingPic = true;
            petNameEditText.setText(getArguments().getString(Constants.PET_UPDATE_NAME));
            spType.setSelection(getArguments().getInt(Constants.PET_UPDATE_TYPE) - 1);
            spSize.setSelection(getArguments().getInt(Constants.PET_UPDATE_SIZE) - 1);
            spAge.setSelection(getArguments().getInt(Constants.PET_UPDATE_AGE) - 1);
            spGender.setSelection(getArguments().getInt(Constants.PET_UPDATE_GENDER) - 1);
            petDescEditText.setText(getArguments().getString(Constants.PET_UPDATE_DESC) );
            mCurrentPhotoPath = getArguments().getString(Constants.PET_UPDATE_PIC_URL);

            Picasso.with(getActivity().getApplicationContext()).load(mCurrentPhotoPath)
                    .placeholder(R.drawable.ic_take_me)
                    .error(R.drawable.ic_take_me)
                    .centerCrop().fit()
                    .into(petPicture);
        }

        return mPetNewView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if(!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Set the title according to pet update/create mode.
            if(mPetUpdateMode.equals(Constants.PetUpdateMode.CREATE)) {
                getActivity().getActionBar().setTitle("Create Ad");
            }else{
                getActivity().getActionBar().setTitle("Update Ad");
            }
            menu.clear();
            return;
        }

        super.onCreateOptionsMenu(menu, inflater);

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
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        for (int i = Constants.MIN_AGE; i <= Constants.MAX_AGE; i++) {
            adapter.add(String.valueOf(i));
        }
        adapter.add("Select Age");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        spAge.setAdapter(adapter);
        spAge.setSelection(adapter.getCount());//display hint
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

    // Open camera
    private void startCamera(){

        dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            pictureFile = null;
            try {
                // Create new file
                pictureFile = createImageFile();
            } catch (IOException ex) {

                handleError(ex.getMessage());
            }

            // Take a picture from camera
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(pictureFile));

            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * if the user take a picture, display it in the screen
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode,data);

        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            //Load the picture to view and cache.
            petPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.with(getActivity().getApplicationContext()).load(mCurrentPhotoPath)
                    .placeholder(R.drawable.ic_take_me)
                    .error(R.drawable.ic_take_me)
                    .centerCrop().fit()
                    .into(petPicture);

            pictureTaken = true;
            existingPic = false;
        }

    }

    // Create new file
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

    // if error occured
    private void handleError(String errorMessage){
        meApplication.hideProgress();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

        // if the user click on picture, then start the camera
        if (view.getId() == R.id.picture)
            startCamera();
        // if the user click on save, the save the pet.
        else if (view.getId() == R.id.save_new_ad)

            //Check data is valid
            if (validateDetails())
                // save the pet
                saveNewAd();
    }

    /**
     * Check input valitation
     * @return true if the validation ok.
     */
    private boolean validateDetails(){

        // Check if the pet name is not empty
        if (petNameEditText.getText().toString().isEmpty()){
            petNameEditText.setError(getString(R.string.msg_error_pet_name));
            return false;
        }

        // Check if the pet have description
        if (petDescEditText.getText().toString().isEmpty()){
            petDescEditText.setError(getString(R.string.msg_error_pet_description));
            return false;
        }

        // Check if the user take a picture
        if (!pictureTaken && !existingPic){

            Toast.makeText(getActivity(), getActivity().getString(R.string.msg_error_pet_picture), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Save the pet data
     */
    private void saveNewAd(){

        //Show progress bar until ad saved.
        meApplication.showProgress(this.getActivity());

        // First try to upload image to s3
        if(!existingPic){
            AwsS3Provider.getInstance().uploadImage(calcPicFileName(), pictureFile, this);
        }else {

            onUploadToS3Completed(this.mCurrentPhotoPath);

        }
    }

    private String calcPicFileName(){

        java.util.Date date= new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        String currTimestamp =  new SimpleDateFormat("MMddyyyyHHmmss").format(timestamp);

        return (meApplication.getCurrentUser() + currTimestamp + ".jpg");
    }

    @Override
    public void onUploadToS3Completed(String uploadResult) {

        //After image uploaded to s3 , save the pet
        Pet pet = new Pet();
        pet.setPetName(petNameEditText.getText().toString());
        pet.setPetType(spType.getSelectedItemPosition() + 1);
        pet.setPetSize(spSize.getSelectedItemPosition() + 1);
        pet.setPetAge(spAge.getSelectedItemPosition() + 1);
        pet.setPetGender(spGender.getSelectedItemPosition() + 1);
        pet.setPetDescription(petDescEditText.getText().toString());
        pet.setPetPhotoUrl(uploadResult);

        // Create or Update the pet
        if (mPetUpdateMode.equals(Constants.PetUpdateMode.CREATE)) {
            PetCreateAdTask petCreateAdTask = new PetCreateAdTask(meApplication.getCurrentUser(), pet, this);
            petCreateAdTask.createPetAd();
        }else if(mPetUpdateMode.equals(Constants.PetUpdateMode.UPDATE)){
            pet.setId(getArguments().getLong(Constants.PET_UPDATE_ID));
            PetUpdateAdTask petUpdateAdTask = new PetUpdateAdTask(meApplication.getCurrentUser(),pet,this);
            petUpdateAdTask.updatePetAd();
        }

    }

    @Override
    public void onUploadToS3Failed() {
        meApplication.hideProgress();
        Toast.makeText(meApplication.getApplicationContext(),"Error occurred trying to save pet ad",Toast.LENGTH_LONG);
        getFragmentManager().popBackStack();
    }

    @Override
    public void onPetCreateAdSuccess() {
        meApplication.hideProgress();
        Toast.makeText(getActivity().getApplicationContext(), "Pet ad Created successfully", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onPetCreateAdFailed() {
        meApplication.hideProgress();
        Toast.makeText(getActivity().getApplicationContext(), "Error occurred trying to Created pet ad", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onPetUpdateAdSuccess() {
        meApplication.hideProgress();
        Toast.makeText(getActivity().getApplicationContext(), "Pet ad updated successfully", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onPetUpdateAdFailed() {
        meApplication.hideProgress();
        Toast.makeText(getActivity().getApplicationContext(), "Error occurred while trying to update pet ad", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onRestCallError(Throwable t) {
        meApplication.hideProgress();
        Toast.makeText(getActivity().getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }


}
