package com.example.projectseg2105;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BranchViewAdapter extends RecyclerView.Adapter<BranchViewAdapter.ViewHolder>{
    private static final String TAG = "BranchViewAdapter";

    private ArrayList<String> mBranchList = new ArrayList<>();
    private ArrayList<String> mAddressList = new ArrayList<>();
    private ArrayList<String> mPhoneList = new ArrayList<>();
    private ArrayList<Boolean> mDriversLicenses = new ArrayList<>();
    private ArrayList<Boolean> mHealthCards = new ArrayList<>();
    private ArrayList<Boolean> mPhotoIDs = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTimes = new ArrayList<>();
    private Context mContext;

    public  BranchViewAdapter(ArrayList branchList, ArrayList addressList, ArrayList phoneList, ArrayList driversLicenses, ArrayList healthCards, ArrayList photoIDs, ArrayList times, Context context){
        mBranchList = branchList;
        mAddressList = addressList;
        mPhoneList = phoneList;
        mDriversLicenses = driversLicenses;
        mHealthCards = healthCards;
        mPhotoIDs = photoIDs;
        mTimes = times;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_branchitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.branchName.setText(mBranchList.get(position));
        holder.addressField.setText(mAddressList.get(position));
        holder.phoneNumber.setText(mPhoneList.get(position));



        holder.branchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mBranchList.get(position));
                Toast.makeText(mContext, mBranchList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, BranchesActivity.class);
                Log.d(TAG, "booleans: " + mDriversLicenses.get(position) + " " + mHealthCards.get(position) + " " + mPhotoIDs.get(position));
                intent.putExtra("branch_name", mBranchList.get(position));
                intent.putExtra("address", mAddressList.get(position));
                intent.putExtra("phone", mPhoneList.get(position));
                intent.putExtra("drivers", mDriversLicenses.get(position));
                intent.putExtra("health", mHealthCards.get(position));
                intent.putExtra("photo", mPhotoIDs.get(position));
                intent.putExtra("times", mTimes.get(position));

                mContext.startActivity(intent);
            }
        });
        holder.deleteBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("branches").document(mBranchList.get(position))
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
                Toast.makeText(mContext, "User deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBranchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView branchName;
        TextView addressField;
        TextView phoneNumber;
        Button deleteBranch;
        RelativeLayout branchLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            branchName = itemView.findViewById(R.id.branch_name);
            addressField = itemView.findViewById(R.id.address);
            phoneNumber = itemView.findViewById(R.id.phone);
            branchLayout = itemView.findViewById(R.id.branch_layout);
            deleteBranch = itemView.findViewById(R.id.delete_branch);
        }
    }

    public void removeAt(int position) {
        mBranchList.remove(position);
        mAddressList.remove(position);
        mPhoneList.remove(position);
        mDriversLicenses.remove(position);
        mHealthCards.remove(position);
        mPhotoIDs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mBranchList.size());
    }
}
