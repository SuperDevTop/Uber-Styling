package com.foxy.arrive5.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxy.arrive5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AutoCompleteParser extends AsyncTask<Void, Integer, Void> implements AdapterView.OnItemClickListener {
    private Context context;
    private String url;
    private TextView textView;
    private LinearLayout linearLayout;
    private JSONObject json;
    private JSONArray arrayListDateFromGoogle;
    private ArrayList<String> arrayListResults = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView autoCompleteTextView;
    private Handler handler;

    public AutoCompleteParser(Context context, String url, ArrayList<String> arrayListResults, ArrayAdapter<String> adapter, AutoCompleteTextView cityAutocompleteTv, Handler handler) {
        this.adapter = adapter;
        this.context = context;
        this.textView = textView;
        this.linearLayout = linearLayout;
        this.url = url;
        this.arrayListResults = arrayListResults;
        this.autoCompleteTextView = cityAutocompleteTv;
        this.handler = handler;
    }

    public AutoCompleteParser() {

    }


    @Override
    protected Void doInBackground(Void... params) {

        JSONParser jsonParser = new JSONParser();
        json = jsonParser.getJSONFromUrl(url);
        if (json != null) {
            try {
                arrayListDateFromGoogle = json.getJSONArray("predictions");
                for (int i = 0; i < arrayListDateFromGoogle.length(); i++) {
                    JSONObject c = arrayListDateFromGoogle.getJSONObject(i);
                    String description = c.getString("description");
                    String placeId = c.getString("place_id");
                    Log.d("map_description", "from map data" + description);
                    arrayListResults.add(description);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        adapter = new ArrayAdapter<String>(context,
                R.layout.layout_text_address, arrayListResults) {

            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view
                        .findViewById(android.R.id.text1);
                return view;
            }
        };
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        autoCompleteTextView.setText(arrayListResults.get(position));
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


}
