package com.takeme.takemeapp.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.takeme.models.Pet;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

import java.util.List;

/**
 * Created by tzamir on 9/13/2015.
 */
public class PetsListAdapter extends ArrayAdapter<Pet>
{
    WishListClickListener wishListClickListener;
    List<Pet> lsPets;
    View convertView;
    private TakeMeApplication meApplication;

    public PetsListAdapter(Context context, List<Pet> lsPets, WishListClickListener wishListClickListener) {
        super(context, R.layout.pet_list_item, lsPets);
        this.wishListClickListener = wishListClickListener;
        this.lsPets = lsPets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.pet_list_item, parent, false);
        this.convertView = convertView;

        Pet pet = getItem(position);

        // Set Pet Name
        TextView petNameTextView = (TextView) convertView.findViewById(R.id.tvPetName);
        petNameTextView.setText(pet.getPetName());

        // Set Pet Short Description
        TextView petShortDec = (TextView) convertView.findViewById(R.id.tvPetShortDesc);
        petShortDec.setText(pet.getShortDescription());

        // Set Pet Image
        ImageView petImage = (ImageView) convertView.findViewById(R.id.imgPetImage);
        Picasso.with(getContext()).load(pet.getPetPhotoUrl()).into(petImage);

        // Set Wish List Button
        CheckBox petWishListCheckBox = (CheckBox) convertView.findViewById(R.id.cbWishList);
        petWishListCheckBox.setTag(position);
        petWishListCheckBox.setChecked(pet.isInWishlist());

        // Set on click wish list button.
        petWishListCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View)v.getParent();
                if (parent != null ) {

                    Pet pet = getItem((Integer) v.getTag());
                    wishListClickListener.onWishListClicked(pet);

                }
            }
        });

        return convertView;
    }

    public interface WishListClickListener{
        public void onWishListClicked(Pet pet);
    }

}
