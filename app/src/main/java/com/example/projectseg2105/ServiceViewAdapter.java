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

public class ServiceViewAdapter extends RecyclerView.Adapter<ServiceViewAdapter.ViewHolder>{
    private static final String TAG = "ServiceViewAdapter";

    private ArrayList<String> mServiceList = new ArrayList<>();
    private ArrayList<Boolean> mDriversLicenses = new ArrayList<>();
    private ArrayList<Boolean> mHealthCards = new ArrayList<>();
    private ArrayList<Boolean> mPhotoIDs = new ArrayList<>();
    private Context mContext;

    public  ServiceViewAdapter(ArrayList serviceList, ArrayList driversLicenses, ArrayList healthCards, ArrayList photoIDs, Context context){
        mServiceList = serviceList;
        mDriversLicenses = driversLicenses;
        mHealthCards = healthCards;
        mPhotoIDs = photoIDs;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_serviceitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.serviceName.setText(mServiceList.get(position));



        holder.serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mServiceList.get(position));
                Toast.makeText(mContext, mServiceList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, Service_Activity.class);
                intent.putExtra("service_name", mServiceList.get(position));

                mContext.startActivity(intent);
            }
        });
        holder.deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("services").document(mServiceList.get(position))
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
                Toast.makeText(mContext, "Service deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mServiceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView serviceName;
        TextView documentList;
        Button deleteService;
        RelativeLayout serviceLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.service_name);
            serviceLayout = itemView.findViewById(R.id.service_layout);
            deleteService = itemView.findViewById(R.id.delete_service);
        }
    }

    public void removeAt(int position) {
        mServiceList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mServiceList.size());
    }
}
