package com.e.dpkartavya;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.dpkartavya.Adapter.ChildrenAdapter;
import com.e.dpkartavya.Common.CurrentChildrenList;
import com.e.dpkartavya.Model.Children;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicDetailsFragment extends Fragment {
    private EditText dob_senior,dob_spouse,wedding,name,mob;
    private DatePickerDialog pickerSeniorDialog,pickerSpouseDialog,pickerWeddingDialog;
    private ChildrenAdapter childrenAdapter;
    private ImageView relativeLayout,relSpouse,relWedding;
    private Spinner childrenSpinner;
    private ArrayList<Children> arrayList;
    private Button addChildren;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BasicDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicDetailsFragment newInstance(String param1, String param2) {
        BasicDetailsFragment fragment = new BasicDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_basic_details, container, false);
        dob_senior = view.findViewById(R.id.dob_senior);
        relativeLayout = view.findViewById(R.id.dob_senior_relative);
        arrayList = new ArrayList<>();
        wedding = view.findViewById(R.id.wedding);
        relWedding = view.findViewById(R.id.wedding_relative);
        recyclerView = view.findViewById(R.id.childrenRecycler);
        name = view.findViewById(R.id.childrenName);
        mob = view.findViewById(R.id.childrenMob);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setHasFixedSize(true);
        addChildren = view.findViewById(R.id.btnAddChildren);
        dob_spouse = view.findViewById(R.id.dob_spouse);
        relSpouse = view.findViewById(R.id.dob_spouse_relative);
        childrenSpinner = view.findViewById(R.id.childrenResidence);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getActivity(),
                R.array.children_residence, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childrenSpinner.setAdapter(adapters);
        addChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(mob.getText())){
                    Toast.makeText(getActivity(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_SHORT).show();
                }
                else if(mob.getText().length()!=10){
                    Toast.makeText(getActivity(),"MOB NO CANNOT BE LESS THAN 10 DIGITS",Toast.LENGTH_SHORT).show();
                }
                else if(childrenSpinner.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(),"PLEASE SELECT RESIDENCE",Toast.LENGTH_SHORT).show();
                }
                else {
                    Children children = new Children(name.getText().toString(),mob.getText().toString(),childrenSpinner.getSelectedItem().toString());
                    name.setText("");
                    mob.setText("");
                    childrenSpinner.setSelection(0);
                    arrayList.add(children);
                    childrenAdapter = new ChildrenAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(childrenAdapter);
                    CurrentChildrenList.currentChildrenList = arrayList;
                    //Toast.makeText(getActivity(),""+ recyclerView.getAdapter().getItemCount(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerSeniorDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob_senior.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerSeniorDialog.show();
                pickerSeniorDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


            }
        });
        relSpouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerSpouseDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob_spouse.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerSpouseDialog.show();
                pickerSpouseDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        relWedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerWeddingDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                wedding.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerWeddingDialog.show();
                pickerWeddingDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        return view;
    }
}