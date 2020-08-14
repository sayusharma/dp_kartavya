package com.e.dpkartavya;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProviderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Spinner driverSpinner,watchmanSpinner,tenantSpinner,servantSpinner,sweeperSpinner,carcleanerSpinner,otherSpinner;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServiceProviderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceProviderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceProviderFragment newInstance(String param1, String param2) {
        ServiceProviderFragment fragment = new ServiceProviderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_service_provider, container, false);
        driverSpinner = view.findViewById(R.id.driverVerStatus);
        watchmanSpinner = view.findViewById(R.id.WatchmanVerStatus);
        tenantSpinner = view.findViewById(R.id.TenantVerStatus);
        servantSpinner = view.findViewById(R.id.ServantVerStatus);
        sweeperSpinner = view.findViewById(R.id.SweeperVerStatus);
        carcleanerSpinner = view.findViewById(R.id.CarCleanerVerStatus);
        otherSpinner = view.findViewById(R.id.OthersVerStatus);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getActivity(),
                R.array.verification, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(adapters);
        watchmanSpinner.setAdapter(adapters);
        tenantSpinner.setAdapter(adapters);
        servantSpinner.setAdapter(adapters);
        sweeperSpinner.setAdapter(adapters);
        carcleanerSpinner.setAdapter(adapters);
        otherSpinner.setAdapter(adapters);
        return view;
    }
}