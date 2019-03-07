package com.owen.netmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.owen.netmall.Model.Users;
import com.owen.netmall.Prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserPhone, userPassword;
    private Button btnLogin;
    private ProgressDialog loginProgress;

    private TextView textViewadmin, textViewnotadmin;

    private String paraentDbName = "Users";


    private CheckBox checkBoxRemMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getIds();

        loginProgress = new ProgressDialog(this);

        Paper.init(this);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginUser();
            }
        });
        textViewadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnLogin.setText("Login Admin");
                textViewadmin.setVisibility(View.INVISIBLE);
                textViewnotadmin.setVisibility(View.VISIBLE);
                paraentDbName = "Admins";
            }
        });
        textViewnotadmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnLogin.setText("Login");
                textViewnotadmin.setVisibility(View.INVISIBLE);
                textViewadmin.setVisibility(View.VISIBLE);
                paraentDbName = "USers";
            }
        });

    }

    /**
     * method to check login user credentials
     */
    public void loginUser()
    {
        String uPhone = etUserPhone.getText().toString();
        String uPassword = userPassword.getText().toString();

        if (TextUtils.isEmpty(uPhone))
        {
            Toast.makeText(LoginActivity.this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(uPassword))
        {
            Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
    else
         {
             loginProgress.setTitle("Welcome To Netmall Business");
             loginProgress.setMessage("Logging you In...");
             loginProgress.setCanceledOnTouchOutside(false);
             loginProgress.show();

             AllowAceessToAccount(uPhone,uPassword);
         }
    }

    /**
     * give acess methods to the account
     */
    public void AllowAceessToAccount(final String phone, final String password)
    {

        if (checkBoxRemMe.isChecked())
        {
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(paraentDbName).child(phone).exists())
                {
                    Users usersData = dataSnapshot.child(paraentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                           if (paraentDbName.equals("Admins"))
                           {
                               Toast.makeText(LoginActivity.this," Admin you areLogged In Successful --",Toast.LENGTH_SHORT).show();

                               loginProgress.dismiss();

                               Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);

                               startActivity(intent);
                           }
                           else if (paraentDbName.equals("Users"))
                           {
                               Toast.makeText(LoginActivity.this,"Login Successful --",Toast.LENGTH_SHORT).show();

                               loginProgress.dismiss();

                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                               startActivity(intent);
                           }
                        }
                        else
                        {
                            loginProgress.dismiss();

                            Toast.makeText(LoginActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please Creaat Acount, User not Registered",Toast.LENGTH_SHORT).show();

                    loginProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //method to find Ids
    public void getIds()
    {
        etUserPhone = (EditText)findViewById(R.id.et_phone_number);
        userPassword =(EditText)findViewById(R.id.et_password);
        btnLogin = (Button)findViewById(R.id.btn_login_customer);
        checkBoxRemMe = (CheckBox)findViewById(R.id.cb_rem_me);
        textViewadmin = (TextView)findViewById(R.id.tv_Admin);
        textViewnotadmin = (TextView)findViewById(R.id.tv_Not_Admin);
    }
}
