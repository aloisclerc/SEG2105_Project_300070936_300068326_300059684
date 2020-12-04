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
    private EditText addAdditionalQuestion;
    private EditText addDocument1;
    private EditText addDocument2;
    private EditText add_rate;
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
                                    String addQuest = addAdditionalQuestion.getText().toString();
                                    String addDoc1 = addDocument1.getText().toString();
                                    String addDoc2 = addDocument2.getText().toString();
                                    String addRate = add_rate.getText().toString();


                                    listener.applyResults(service_name, addQuest, addDoc1, addDoc2, addRate);
                                }
                            });
        serviceName = view.findViewById(R.id.addServiceName);
        addAdditionalQuestion = view.findViewById(R.id.addAdditionalQuestion);
        addDocument1 = view.findViewById(R.id.addDocument1);
        addDocument2 = view.findViewById(R.id.addDocument2);
        add_rate = view.findViewById(R.id.addRate);

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
        void applyResults(String service_name, String addQuest, String addDoc1, String addDoc2, String addRate);
    }
}
