package com.takeme.takemeapp.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.takeme.services.Constants;
import com.takeme.takemeapp.R;

/**
 * This class represnt Fragments of search of pets
 */
public class PetsSearchFragment extends DialogFragment implements RangeBar.OnRangeBarChangeListener{


    private Spinner mAnimalSpinner;
    private Spinner mGenderSpinner;
    private Spinner mSizeSpinner;
    private Button mSerachButton;

    private RangeBar mAgeBar;

    private TextView mAgeRangeText;

    private OnFragmentSearchInteractionListener mListener;
    private OnSearchClicked mSearchListener = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetsSearchFragment newInstance(String param1, String param2) {
        PetsSearchFragment fragment = new PetsSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }
    public PetsSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_pets_search, container, false);

        mAgeRangeText = (TextView)frag.findViewById(R.id.age_range_text);
        mAgeRangeText.setText(getRangeText(Constants.MIN_AGE, Constants.MAX_AGE+1));

        mAgeBar = (RangeBar)frag.findViewById(R.id.age_range);
        mAgeBar.setOnRangeBarChangeListener(this);
        mAgeBar.setTickCount(Constants.MAX_AGE + 1);
        mAgeBar.setThumbIndices(Constants.MIN_AGE,Constants.MAX_AGE);
        setAnimalAdapter(frag);
        setGenderAdapter(frag);
        setSizeAdapter(frag);

        mSerachButton = (Button) frag.findViewById(R.id.btnSerach);
        mSerachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSearchListener != null) {
                    mSearchListener.onSearchClicked(
                            mAnimalSpinner.getSelectedItemPosition(),
                            mSizeSpinner.getSelectedItemPosition(),
                            mGenderSpinner.getSelectedItemPosition(),
                            mAgeBar.getLeftIndex(),
                            mAgeBar.getRightIndex());
                }
            }
        });
        return frag;
    }

    //Set the animal types values set.
    private void setAnimalAdapter(View frag)
    {
        mAnimalSpinner = (Spinner)frag.findViewById(R.id.animal_spinner);

//      Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> animalAdapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.pet_type, android.R.layout.simple_spinner_dropdown_item);

//      Specify the layout to use when the list of choices appears
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//      Apply the adapter to the spinner
        mAnimalSpinner.setAdapter(animalAdapter);
    }

    //Set the animal types values set.
    private void setGenderAdapter(View frag)
    {
        mGenderSpinner = (Spinner)frag.findViewById(R.id.gender_spinner);

//      Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> genderAdapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.pet_gender, android.R.layout.simple_spinner_dropdown_item);

//      Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//      Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderAdapter);
    }

    //Set the animal types values set.
    private void setSizeAdapter(View frag)
    {
        mSizeSpinner = (Spinner)frag.findViewById(R.id.size_spinner);

//      Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sizeAdapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.pet_size, android.R.layout.simple_spinner_dropdown_item);

//      Specify the layout to use when the list of choices appears
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//      Apply the adapter to the spinner
        mSizeSpinner.setAdapter(sizeAdapter);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentSearchInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentSearchInteractionListener) activity;
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

    private String getRangeText(int from,int to){
        return (Integer.toString(from) + "   -   " + Integer.toString(to));
    }
    @Override
    public void onIndexChangeListener(RangeBar rangeBar, int min, int max) {

        mAgeRangeText.setText(getRangeText(min,max));
    }

    public void setSearchClickedListener(OnSearchClicked searchClickedListener)
    {
        mSearchListener = searchClickedListener;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentSearchInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentSearchInteraction(Uri uri);
    }


    public interface  OnSearchClicked{
        public void onSearchClicked(int animalType,
                                    int animalSize,
                                    int animalGender,
                                    int animalAgeFrom,
                                    int animalAgeTo);
    }
}
