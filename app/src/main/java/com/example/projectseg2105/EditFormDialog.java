package com.example.projectseg2105;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditFormDialog extends AppCompatDialogFragment {
    private EditText additionalQuestion;
    private EditFormDialog.EditFormDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_form, null);

        builder.setView(view).setTitle("Add Service")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String add_quest = additionalQuestion.getText().toString();
                        listener.applyResults(add_quest);
                    }
                });

        additionalQuestion = view.findViewById(R.id.additionalQuestion);


        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (EditFormDialog.EditFormDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement AddServiceDialogListener");
        }

    }

    public interface EditFormDialogListener{
        void applyResults(String addQuest);
    }
}