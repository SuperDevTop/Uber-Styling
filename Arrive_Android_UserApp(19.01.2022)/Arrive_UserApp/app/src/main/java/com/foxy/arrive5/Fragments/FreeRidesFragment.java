package com.foxy.arrive5.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.ReadPref;

import static com.foxy.arrive5.SideMenu.HomeFragment.setSystemBarTheme;

public class FreeRidesFragment extends Fragment {

    TextView txtCode;
    ReadPref readPref;
    ImageView imgInvite;
    String URL_TO_SHARE;
    public FreeRidesFragment() {
    }

    public static FreeRidesFragment newInstance() {
        FreeRidesFragment fragment = new FreeRidesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_free_rides, container, false);
        getActivity().setTitle(getResources().getString(R.string.free_rides));
        txtCode = view.findViewById(R.id.txtCode);
        imgInvite = view.findViewById(R.id.imgInvite);
        readPref = new ReadPref(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.darkBg));
        }
        setSystemBarTheme(getActivity(), true);
        URL_TO_SHARE = "" + getContext().getResources().getString(R.string.code_App) + " " + readPref.getInviteCode();
        txtCode.setText(readPref.getInviteCode());
        imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, URL_TO_SHARE);
                startActivity(Intent.createChooser(intent, "" + getContext().getResources().getString(R.string.share)));
            }
        });
        return view;
    }

}
