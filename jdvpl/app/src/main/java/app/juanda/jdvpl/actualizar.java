package app.juanda.jdvpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class actualizar extends AppCompatActivity {
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    ImageView userphoto;
     static int PReqCodea = 5880;
     static int REQUESCODEa = 5880;
    Uri imagenescogidauria =null;
    EditText editTextfina;
    private ProgressBar loadingprogres;
    Button btnactuali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_actualizar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.user));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#051391")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userphoto = findViewById(R.id.curren_img);

        editTextfina = findViewById(R.id.usernombre);
        editTextfina.setText(currentUser.getDisplayName());

        final TextView mail=findViewById(R.id.usergmail);
        mail.setText((currentUser.getEmail()));


         btnactuali = findViewById(R.id.actualizar);

        Glide.with(this).load(currentUser.getPhotoUrl()).into(userphoto);
        loadingprogres = findViewById(R.id.progreee);

        userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // here when image clicked we need to open the gallery
                    // before we open the gallery we need to check if our app have the access to user files
                    // we did this before in register activity I'm just going to copy the code to save time ...

                    checkAndRequestForPermission();


                }
        });

        btnactuali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nombre1 = editTextfina.getText().toString();


                if (!editTextfina.getText().toString().equals(currentUser.getDisplayName())||(imagenescogidauria!=null)) {
                    if (imagenescogidauria == null) {
                        btnactuali.setVisibility(View.INVISIBLE);
                        loadingprogres.setVisibility(View.VISIBLE);
                        Toast.makeText(actualizar.this, "Espera un momento", Toast.LENGTH_SHORT).show();
                        updateUserInfo(nombre1, mAuth.getCurrentUser());
                    } else {

                        btnactuali.setVisibility(View.INVISIBLE);
                        loadingprogres.setVisibility(View.VISIBLE);
                        Toast.makeText(actualizar.this, "Espera un momento", Toast.LENGTH_SHORT).show();
                        updateUserInfo(nombre1, imagenescogidauria, mAuth.getCurrentUser());
                    }

                }else{
                    Toast.makeText(actualizar.this, "No puedes ya que son iguales", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void updateUserInfo(String nombre1, FirebaseUser currentUser) {



            UserProfileChangeRequest profileUpdatekl=new UserProfileChangeRequest.Builder().setDisplayName(nombre1).build();

            currentUser.updateProfile(profileUpdatekl).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(actualizar.this, "Nombre Actualizado", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent sfsd = new Intent(actualizar.this, login.class);
                        startActivity(sfsd);
                        finish();

                    }
                }
            });
        }

    private void updateUserInfo(final String nombre1, Uri imagenescogidauri, final FirebaseUser currentUser1) {

        StorageReference mStorage= FirebaseStorage.getInstance().getReference().child("usuario");
        final StorageReference imagefilepath=mStorage.child(imagenescogidauri.getLastPathSegment());
        imagefilepath.putFile(imagenescogidauri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //subida de imagen exitosa

                imagefilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profileUpdatek=new UserProfileChangeRequest.Builder().setDisplayName(nombre1).setPhotoUri(uri).build();

                        currentUser1.updateProfile(profileUpdatek).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(actualizar.this, "Nombre y Foto actualizada", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    Intent sfsd = new Intent(actualizar.this, login.class);
                                    startActivity(sfsd);
                                    finish();

                                }
                            }
                        });
                    }
                });
            }
        });
    }

    //metodo para ocultar el boton
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eliminar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.item1) {
            AlertDialog.Builder builder=new AlertDialog.Builder(actualizar.this);
            builder.setTitle("¿Deseas eliminar cuenta?");
            builder.setMessage("Si eliminas esta cuenta deberás registrarte de nuevo");
            builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(actualizar.this,"Has cerrado sesion",Toast.LENGTH_SHORT).show();
                                Toast.makeText(actualizar.this,"Cuenta eliminada",Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent login=new Intent(getApplicationContext(), login.class);
                                startActivity(login);
                                finish();
                            }else {
                                Toast.makeText(actualizar.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(actualizar.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(actualizar.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(actualizar.this,"Por favor acepta los terminos",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(actualizar.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCodea);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODEa);
    }

    public void camara(View view) {
        if (ContextCompat.checkSelfPermission(actualizar.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(actualizar.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actualizar.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);


        }else{

            ContentValues values=new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,"imagen");
            values.put(MediaStore.Images.Media.DESCRIPTION,"de la camara");
            imagenescogidauria=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenescogidauria);
            startActivityForResult(intent, 1001);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==REQUESCODEa&&data!=null){
            imagenescogidauria=data.getData();
            userphoto.setImageURI(imagenescogidauria);

        }
        if (resultCode == RESULT_OK){
            userphoto.setImageURI(imagenescogidauria);
        }

    }

}