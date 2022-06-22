package com.foxy.arrive5.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxy.arrive5.Adapter.RecyclerTextAdapter;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.VehicleType;
import com.foxy.arrive5.utils.SavePref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SelectPoolFragment extends Fragment {
    LinearLayout type_layout;
    RecyclerView recycler_bottom;
    SavePref savePref;
    List<VehicleType> vehicleType = new ArrayList<>();
    private TextView txtName;

    public SelectPoolFragment() {
    }

    public static SelectPoolFragment newInstance() {
        SelectPoolFragment fragment = new SelectPoolFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_pool, container, false);
        savePref = new SavePref(getContext());
        type_layout = v.findViewById(R.id.type_layout);
        txtName = v.findViewById(R.id.txtName);
        recycler_bottom = v.findViewById(R.id.recycler_bottom);
        Bundle bundle = getArguments();
        String list = bundle.getString("vehicledetails");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {
            jsonObject = new JSONObject(list);
            jsonArray = jsonObject.getJSONArray("vehicleType");
            Type listType = new TypeToken<List<VehicleType>>() {
            }.getType();
            vehicleType = new Gson().fromJson(jsonArray.toString(), listType);
            String name = jsonObject.getString("vehicleTypeName");
            txtName.setText(name);
            Log.e("name vehicle type", name);
            RecyclerTextAdapter adapter = new RecyclerTextAdapter(getContext(), vehicleType);
            recycler_bottom.setAdapter(adapter);
            recycler_bottom.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
}
