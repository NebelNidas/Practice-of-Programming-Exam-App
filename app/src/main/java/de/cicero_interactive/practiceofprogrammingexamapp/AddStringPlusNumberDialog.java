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

public class AddStringPlusNumberDialog extends AppCompatDialogFragment {
    EditText text_chooser,
            number_chooser;
    AddDialogListener addDialogListener;

    String title;
    String hint_text_chooser,
            hint_number_chooser;


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
        void passDataBack(String text, int number) throws JSONException;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = getArguments();
        title = bundle.getString("title");
        hint_text_chooser = bundle.getString("hint_text_chooser");
        hint_number_chooser = bundle.getString("hint_number_chooser");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_add_string_plus_number, null);
        text_chooser = view.findViewById(R.id.text_chooser);
        number_chooser = view.findViewById(R.id.number_chooser);

        text_chooser.setHint(hint_text_chooser);
        number_chooser.setHint(hint_number_chooser);

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
                        String text;
                        int number;
                        boolean ok = true;

                        if (text_chooser.getText().toString().matches("")) {
                            ok = false;
                        }
                        if (number_chooser.getText().toString().matches("")) {
                            ok = false;
                        }
                        if (ok == true) {
                            text = text_chooser.getText().toString();
                            number = Integer.parseInt(number_chooser.getText().toString());
                            try {
                                addDialogListener.passDataBack(text, number);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please fill out all boxes!", Toast.LENGTH_SHORT).show();
                            // TODO: Dialog shouldn't close here
                        }
                    }
                });

        return builder.create();
    }
}
