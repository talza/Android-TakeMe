package com.takeme.takemeapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.takeme.models.Pet;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // populate data
        this.mPetsList = new ArrayList<Pet>();
        mPetsList.add(new Pet("1","Princess",null,"1","Female","Medium","http://farm5.staticflickr.com/4142/4787427683_3672f1db9a_s.jpg",null));
        mPetsList.add(new Pet("2","Carmen", null,"1","Male","Medium","http://farm2.staticflickr.com/1008/1420343003_13eeb0f9f3_s.jpg",null));
        mPetsList.add(new Pet("3","Lili",null,"3","Male","Small","http://farm4.staticflickr.com/3139/2780642603_8d2c90e364_s.jpg",null));
        mPetsList.add(new Pet("4","Princess",null,"4","Female","Large","http://farm5.staticflickr.com/4142/4787427683_3672f1db9a_s.jpg",null));
        mPetsList.add(new Pet("5","Princess",null,"1","Male","Large", "http://farm2.staticflickr.com/1008/1420343003_13eeb0f9f3_s.jpg",null));
        mPetsList.add(new Pet("6","Princess",null,"3","Female","Small","http://farm4.staticflickr.com/3139/2780642603_8d2c90e364_s.jpg",null));
        mPetsList.add(new Pet("8","Lili",null,"1","Female","Large", "http://farm2.staticflickr.com/1008/1420343003_13eeb0f9f3_s.jpg",null));
        mPetsList.add(new Pet("9", "Lili", null, "2", "Male", "Small", "http://farm4.staticflickr.com/3139/2780642603_8d2c90e364_s.jpg", null));
        mPetsList.add(new Pet("7","Princess",null,"1","Medium","Princess","http://farm5.staticflickr.com/4142/4787427683_3672f1db9a_s.jpg",null));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pets_list, container, false);
        this.mPetsListView = (ListView) view.findViewById(R.id.lvPets);

        this.mPetsListView.setAdapter(new PetsListAdapter(view.getContext(), this.mPetsList));

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
