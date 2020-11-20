package com.example.projectseg2105;

import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.ViewHolder>{
    private static final String TAG = "UserViewAdapter";

    private ArrayList<String> mUserEmails = new ArrayList<>();
    private ArrayList<String> mUserTypes = new ArrayList<>();
    private Context mContext;

    public  UserViewAdapter(ArrayList userEmails, ArrayList userTypes, Context context){
        mUserEmails = userEmails;
        mUserTypes = userTypes;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_useritem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.userEmail.setText(mUserEmails.get(position));
        holder.userType.setText(mUserTypes.get(position));

        holder.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mUserEmails.get(position));

                Toast.makeText(mContext, mUserEmails.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").document(mUserEmails.get(position))
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
        return mUserEmails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userEmail;
        TextView userType;
        Button deleteUser;
        RelativeLayout userLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.user_email);
            userType = itemView.findViewById(R.id.user_type);
            userLayout = itemView.findViewById(R.id.user_layout);
            deleteUser = itemView.findViewById(R.id.delete_user);
        }
    }

    public void removeAt(int position) {
        mUserEmails.remove(position);
        mUserTypes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mUserEmails.size());
    }
}
