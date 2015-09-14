package com.takeme.takemeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.takeme.models.Pet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzamir on 9/13/2015.
 */
public class PetsListAdapter extends ArrayAdapter<Pet>
{

    List<Pet> lsPets;
    View convertView;

    public PetsListAdapter(Context context, List<Pet> lsPets) {
        super(context, R.layout.pet_list_item, lsPets);

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
        petNameTextView.setText(pet.getName());

        // Set Pet Short Description
        TextView petShortDec = (TextView) convertView.findViewById(R.id.tvPetShortDesc);
        petShortDec.setText(pet.getShortDescription());

        // Set Pet Image
        ImageView petImage = (ImageView) convertView.findViewById(R.id.imgPetImage);
        Picasso.with(getContext()).load(pet.getImgUrl()).into(petImage);

        // Set Wish List Button
        ImageButton petWishListImageButton = (ImageButton) convertView.findViewById(R.id.ibWishList);
        petWishListImageButton.setTag(position);

        int petWishListImageResource = android.R.drawable.btn_star_big_off;
        if (pet.isWishInList())
        {
            petWishListImageResource =  android.R.drawable.btn_star_big_on;
        }
        petWishListImageButton.setImageResource(petWishListImageResource);

        // Set on click wish list button.
        petWishListImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(), "Button Click Row Number " + v.getTag(),Toast.LENGTH_SHORT ).show();
                View parent = (View)v.getParent();
                if (parent != null ) {
                    ImageButton petWishListImageButton = (ImageButton) parent.findViewById(v.getId());

                    Pet pet = getItem((Integer) v.getTag());
                    int ndrawableResource = android.R.drawable.btn_star_big_off;
                    if (pet.isWishInList())
                    {
                        ndrawableResource =  android.R.drawable.btn_star_big_on;
                    }
                    pet.setIsWishInList(!pet.isWishInList());
                    petWishListImageButton.setImageResource(ndrawableResource);
                }
            }
        });

        return convertView;
    }

}
