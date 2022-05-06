package com.example.szakdolgozat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProductAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private RadioGroup mradiogroup;
    private RadioButton maldi, mlidl, mtesco, mother;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private TextView mregist;
    private EditText metName, metPrice, metLocation,metShortdescript, metLongdescript, metContact;
    private ImageView mImageView;
    private ImageView mbtnChoose;
    private int i = 0;
    private Uri mImageUrl;
    private Product product;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        mregist = findViewById(R.id.register);
        metName = findViewById(R.id.etName);
        metLocation = findViewById(R.id.etLocation);
        metPrice = findViewById(R.id.etPrice);
        metShortdescript = findViewById(R.id.etShortdescript);
        metLongdescript = findViewById(R.id.etLongdescript);
        metContact = findViewById(R.id.etContact);
        mImageView = findViewById(R.id.image_view);
        mbtnChoose = findViewById(R.id.btnChoose);
        mradiogroup = findViewById(R.id.radio_group);
        maldi = findViewById(R.id.aldi);
        mlidl = findViewById(R.id.lidl);
        mtesco = findViewById(R.id.tesco);
        mother = findViewById(R.id.other);




        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

    }




    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            mImageUrl = data.getData();
            Picasso.with(this).load(mImageUrl).into(mImageView);
        }

    }

    public void add(View view) {
        String name = metName.getText().toString().trim();
        String contact = metContact.getText().toString().trim();
        if (mImageUrl != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUrl));

            if (name.equals("")){
                Toast.makeText(ProductAddActivity.this, "Empty name", Toast.LENGTH_LONG).show();
                return;
            }
            if (contact.equals("")){
                Toast.makeText(ProductAddActivity.this, "Empty user contact", Toast.LENGTH_LONG).show();
                return;
            }

            mUploadTask = fileReference.putFile(mImageUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String r_aldi = maldi.getText().toString();
                            String r_lidl = mlidl.getText().toString();
                            String r_tesco = mtesco.getText().toString();
                            String r_other = mother.getText().toString();
                            String store;

                            if (maldi.isChecked()){
                                store = r_aldi;
                            }
                            else if (mlidl.isChecked()){
                                store = r_lidl;
                            }
                            else if (mtesco.isChecked()){
                                store = r_tesco;
                            }
                            else {
                                store = r_other;
                            }


                            Toast.makeText(ProductAddActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Product product = new Product(
                                    name,
                                    metLocation.getText().toString().trim(),
                                    taskSnapshot.getStorage().getDownloadUrl().toString(),
                                    metPrice.getText().toString().trim(),
                                    metShortdescript.getText().toString().trim(),
                                    metLongdescript.getText().toString().trim(),
                                    store,
                                    contact);



                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(product);

                            openShop();

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProductAddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openShop() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }

    public void choose(View view) {
        openFileChooser();
    }
}