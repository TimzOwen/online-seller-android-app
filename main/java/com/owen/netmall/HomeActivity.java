package com.owen.netmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutbtn = (Button)findViewById(R.id.btnLogout);

        logoutbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });
    }
}
