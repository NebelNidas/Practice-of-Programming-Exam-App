package de.cicero_interactive.practiceofprogrammingexamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    String correct_username;
    String correct_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyDatabase myDatabase = new MyDatabase(this);
        correct_username = myDatabase.getUsername();
        correct_password = myDatabase.getPassword();
    }


    public void loginButtonClicked(View view) {
        Button btn = (Button) view;
        btn.setEnabled(false);
        btn.setText("Logging in...");

        // Get username
        EditText usernameBox = findViewById(R.id.usernameBox);
        String username = usernameBox.getText().toString();

        // Get password
        EditText passwordBox = findViewById(R.id.passwordBox);
        String password = passwordBox.getText().toString();

        // Call login func
        login(username, password);

        // Reset button
        btn.setEnabled(true);
        btn.setText("OK");

        // Reset password box
        passwordBox.setText("");
    }


    public void login(String username, String password) {
        // TODO: addition of a database, password hashing for security
        if (username.equals(correct_username) && password.equals(correct_password)) {
            // Show success toast
            showToast("Login successful!");

            // Launch new activity
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Username", username);
            startActivity(i);
        } else {
            // Show error toast
            showToast("Login failed!");
        }
    }


    public void showToast(String toast_text) {
        Toast login_toast = Toast.makeText(this, toast_text, Toast.LENGTH_SHORT);
        login_toast.show();
    }
}
