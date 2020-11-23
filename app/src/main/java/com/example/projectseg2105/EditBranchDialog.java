package com.example.projectseg2105;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditBranchDialog extends AppCompatDialogFragment {
    private EditText branchName;
    private EditText branchAddress;
    private EditText phoneNumber;
    private CheckBox driversCheck;
    private CheckBox healthCheck;
    private CheckBox photoCheck;

    private TextView mondayStart, mondayEnd;
    private EditBranchDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        initializeTimes();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_branch, null);

        builder.setView(view).setTitle("Edit Branch")
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String branch_name = branchName.getText().toString();
                                    String add = branchAddress.getText().toString();
                                    String phoneNum = phoneNumber.getText().toString();
                                    Boolean drivers_license = driversCheck.isChecked();
                                    Boolean health_card = healthCheck.isChecked();
                                    Boolean photoID = photoCheck.isChecked();

                                    if(TextUtils.isEmpty(branch_name) || TextUtils.isEmpty(add) || TextUtils.isEmpty(phoneNum)){
                                        Toast.makeText( getContext(), "A field is missing a value", Toast.LENGTH_SHORT).show();
                                    }
                                    listener.applyResults(branch_name, add, phoneNum, drivers_license, health_card, photoID);
                                }
                            });
        branchName = view.findViewById(R.id.addBranchName);
        branchAddress = view.findViewById(R.id.addAddress);
        phoneNumber = view.findViewById(R.id.addPhone);
        driversCheck = view.findViewById(R.id.driversCheck);
        healthCheck = view.findViewById(R.id.healthCheck);
        photoCheck = view.findViewById(R.id.photoCheck);

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (EditBranchDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement AddServiceDialogListener");
        }

    }

    public interface EditBranchDialogListener{
        void applyResults(String branch_name, String address, String phone, Boolean drivers_license, Boolean health_card, Boolean photo_ID);
    }

    private void initializeTimes(){
        mondayStart = getView().findViewById(R.id.monday_Start);
        mondayEnd = getView().findViewById(R.id.monday_start1);


    }
}
