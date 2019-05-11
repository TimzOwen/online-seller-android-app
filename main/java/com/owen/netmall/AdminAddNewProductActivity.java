package com.owen.netmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity
{
    private String categoryName, Pname, Description, Price, saveCurrentDate, saveCurrentTime;
    private Button addNewProduct;
    private Uri ImageUri;
    private ImageView inputProductImage;
    private final static int GalleryPick = 1;
    private String productRandomKey, downloadImageUrl;
    private StorageReference productStorageRefe;
    private DatabaseReference productRef;
    private ProgressDialog loginProgress;
    private EditText inputName, inputDescription, inputrice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        findIds();

        loginProgress = new ProgressDialog(this);

       categoryName = getIntent().getExtras().get("category").toString();
       productStorageRefe = FirebaseStorage.getInstance().getReference().child("Product Images");
       productRef = FirebaseDatabase.getInstance().getReference().child("Products");

       inputProductImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               openGallery();               
           }
       });

       addNewProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               validateProductData();
           }
       });
    }


//Use implecit intend here please.
    public void openGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    public void findIds()// Remove this finction and use the USGC web APi to collect data .Dont also cast the TextViews
    {
        addNewProduct  = (Button)findViewById(R.id.btn_add_new_product);
        inputName = (EditText)findViewById(R.id.product_name);
        inputDescription = (EditText)findViewById(R.id.product_description);
        inputrice = (EditText)findViewById(R.id.product_price);
        inputProductImage = (ImageView)findViewById(R.id.select_product_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && requestCode==RESULT_OK && data!=null)
        {
           ImageUri = data.getData();
           inputProductImage.setImageURI(ImageUri);
        }
    }

    private void validateProductData()
    {
        Price = inputrice.getText().toString();
        Pname = inputName.getText().toString();
        Description = inputDescription.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Product Image Is Mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this,"Description of product is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this,"Product Price Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this,"Enter Product Name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            storeProductInformation();
        }
    }

    public void storeProductInformation()
    {


        loginProgress.setTitle("Adding New Product");
        loginProgress.setMessage("Product being added...");
        loginProgress.setCanceledOnTouchOutside(false);
        loginProgress.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd , yyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productStorageRefe.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message ,Toast.LENGTH_SHORT).show();

                loginProgress.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewProductActivity.this, "Product uploaded Successfully" ,Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                       if (!task.isSuccessful())
                       {
                           throw task.getException();
                       }
                        downloadImageUrl = filePath.getDownloadUrl().toString();

                             return   filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {

                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewProductActivity.this,"Got image url sucess..",Toast.LENGTH_SHORT).show();

                            saveProductInfoTODatabase();
                        }
                    }
                });
            }
        });
    }

    public void saveProductInfoTODatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("price", Price);
        productMap.put("name", Pname);
//checks from the database firebase child.href update using the Picasso API
        productRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                       if (task.isSuccessful())
                       {
                           Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);

                           startActivity(intent);

                           loginProgress.dismiss();
                           Toast.makeText(AdminAddNewProductActivity.this,"Product added sucessfully",Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           loginProgress.dismiss();
                           String message = task.getException().toString();
//change this to use the Picasso API also
                           Toast.makeText(AdminAddNewProductActivity.this,"Error: " + message ,Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }
}
