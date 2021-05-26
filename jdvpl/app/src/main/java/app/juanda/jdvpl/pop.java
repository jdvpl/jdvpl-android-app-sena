package app.juanda.jdvpl;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class pop extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    public void onBackPressed() {

        AlertDialog.Builder prueba=new AlertDialog.Builder(this);
        prueba.setMessage("Desea Salir de la aplicacion?");
        prueba.setTitle("JD Virtual Programming Lab");

        prueba.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        prueba.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog=prueba.create();
        dialog.show();
    }
    final static int PReqCode = 2 ;
    final static int REQUESCODE = 2 ;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    Dialog popAddPost;
    ImageView popupUserImage,popupPostImage,popupAddBtn;
    EditText popupTitle,popupDescription,poptelefono,cr,crnu,crdire,crciu,depar,pais;
    ProgressBar popupClickProgress;
    Uri pickedImgUri =null;
    RecyclerView postRecyclerView;
    PostAdapter postAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SearchView searchView;
    List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pop);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.retro));


        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setStackFromEnd(true);
        lin.setReverseLayout(true);
        postRecyclerView  = findViewById(R.id.postRV);
        postRecyclerView.setLayoutManager(lin);
        postRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popAddPost.show();
            }
        });
        mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        iniPopup();
        setupPopupImageClick();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);

        UpdateNavHeader();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Get List Posts from the database

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

                    Post post = postsnap.getValue(Post.class);
                    postList.add(post) ;
                }

                postAdapter = new PostAdapter(getApplicationContext(),postList);
                postRecyclerView.setAdapter(postAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void setupPopupImageClick() {

        popupPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here when image clicked we need to open the gallery
                // before we open the gallery we need to check if our app have the access to user files
                // we did this before in register activity I'm just going to copy the code to save time ...

                checkAndRequestForPermission();


            }
        });


    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(pop.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(pop.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(pop.this,"Por favor acepta los permisos",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(pop.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    public void camara(View view) {
        if (ContextCompat.checkSelfPermission(pop.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(pop.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(pop.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);


        }else{

            ContentValues values=new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,"imagen");
            values.put(MediaStore.Images.Media.DESCRIPTION,"de la camara");
            pickedImgUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pickedImgUri);
            startActivityForResult(intent, 1001);
        }
    }
    // when user picked an image ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData();
            popupPostImage.setImageURI(pickedImgUri);

        }
        if (resultCode == RESULT_OK){
            popupPostImage.setImageURI(pickedImgUri);
        }


    }

    private void iniPopup() {


        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.popup_add_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

        // ini popup widgets
        popupUserImage = popAddPost.findViewById(R.id.popup_user_image);
        popupPostImage = popAddPost.findViewById(R.id.popup_img);
        popupTitle = popAddPost.findViewById(R.id.popup_title);
        popupDescription = popAddPost.findViewById(R.id.popup_description);

        //mapa

        cr=popAddPost.findViewById(R.id.popup_cr);
        crnu=popAddPost.findViewById(R.id.popup_numero);
        crdire=popAddPost.findViewById(R.id.popup_direcc);
        crciu=popAddPost.findViewById(R.id.popup_ciudad);
        depar=popAddPost.findViewById(R.id.popup_depart);
        pais=popAddPost.findViewById(R.id.popup_pais);
        //termina mapa
        popupAddBtn = popAddPost.findViewById(R.id.popup_add);
        popupClickProgress = popAddPost.findViewById(R.id.popup_progressBar);
        poptelefono=popAddPost.findViewById(R.id.popup_telefono);
        searchView=findViewById(R.id.search);


        // load Current user profile photo

        Glide.with(pop.this).load(currentUser.getPhotoUrl()).into(popupUserImage);


        // Add post click Listener

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);

                // we need to test all input fields (Title and description ) and post image

                if (!popupTitle.getText().toString().isEmpty()
                        && !popupDescription.getText().toString().isEmpty()
                        //mapas
                        //maps termina
                        && pickedImgUri != null) {

                    //everything is okey no empty or null value
                    // TODO Create Post Object and add it to firebase database
                    // first we need to upload post Image
                    // access firebase storage
                    if ((!cr.getText().toString().equals("Cr") && !cr.getText().toString().equals("Cl")&& !cr.getText().toString().equals("Dg") &&!cr.getText().toString().equals("Ac")&&!cr.getText().toString().equals(""))) {
                        popupAddBtn.setVisibility(View.VISIBLE);
                        popupClickProgress.setVisibility(View.INVISIBLE);
                        cr.setText("");
                        crnu.setText("");
                        crdire.setText("");
                        crciu.setText("");
                        depar.setText("");
                        pais.setText("");
                        Toast.makeText(pop.this,"En la direccion debe ser Cl, Cr, Dg o Ac o has ingresado la direccion erronea",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "espera un momento", Toast.LENGTH_LONG).show();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("blog_images");
                        final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageDownlaodLink = uri.toString();
                                        // create post Object
                                        Post post = new Post(popupTitle.getText().toString(),
                                                popupDescription.getText().toString(),
                                                poptelefono.getText().toString(),
                                                cr.getText().toString(),
                                                crnu.getText().toString(),
                                                crdire.getText().toString(),
                                                crciu.getText().toString(),
                                                depar.getText().toString(),
                                                pais.getText().toString(),
                                                imageDownlaodLink,
                                                currentUser.getUid(),
                                                currentUser.getPhotoUrl().toString());

                                        // Add post to firebase database

                                        addPost(post);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // something goes wrong uploading picture

                                        showMessage(e.getMessage());
                                        popupClickProgress.setVisibility(View.INVISIBLE);
                                        popupAddBtn.setVisibility(View.VISIBLE);

                                    }
                                });

                            }
                        });

                    }
                }


                    //else de verifacion direccion
                else{
                        showMessage("Debes completar los 3 primeros campos");
                        popupAddBtn.setVisibility(View.VISIBLE);
                        popupClickProgress.setVisibility(View.INVISIBLE);


                    }

                }

        });


    }

    private void addPost(Post post) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts").push();

        // get post unique ID and upadte post key
        String key = myRef.getKey();
        post.setPostKey(key);


        // add post data to firebase database

        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("La imagen ha sido agregada correctamente");
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupAddBtn.setVisibility(View.VISIBLE);
                popAddPost.dismiss();

                popupPostImage.setImageURI(null);
                popupTitle.setText("");
                popupDescription.setText("");
                poptelefono.setText("");
                cr.setText("");
                crnu.setText("");
                crdire.setText("");
                crciu.setText("");
                depar.setText("");
                pais.setText("");



            }
        });
    }

    private void showMessage(String message) {

        Toast.makeText(pop.this,message,Toast.LENGTH_LONG).show();
    }




    //metodo para ocultar el boton
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        final MenuItem myiconmenuitem=menu.findItem(R.id.search);

            searchView=(SearchView)myiconmenuitem.getActionView();
        searchView.findViewById(androidx.appcompat.R.id.search_edit_frame);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                myiconmenuitem.collapseActionView();
                return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 final List<Post>filtermodelist=filter(postList,newText);
                    postAdapter.setfilter(filtermodelist);
                    return true;
             }
         });
        return true;
    }
    private void changedsearchviewtexcolo(View view){
        if (view !=null){
            if (view instanceof TextView){
                ((TextView) view).setTextColor(Color.YELLOW);
                return;
            }else if(view instanceof ViewGroup){
                ViewGroup viewGroup=(ViewGroup)view;
                for (int i=0; i<viewGroup.getChildCount(); i++){
                    changedsearchviewtexcolo(viewGroup.getChildAt(i));
                }
            }
        }

    }
    private List<Post>filter(List<Post> pl,String query){
        query=query.toLowerCase();
        final  List<Post> filteredModeList=new ArrayList<>();
        for (Post model:pl){
            final String text=model.getTitle().toLowerCase();
            if (text.startsWith(query)){
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio2) {
            getSupportActionBar().setTitle("Selecciona tu curso");
            Intent htm=new Intent(this,inicio2.class);
            startActivity(htm);
            finish();
            overridePendingTransition(R.anim.despa,R.anim.despaou);

        }else if (id == R.id.nav_videos) {
            Intent htm=new Intent(this,videos.class);
            startActivity(htm);
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        }else if (id == R.id.nav_dudas) {

            getSupportActionBar().setTitle("Selecciona tu curso");
            Intent htm=new Intent(this,pop.class);
            startActivity(htm);
            finish();
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        }else if (id == R.id.nav_divietertete) {
            Intent quiz=new Intent(this,quizactvivity.class);
            startActivity(quiz);
            overridePendingTransition(R.anim.despa,R.anim.despaou);

        }else if (id == R.id.nav_usuario) {
            Intent pe=new Intent(this,actualizar.class);
            startActivity(pe);
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        } else if (id == R.id.nav_material) {
            Intent mate=new Intent(this,material.class);
            startActivity(mate);
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        }
        else if (id == R.id.nav_contacto) {
            Intent con=new Intent(this,contacto.class);
            startActivity(con);
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        }
        else if (id == R.id.nav_manual) {
            Intent pdf=new Intent(this,manual_usuario.class);
            startActivity(pdf);
            overridePendingTransition(R.anim.despa,R.anim.despaou);

        }else if (id == R.id.nav_salir) {
            Toast.makeText(this,"Has cerrado sesion",Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent login=new Intent(getApplicationContext(), login.class);
            startActivity(login);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void UpdateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        TextView navUsernombre=headerView.findViewById(R.id.nav_username);
        TextView navUsermail=headerView.findViewById(R.id.nav_useremail);
        ImageView navUserphoto=headerView.findViewById(R.id.nav_user_foto);

        navUsermail.setText(currentUser.getEmail());
        navUsernombre.setText(currentUser.getDisplayName());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserphoto);

        navUserphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(pop.this,actualizar.class);
                startActivity(intent);
            }
        });
    }



}
