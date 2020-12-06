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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddBranchDialog extends AppCompatDialogFragment {
    private EditText branchName;
    private EditText branchAddress;
    private EditText phoneNumber;
    private CheckBox driversCheck;
    private CheckBox healthCheck;
    private CheckBox photoCheck;
    private AddBranchDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_branch, null);

        builder.setView(view).setTitle("Add Branch")
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

                                    for(char c : phoneNum.toCharArray()){
                                        if (Character.isDigit(c) == false){
                                            Toast.makeText( getContext(), "Phone number must only contain digits (Ex: 1234567890)", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if(TextUtils.isEmpty(branch_name) || TextUtils.isEmpty(add) || TextUtils.isEmpty(phoneNum)){
                                        Toast.makeText( getContext(), "A field is missing a value", Toast.LENGTH_SHORT).show();
                                    } else if(phoneNum.length() != 10){
                                        Toast.makeText( getContext(), "Phone number must contain 10 digits", Toast.LENGTH_SHORT).show();
                                    }else {
                                        listener.applyResults(branch_name, add, phoneNum, drivers_license, health_card, photoID);
                                    }
                                }
                            });
        branchName = view.findViewById(R.id.searchBranchName);
        branchAddress = view.findViewById(R.id.searchAddress);
        phoneNumber = view.findViewById(R.id.searchPhone);
        driversCheck = view.findViewById(R.id.searchDriversCheck);
        healthCheck = view.findViewById(R.id.searchHealthCheck);
        photoCheck = view.findViewById(R.id.searchPhotoCheck);

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (AddBranchDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement AddServiceDialogListener");
        }

    }

    public interface AddBranchDialogListener{
        void applyResults(String branch_name, String address, String phone, Boolean drivers_license, Boolean health_card, Boolean photo_ID);
    }
}
