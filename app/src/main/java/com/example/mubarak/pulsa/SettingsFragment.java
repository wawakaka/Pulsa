package com.example.mubarak.pulsa;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    String PREFS_NAME="MYTEXT";
    String PREFS_NAMEA="MYTEXTA";
    String nO,nM;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
/*
        EditText nomorOperator = (EditText)getActivity().findViewById(R.id.editTextOperator);
        EditText nomorMember = (EditText)getActivity().findViewById(R.id.editTextIdMember);


        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        String mytext = settings.getString("mytext", "");
        nomorOperator.setText(mytext);

        SharedPreferences settingsa = this.getActivity().getSharedPreferences(PREFS_NAMEA, 0);
        String mytexta = settingsa.getString("mytexta", "");
        nomorMember.setText(mytexta);
        */
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
