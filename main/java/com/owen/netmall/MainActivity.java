package com.owen.netmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.owen.netmall.Model.Users;
import com.owen.netmall.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{

    private Button joinNow, haveAccount;

    private ProgressDialog loginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        loginProgress = new ProgressDialog(this);

        joinNow = (Button) findViewById(R.id.btn_join_now);

        haveAccount = (Button)findViewById(R.id.btn_login);

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });
        joinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        String userPhoneey = Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (userPhoneey != "" && userPasswordKey != "")
        {
            if (!TextUtils.isEmpty(userPhoneey) && !TextUtils.isEmpty(userPasswordKey))
            {
                AllowAcess(userPhoneey, userPasswordKey);


                loginProgress.setTitle("WelcomeBack To Business");
                loginProgress.setMessage("Please Wait....");
                loginProgress.setCanceledOnTouchOutside(false);
                loginProgress.show();
            }

        }
    }

    public  void AllowAcess(final String phone,  final String password)
    {
        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this,"Login Successful --",Toast.LENGTH_SHORT).show();

                            loginProgress.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                            startActivity(intent);
                        }
                        else
                        {
                            loginProgress.dismiss();

                            Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please Creaat Acount, User not Registered",Toast.LENGTH_SHORT).show();

                    loginProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
