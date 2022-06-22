package com.foxy.arrive5.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxy.arrive5.R;

public class AddCardsFragment extends Fragment {

    public AddCardsFragment() {
    }

    public static AddCardsFragment newInstance() {
        AddCardsFragment fragment = new AddCardsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_cards, container, false);
    }
}
