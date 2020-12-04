package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditDocsDialog extends AppCompatDialogFragment {
    private EditText firstDoc;
    private EditText secondDoc;
    private EditDocsDialog.EditDocsDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_docs, null);

        builder.setView(view).setTitle("Add Service")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String first_doc = firstDoc.getText().toString();
                        String second_doc = secondDoc.getText().toString();
                        listener.applyResults(first_doc, second_doc);
                    }
                });

        firstDoc = view.findViewById((R.id.firstDoc));
        secondDoc = view.findViewById((R.id.secondDoc));


        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (EditDocsDialog.EditDocsDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement AddServiceDialogListener");
        }

    }

    public interface EditDocsDialogListener{
        void applyResults(String firstDoc, String secondDoc);
    }
}