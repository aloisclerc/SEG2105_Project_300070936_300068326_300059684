package com.example.projectseg2105;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ApplicationViewAdapter extends RecyclerView.Adapter<ApplicationViewAdapter.ViewHolder>{
    private static final String TAG = "ApplicationViewAdapter";

    private Context mContext;
    private String branchName;
    private String applicationType;
    private ArrayList<String> mFirstNames = new ArrayList<>();
    private ArrayList<String> mLastNames = new ArrayList<>();
    private ArrayList<String> mDOB = new ArrayList<>();
    private ArrayList<String> mAppointmentDates = new ArrayList<>();
    private ArrayList<String> mAddresses = new ArrayList<>();
    private ArrayList<String> mAddReplies = new ArrayList<>();

    public ApplicationViewAdapter(ArrayList firstNames, ArrayList lastNames, ArrayList DOB, ArrayList appointmentDates, ArrayList addresses, ArrayList addReplies, String branch, String appType, Context context) {

        mFirstNames = firstNames;
        mLastNames = lastNames;
        mDOB = DOB;
        mAppointmentDates = appointmentDates;
        mAddresses = addresses;
        mAddReplies = addReplies;
        branchName = branch;
        mContext = context;
        applicationType = appType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_applicationitem, parent, false);
        ApplicationViewAdapter.ViewHolder holder = new ApplicationViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.firstName.setText(mFirstNames.get(position));
        holder.lastName.setText(mLastNames.get(position));
        holder.dob.setText("Date of Birth: "+mDOB.get(position));
        holder.date.setText("Appointment: " +mAppointmentDates.get(position));
        holder.address.setText(mAddresses.get(position));
        holder.reply.setText(mAddReplies.get(position));

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("branches").document(branchName).collection(applicationType).document(mFirstNames.get(position))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                removeAt(position);
                Toast.makeText(mContext, "Application deleted", Toast.LENGTH_SHORT).show();
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("branches").document(branchName).collection(applicationType).document(mFirstNames.get(position))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                removeAt(position);
                Toast.makeText(mContext, "Application deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFirstNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView firstName;
        TextView lastName;
        TextView address;
        TextView dob;
        TextView date;
        TextView reply;
        Button approve;
        Button reject;
        ConstraintLayout appLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.reviewFirstName);
            lastName = itemView.findViewById(R.id.reviewLastName);
            address = itemView.findViewById(R.id.reviewAddress);
            dob = itemView.findViewById(R.id.reviewDOB);
            date = itemView.findViewById(R.id.reviewDate);
            reply = itemView.findViewById(R.id.reviewReply);
            appLayout = itemView.findViewById(R.id.application_layout);
            approve = itemView.findViewById(R.id.approve);
            reject = itemView.findViewById(R.id.reject);
        }
    }

    public void removeAt(int position) {
        mFirstNames.remove(position);
        mLastNames.remove(position);
        mDOB.remove(position);
        mAppointmentDates.remove(position);
        mAddresses.remove(position);
        mAddReplies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mFirstNames.size());
    }
}
