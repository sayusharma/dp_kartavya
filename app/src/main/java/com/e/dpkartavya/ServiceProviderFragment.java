package com.e.dpkartavya;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.dpkartavya.Adapter.ServiceAdapter;
import com.e.dpkartavya.Common.CurrentServiceProviderList;
import com.e.dpkartavya.Model.ServiceProvider;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProviderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private ServiceAdapter serviceAdapter;
    private ArrayList<ServiceProvider> arrayList;
    private static final String ARG_PARAM2 = "param2";
    private EditText name,addr,notes;
    private Spinner verStatus,serType;
    private RecyclerView recyclerView;
    private Button addService;

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
        name = view.findViewById(R.id.serName);
        addr = view.findViewById(R.id.serAddress);
        recyclerView = view.findViewById(R.id.serviceProvideRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notes = view.findViewById(R.id.verNotes);
        arrayList = new ArrayList<>();
        verStatus = view.findViewById(R.id.verStatus);
        serType = view.findViewById(R.id.serviceType);
        addService = view.findViewById(R.id.btnAddServiceProvider);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getActivity(),
                R.array.verification, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapters2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.service_type, R.layout.spinner_item_text);
        adapters2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        verStatus.setAdapter(adapters);
        serType.setAdapter(adapters2);
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(notes.getText())){
                    Toast.makeText(getActivity(),"FIELDS CANNOT BE EMPTY!",Toast.LENGTH_SHORT).show();
                }
                else if (verStatus.getSelectedItemPosition() == 0){
                    Toast.makeText(getActivity(),"SELECT VERIFICATION STATUS",Toast.LENGTH_SHORT).show();
                }
                else if (serType.getSelectedItemPosition() == 0){
                    Toast.makeText(getActivity(),"SELECT SERVICE TYPE",Toast.LENGTH_SHORT).show();
                }
                else{
                    ServiceProvider serviceProvider = new ServiceProvider(serType.getSelectedItem().toString(),name.getText().toString(),addr.getText().toString(),verStatus.getSelectedItem().toString(),notes.getText().toString());
                    arrayList.add(serviceProvider);
                    name.setText("");
                    addr.setText("");
                    notes.setText("");
                    verStatus.setSelection(0);
                    serType.setSelection(0);
                    serviceAdapter = new ServiceAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(serviceAdapter);
                    CurrentServiceProviderList.currentServiceList = arrayList;
                }
            }
        });
        return view;
    }
}