package com.foxy.arrive5.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxy.arrive5.R;

public class MyRewardsFragment extends Fragment {

    public MyRewardsFragment() {
    }

    public static MyRewardsFragment newInstance() {
        MyRewardsFragment fragment = new MyRewardsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
        getActivity().setTitle(getResources().getString(R.string.my_reward));
        return view;
    }

}
