package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String[] items = {"Customer","Independent Help","Agency","Admin"};

    AutoCompleteTextView autoCompleteTxt;

    ArrayAdapter<String> adapterItems;

    TextInputEditText etDate,etName,etEmail,etPassword,etAddress1,etAddress2,etState,etZip;

    MaterialButton registerButton;

    FirebaseAuth fAuth;

    TextInputLayout txtInpDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        autoCompleteTxt = findViewById(R.id.auto_complete_text);
        etDate = findViewById(R.id.et_date);
        registerButton = findViewById(R.id.sign_up_btn_submit);
        etName = findViewById(R.id.sign_up_et_name);
        etEmail = findViewById(R.id.sign_up_et_email);
        etPassword = findViewById(R.id.sign_up_et_password);
        etAddress1 = findViewById(R.id.sign_up_et_address1);
        etAddress2 = findViewById(R.id.sign_up_et_address2);
        etState = findViewById(R.id.sign_up_et_state);
        etZip = findViewById(R.id.sign_up_et_zip);

        txtInpDate = findViewById(R.id.text_input_layout_date);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            //Send to Main Page

            finish();
        }

        etDate.setShowSoftInputOnFocus(true);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

            }
        });

        txtInpDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();


                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i("reg","registration successful");
                        }
                        else{
                            Log.i("reg","registration failed");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        etDate.setText(currentDateString);
    }

}