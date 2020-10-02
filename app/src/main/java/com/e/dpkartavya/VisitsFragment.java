package com.e.dpkartavya;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.e.dpkartavya.Adapter.VisitAdapter;
import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Interface.ItemClickListener;
import com.e.dpkartavya.Model.Visit;
import com.e.dpkartavya.ViewHolder.VisitViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisitsFragment extends Fragment implements VisitAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private VisitAdapter orderAdapter;
    private ArrayList<Visit> currentList;
    private ArrayList<Visit> arrayList;
    private DatabaseReference databaseReference;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VisitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisitsFragment newInstance(String param1, String param2) {
        VisitsFragment fragment = new VisitsFragment();
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
        View view = inflater.inflate(R.layout.fragment_visits, container, false);
        // Toast.makeText(getContext(),"YOU THERE",Toast.LENGTH_LONG).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("my_visits").child(CurrentUser.currentUser.getMob());
        arrayList = new ArrayList<>();
        currentList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.visitRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        //Query query = databaseReference.orderByKey();

        FirebaseRecyclerAdapter<Visit, VisitViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Visit, VisitViewHolder>(Visit.class,
                R.layout.item_visit,VisitViewHolder.class,databaseReference.orderByKey()) {
            @Override
            protected void populateViewHolder(VisitViewHolder visitViewHolder, Visit visit, int i) {
                visitViewHolder.complaint.setText(visit.getComplaint());
                visitViewHolder.date.setText(visit.getDate());
                visitViewHolder.mob.setText(visit.getMob());
                visitViewHolder.name.setText(visit.getName());
                visitViewHolder.notes.setText(visit.getNotes());
                visitViewHolder.time.setText(visit.getTime());
                visitViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        return view;
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(getContext(),"CAN'T ACCESS",Toast.LENGTH_SHORT).show();
    }
}