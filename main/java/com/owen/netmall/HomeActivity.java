package com.owen.netmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button logoutbtn;
    private Button Loginbtn;
    private TextView tvAlreadySignedUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutbtn = (Button)findViewById(R.id.btnLogout);
        Loginbtn = (Button) findViewById(R.id.btnLogin);

        logoutbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
                //used to make sure a user once loged in is remembeered
                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });
    }
    public void onClick(View v)
    {
     if(v==btnLogin)
     {
         //perform actions by user
     }
        if(v==logoutbtn)
        {
            //perform the button logout instructions
        }
    }

}
