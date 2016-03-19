package com.example.xps.letsall;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.login.widget.ProfilePictureView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mThumbPaths;

    public ImageAdapter(Context c, String[] mThumbPaths) {
        mContext = c;
        this.mThumbPaths = mThumbPaths;
    }

    public int getCount() {
        return mThumbPaths.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ProfilePictureView pView;
        if (convertView == null) {
            pView = new ProfilePictureView(mContext);

        } else {
            pView = (ProfilePictureView) convertView;
        }

        //Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" + mThumbPaths[position]).into(imageView);

        pView.setProfileId("725725727");

        /*Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + mThumbPaths[position]).into(imageView);*/
        return pView;
    }

}