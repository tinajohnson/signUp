package com.thinkfoss.signupapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by tina on 29/5/16.
 */


public class SelectCityFragment extends DialogFragment{

    private  int itemIndex;
    public String selectedItem;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String selecteditems);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] cities = {"Seattle", "San Ramon", "Los Angeles", "New Mexico", "Dallas", "Chicago",
                            "New York", "Boston", "DC", "Toronto"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select City");
        builder.setSingleChoiceItems(R.array.cities, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                itemIndex = which;

            }
        });
        builder.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItem = cities[itemIndex];
                mListener.onDialogPositiveClick(SelectCityFragment.this, selectedItem );

            }

        });

        return builder.create();
    }
}
