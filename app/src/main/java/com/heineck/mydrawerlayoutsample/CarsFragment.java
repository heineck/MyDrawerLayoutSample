package com.heineck.mydrawerlayoutsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarsFragment extends Fragment implements OnDrawerItemListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG_NAME = "PLANETS_FRAGMENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mTxtView;

    private static DrawerDatasource menuDatasource = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarsFragment newInstance(String param1, String param2, DrawerDatasource datasource) {
        CarsFragment fragment = new CarsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        menuDatasource = datasource;

        return fragment;
    }

    public CarsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);

        mTxtView = (TextView)rootView.findViewById(R.id.txtView);

        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.d(TAG_NAME, "[onHiddenChanged] - INIT");

        if (hidden == false) {
            updateMenu();
        }

    }

    @Override
    public void onItemClick(int position, String data) {

        Log.d("Cars onItemClick", "position: " + position + "data: " + data);

        mTxtView.setText(data);

    }

    private void updateMenu() {

        Log.d("Cars updateMenu", "INIT");

        ArrayList<String> data = new ArrayList<String>();
        data.add("Toyota");
        data.add("Honda");
        data.add("Mitsubishi");

        if (menuDatasource != null) {

            Log.d("Planets updateMenu", "datasource OK");
            menuDatasource.setDatasource(data);

        }

    }

}
