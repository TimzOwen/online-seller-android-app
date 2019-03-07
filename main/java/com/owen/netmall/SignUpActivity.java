package com.owen.netmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText etUserName, etUserPhone, etuserPasswd;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getIds();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        progressDialog = new ProgressDialog(this);

    }

    /**
     * method to create user account
     */
    public void createAccount()
    {
        String uName = etUserName.getText().toString();
        String uPhone = etUserPhone.getText().toString();
        String uPassword = etuserPasswd.getText().toString();

        if (TextUtils.isEmpty(uName))
        {
            Toast.makeText(SignUpActivity.this, "Please enter your name",Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(uPhone))
        {
            Toast.makeText(SignUpActivity.this, "Please enter your Phone Number",Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(uPassword))
        {
            Toast.makeText(SignUpActivity.this, "Please enter your password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setTitle("Welcome To Netmall Business");
            progressDialog.setMessage("Please Wait as we register you--");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validatePhoneNumber(uName,uPhone,uPassword);
        }

    }

    /**
     * method to validate the user phone ,name and passord
     */
    public void validatePhoneNumber(final String name, final String phone, final String password)
    {
      final DatabaseReference RootRef;

      RootRef = FirebaseDatabase.getInstance().getReference();

      RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              if(!(dataSnapshot.child("Users").child(phone).exists()))
              {
                  HashMap<String, Object> userDataMap = new HashMap<>();

                  userDataMap.put("Phone", phone);
                  userDataMap.put("Name", name);
                  userDataMap.put("Password", password);

                  RootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(SignUpActivity.this, "Congratulations!! acount created",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        //send user to login page
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignUpActivity.this,"Network Err!! try again",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
              }
              else
              {
                  Toast.makeText(SignUpActivity.this,"User Already " + phone + " Exist",Toast.LENGTH_SHORT).show();
                  progressDialog.dismiss();
                  Toast.makeText(SignUpActivity.this,"Please try again usig another Phone umber",Toast.LENGTH_SHORT).show();

                  Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                  startActivity(intent);
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }
    /**
     * function to get the ids
     */
    public void getIds()
    {
        btnSignUp = (Button)findViewById(R.id.btn_sign_up_customer);
        etUserName = (EditText)findViewById(R.id.et_register_user_name);
        etUserPhone = (EditText)findViewById(R.id.et_register_phonee_number);
        etuserPasswd = (EditText)findViewById(R.id.et_register_password);
    }
}
