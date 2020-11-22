package com.example.projectseg2105;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddServiceDialog extends AppCompatDialogFragment {
    private EditText serviceName;
    private CheckBox driversCheck;
    private CheckBox healthCheck;
    private CheckBox photoCheck;
    private AddServiceDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_service, null);

        builder.setView(view).setTitle("Add Service")
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String service_name = serviceName.getText().toString();
                                    Boolean drivers_license = driversCheck.isChecked();
                                    Boolean health_card = healthCheck.isChecked();
                                    Boolean photoID = photoCheck.isChecked();
                                    listener.applyResults(service_name, drivers_license, health_card, photoID);
                                }
                            });
        serviceName = view.findViewById(R.id.addServiceName);
        driversCheck = view.findViewById(R.id.driversCheck);
        healthCheck = view.findViewById(R.id.healthCheck);
        photoCheck = view.findViewById(R.id.photoCheck);

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (AddServiceDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement AddServiceDialogListener");
        }

    }

    public interface AddServiceDialogListener{
        void applyResults(String service_name, Boolean drivers_license, Boolean health_card, Boolean photo_ID);
    }
}
