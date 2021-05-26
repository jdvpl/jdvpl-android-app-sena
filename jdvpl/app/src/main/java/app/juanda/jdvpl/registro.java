package app.juanda.jdvpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class registro extends AppCompatActivity {
    ImageView userPhoto;
    static  int PReqCode=1;
    static  int REQUESCODE=1;
    Uri imagenescogidauri;

    private EditText usernombre, userapellido,usercorreo, usercontra, usercontra2;
    private ProgressBar loadingprogres;
    private Button regBtn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registro);


        usernombre=(EditText)findViewById(R.id.regnom);
        userapellido=(EditText)findViewById(R.id.regape);
        usercorreo=(EditText)findViewById(R.id.regcor);
        usercontra=(EditText)findViewById(R.id.regcon);
        usercontra2=(EditText)findViewById(R.id.regconcon);
        loadingprogres=(ProgressBar) findViewById(R.id.progressBar);
        regBtn=findViewById(R.id.btnregistarreg);
        loadingprogres.setVisibility(View.INVISIBLE);

        mAuth=FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regBtn.setVisibility(View.INVISIBLE);
                loadingprogres.setVisibility(View.VISIBLE);

                 String nombre=usernombre.getText().toString();

                 String apellido=userapellido.getText().toString();
                 String correo=usercorreo.getText().toString();
                 String contrasena=usercontra.getText().toString();
                 String contrasena2=usercontra2.getText().toString();



                if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || contrasena2.isEmpty()){

                    //necesitamos verificar todos los campos que sean completados
                    showMessage("Por favor completa todos los campos");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingprogres.setVisibility(View.INVISIBLE);
                }else{

                    CreateUserAccount(nombre,apellido,correo,contrasena);

                }
            }
        });
        userPhoto=(ImageView)findViewById(R.id.imageViewuser);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();
                }else{
                    openGallery();
                }


            }
        });
    }

    private void CreateUserAccount(final String nombre, final String apellido, String correo, String contrasena) {
        if (imagenescogidauri==null) {
            regBtn.setVisibility(View.VISIBLE);
            loadingprogres.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Debes elegir la imagen de perfil",Toast.LENGTH_SHORT).show();

        }else{


        mAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    showMessage("cuenta creada");
                    //con este metodo podemos actuales
                    updateUserInfo(nombre, apellido, imagenescogidauri,mAuth.getCurrentUser());
                }
                else{
                    //la cuenta no fue creada
                    showMessage("la cuenta no pudo ser creada"+task.getException().getMessage());
                    regBtn.setVisibility(View.VISIBLE);
                    loadingprogres.setVisibility(View.INVISIBLE);
                }
            }
        });
        }
    }

    private void updateUserInfo(final String nombre, final String apellido, Uri imagenescogidauri, final FirebaseUser currentUser) {

           StorageReference mStorage= FirebaseStorage.getInstance().getReference().child("fotos_usuario");
           final StorageReference imagefilepath=mStorage.child(imagenescogidauri.getLastPathSegment());
           imagefilepath.putFile(imagenescogidauri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   //subida de imagen exitosa

                   imagefilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {

                           UserProfileChangeRequest profileUpdate=new UserProfileChangeRequest.Builder().setDisplayName(nombre).setDisplayName(apellido).setPhotoUri(uri).build();

                           currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {

                                   if (task.isSuccessful()){
                                       showMessage("registro completo");
                                       updateUI();
                                   }
                               }
                           });
                       }
                   });
               }
           });
       }


    private void updateUI() {
        Intent inicioactivitye=new Intent(getApplicationContext(),inicio.class);
        startActivity(inicioactivitye);
        finish();
    }

    //metodo simple para mostrar un toast
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    private void openGallery() {

        Intent galeria=new Intent(Intent.ACTION_GET_CONTENT);
        galeria.setType("image/*");
        startActivityForResult(galeria,REQUESCODE);
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(registro.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(registro.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(registro.this,"por favor acepta los permisos requiridos",Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(registro.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else openGallery();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==REQUESCODE && data !=null){
            imagenescogidauri=data.getData();
            userPhoto.setImageURI(imagenescogidauri);
        }
    }

    public void yatengo(View view) {
        finish();
    }
}

