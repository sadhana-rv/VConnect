package com.example.sadhanaravoori.firebasevconnect;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class OrganizationDetails extends AppCompatActivity {

    EditText admin_name, organization_name, role_admin,phone,email,description;

    private DatabaseReference fire= FirebaseDatabase.getInstance().getReference().child("Administrator");

    public static OrganizationDetails od;

    FirebaseStorage storage;
    StorageReference storageReference;
    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;
    private StorageTask mUploadTask;
    private Uri filePath;
    private String url;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_details);

        od=this;

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mButtonChooseImage = (Button)findViewById(R.id.button_choose_image);
        mButtonUpload =(Button) findViewById(R.id.button_upload);
        mImageView = (ImageView)findViewById(R.id.image_view);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Administrator");
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar2);
        imageView = (ImageView) findViewById(R.id.image_view);

        admin_name=(EditText)findViewById(R.id.nameofadmin);
        organization_name=(EditText)findViewById(R.id.nameoforganization);
        role_admin=(EditText)findViewById(R.id.roleofadmin);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        description=(EditText)findViewById(R.id.description);

        submit=(Button)findViewById(R.id.submit);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(OrganizationDetails.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> datamap= new HashMap<String ,String>();

                datamap.put("Name Of Organization",organization_name.getText().toString().trim());
                datamap.put("Phone", phone.getText().toString().trim());
                datamap.put("Email", email.getText().toString().trim().replace('.',' '));
                datamap.put("Description",description.getText().toString().trim());
                datamap.put("Name Of Admin",admin_name.getText().toString().trim());
                datamap.put("Role Of Admin",role_admin.getText().toString().trim());
                datamap.put("Image url",url);

                Bundle bundle=getIntent().getExtras();
                String head=bundle.getString("email").replaceAll("\\."," ");

                //fire.child(head).setValue(datamap);

                Bundle sendEmail=new Bundle();
                sendEmail.putString("Email",head);
                sendEmail.putString("Name Of Organization",organization_name.getText().toString().trim());
                sendEmail.putString("Phone", phone.getText().toString().trim());
                sendEmail.putString("Email Of Org", email.getText().toString().trim().replace('.',' '));
                sendEmail.putString("Description",description.getText().toString().trim());
                sendEmail.putString("Name Of Admin",admin_name.getText().toString().trim());
                sendEmail.putString("Role Of Admin",role_admin.getText().toString().trim());
                sendEmail.putString("Image Url", url);

                Intent intent=new Intent(getApplicationContext(),MapsAdmin.class);
                intent.putExtras(sendEmail);
                startActivity(intent);

            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Glide.with(this).load(mImageUri).into(imageView);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(OrganizationDetails.this, "Upload successful", Toast.LENGTH_LONG).show();
                            url=taskSnapshot.getDownloadUrl().toString();
                            //Toast.makeText(OrganizationDetails.this, url, Toast.LENGTH_SHORT).show();
                            Log.d("url",url);
                            Bundle b=new Bundle();
                            b.putString("Url",url);

                            //Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                            //String uploadId = mDatabaseRef.push().getKey();
                            //mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(OrganizationDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}