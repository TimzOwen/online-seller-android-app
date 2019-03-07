package com.owen.netmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity
{
    private ImageView  tShirts, sportsTshirt, femaleDresses, sweathers;
    private ImageView glasses, hatCaps, walletBagsPurses, shoes;
    private ImageView headPhoesHandFree, Laptops, watches, mobilePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        findIds();

        tShirts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Tshirts");
                startActivity(intent);
            }
        });
        sportsTshirt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Sports Tshirt");
                startActivity(intent);
            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Sweaters");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Glasses");
                startActivity(intent);
            }
        });
        hatCaps.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Hats Caps");
                startActivity(intent);
            }
        });

        walletBagsPurses.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Wallet Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Shoes");
                startActivity(intent);
            }
        });
        headPhoesHandFree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "HeadPhones Earphones ");
                startActivity(intent);
            }
        });
        Laptops.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Watches");
                startActivity(intent);
            }
        });
        mobilePhones.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category" , "Mobile Phones");
                startActivity(intent);
            }
        });
    }

    public void findIds()
    {
        tShirts = (ImageView)findViewById(R.id.iv_tshirt);
        sportsTshirt = (ImageView)findViewById(R.id.iv_sports_shirt);
        femaleDresses = (ImageView)findViewById(R.id.iv_female_dress);
        sweathers = (ImageView)findViewById(R.id.iv_sweathers);

        glasses = (ImageView)findViewById(R.id.iv_glasses);
        hatCaps = (ImageView)findViewById(R.id.iv_hats_caps);
        walletBagsPurses = (ImageView)findViewById(R.id.iv_purses);
        shoes = (ImageView)findViewById(R.id.iv_shoes);

        headPhoesHandFree = (ImageView)findViewById(R.id.iv_headphones);
        Laptops = (ImageView)findViewById(R.id.iv_laptops);
        watches = (ImageView)findViewById(R.id.iv_watches);
        mobilePhones = (ImageView)findViewById(R.id.iv_mobile_phones);

    }
}
