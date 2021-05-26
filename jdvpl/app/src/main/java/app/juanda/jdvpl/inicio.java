package app.juanda.jdvpl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


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

    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.Inicio));

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();




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

    //metodo para ocultar el boton
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eliminar, menu);
        getMenuInflater().inflate(R.menu.actua,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.item1) {
            AlertDialog.Builder builder=new AlertDialog.Builder(inicio.this);
            builder.setTitle("¿Deseas eliminar cuenta?");
            builder.setMessage("Si eliminas esta cuenta deberás registrarte de nuevo");
            builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(inicio.this,"Has cerrado sesion",Toast.LENGTH_SHORT).show();
                                Toast.makeText(inicio.this,"Cuenta eliminada",Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent login=new Intent(getApplicationContext(), login.class);
                                startActivity(login);
                                finish();
                            }else {
                                Toast.makeText(inicio.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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

        }else if (id==R.id.actualddd){
            Intent actua=new Intent(this,actualizar.class);
            startActivity(actua);
        }
        return super.onOptionsItemSelected(item);
        }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio2) {
           Intent htm=new Intent(this,inicio2.class);
           startActivity(htm);
           finish();
           overridePendingTransition(R.anim.despa,R.anim.despaou);

        }else if (id == R.id.nav_videos) {
            Intent htm=new Intent(this,videos.class);
            startActivity(htm);
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        }
        else if (id == R.id.nav_dudas) {
            Intent htm=new Intent(this,pop.class);
            startActivity(htm);
            finish();
            overridePendingTransition(R.anim.despa,R.anim.despaou);

        }else if (id == R.id.nav_divietertete) {
            Intent quiz=new Intent(this,quizactvivity.class);
            startActivity(quiz);
            overridePendingTransition(R.anim.despa,R.anim.despaou);

        } else if (id == R.id.nav_material) {
            Intent mate=new Intent(this,material.class);
            startActivity(mate);
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        }else if (id == R.id.nav_usuario) {
            Intent pe=new Intent(this,actualizar.class);
            startActivity(pe);
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
                Intent intent=new Intent(inicio.this,actualizar.class);
                startActivity(intent);
            }
        });
    }



}
