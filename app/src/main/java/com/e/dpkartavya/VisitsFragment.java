package com.e.dpkartavya;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Interface.ItemClickListener;
import com.e.dpkartavya.Model.VerifySnr;
import com.e.dpkartavya.Model.Visit;
import com.e.dpkartavya.ViewHolder.VerificationHolder;
import com.e.dpkartavya.ViewHolder.VisitHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisitsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
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
        databaseReference = firebaseDatabase.getReference("visits");
        final ProgressDialog p = new ProgressDialog(getContext());
        p.setMessage("Loading....");
        p.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                p.dismiss();
            }
        },3500);
        recyclerView = view.findViewById(R.id.visitRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        FirebaseRecyclerAdapter<Visit, VisitHolder> adapter = new FirebaseRecyclerAdapter<Visit, VisitHolder>(
                Visit.class,R.layout.item_visit,VisitHolder.class,databaseReference.orderByChild("police_officer").equalTo(CurrentUser.currentUser.getMob())
        ) {
            @Override
            protected void populateViewHolder(VisitHolder verirySnrViewHolder, Visit verifySnr, int i) {
                verirySnrViewHolder.name.setText(verifySnr.getName());
                verirySnrViewHolder.mob.setText(verifySnr.getMob());
                verirySnrViewHolder.addr.setText(verifySnr.getAddr());
                verirySnrViewHolder.time.setText(verifySnr.getTime());
                verirySnrViewHolder.date.setText(verifySnr.getDate());
                Picasso.get()
                        .load(verifySnr.getPhoto())
                        .into(verirySnrViewHolder.img);
                verirySnrViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getContext(),"CAN'T ACCESS",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        return view;
    }
}