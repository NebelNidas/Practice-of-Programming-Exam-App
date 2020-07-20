package de.cicero_interactive.practiceofprogrammingexamapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import org.json.JSONException;

import java.util.ArrayList;

public class AddNumberDialog extends AppCompatDialogFragment {
    EditText number_chooser;
    AddDialogListener addDialogListener;

    String title;
    String hint;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            addDialogListener = (AddDialogListener) context;
        } catch (ClassCastException e) {
            throw(new ClassCastException(context.toString() + " must implement AddDialogListener!"));
        }
    }

    public interface AddDialogListener {
        void passDataBack(int number) throws JSONException;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = getArguments();
        title = bundle.getString("title");
        hint = bundle.getString("hint");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_add_number, null);
        number_chooser = view.findViewById(R.id.number_chooser);

        number_chooser.setHint(hint);

        builder.setView(view)
                .setTitle(title)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int number;
                        if (!number_chooser.getText().toString().matches("")) {
                            number = Integer.parseInt(number_chooser.getText().toString());
                            try {
                                addDialogListener.passDataBack(number);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please enter a number!", Toast.LENGTH_SHORT).show();
                            // TODO: Dialog shouldn't close here
                        }
                    }
                });

        return builder.create();
    }
}
