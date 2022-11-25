package com.moutamid.car_gps_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    Button login_btn;
    LinearLayout goto_signup;
    EditText emailTxt,passTxt;
    String email,password;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private ImageView settingsImag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login_btn);
        goto_signup = findViewById(R.id.goto_signup);
        emailTxt = findViewById(R.id.email);
        passTxt = findViewById(R.id.password);
        settingsImag = findViewById(R.id.server);
        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validInfo()){
                    dialog = new ProgressDialog(Login_Activity.this);
                    dialog.setMessage("Logging into your account....");
                    dialog.show();
                    login();
                }
            }
        });

        settingsImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showServerDialigBox();
            }
        });

        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this , Sign_Up_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void showServerDialigBox() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_server_alert_box, null);
        dialogBuilder.setView(dialogView);

        EditText editText = (EditText) dialogView.findViewById(R.id.serverUrl);
        TextView saveBtn = (TextView) dialogView.findViewById(R.id.save);
        AlertDialog alertDialog = dialogBuilder.create();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String server = editText.getText().toString();
                if (!TextUtils.isEmpty(server)){
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Login_Activity.this, "Enter server here!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }

    private void login() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(Login_Activity.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }else {
                    Toast.makeText(Login_Activity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }


    public boolean validInfo() {
        email = emailTxt.getText().toString();
        password = passTxt.getText().toString();

        if(email.isEmpty()){
            emailTxt.setText("Input Email");
            emailTxt.requestFocus();
            return false;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTxt.setError("Please input valid email!");
            emailTxt.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            passTxt.setText("Input Password");
            passTxt.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(Login_Activity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}