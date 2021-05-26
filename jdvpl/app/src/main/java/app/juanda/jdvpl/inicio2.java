package app.juanda.jdvpl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class inicio2 extends AppCompatActivity
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
    LinearLayout html,btncss3,btnboots,btnmysql,btnphp;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_inicio2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.Inicio2));

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        Fade fade= new Fade();
        View decor=getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        final ImageView imageView=findViewById(R.id.image_a1);
        final ImageView imageView2=findViewById(R.id.image_a2);
        final ImageView imageVie3=findViewById(R.id.image_a3);
        final ImageView imageVie4=findViewById(R.id.image_a4);
        final ImageView imageView5=findViewById(R.id.image_a5);

        html=findViewById(R.id.btnhtml);
        html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent htm=new Intent(inicio2.this,html5scrollacti.class);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(inicio2.this,imageView, ViewCompat.getTransitionName(imageView));
                startActivity(htm, options.toBundle());

            }
        });
        btncss3=findViewById(R.id.btncss3);
        btncss3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cs=new Intent(inicio2.this,ScrollingCss3.class);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(inicio2.this,imageView2, ViewCompat.getTransitionName(imageView2));
                startActivity(cs, options.toBundle());
            }
        });
        btnboots=findViewById(R.id.btnboots1);
        btnboots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bo=new Intent(inicio2.this,ScrollingBootstrap.class);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(inicio2.this,imageVie3, ViewCompat.getTransitionName(imageVie3));
                startActivity(bo, options.toBundle());


            }
        });
        btnmysql=findViewById(R.id.btnmysql);
        btnmysql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my=new Intent(inicio2.this,ScrollingMysql.class);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(inicio2.this,imageView5, ViewCompat.getTransitionName(imageView5));
                startActivity(my, options.toBundle());
            }
        });
        btnphp=findViewById(R.id.btnphp);
        btnphp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ph=new Intent(inicio2.this,ScrollingPHP.class);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(inicio2.this,imageVie4, ViewCompat.getTransitionName(imageVie4));
                startActivity(ph, options.toBundle());
            }
        });



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
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.item1) {
            AlertDialog.Builder builder=new AlertDialog.Builder(inicio2.this);
            builder.setTitle("¿Deseas eliminar cuenta?");
            builder.setMessage("Si eliminas esta cuenta deberás registrarte de nuevo");
            builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(inicio2.this,"Has cerrado sesion",Toast.LENGTH_SHORT).show();
                                Toast.makeText(inicio2.this,"Cuenta eliminada",Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent login=new Intent(getApplicationContext(), login.class);
                                startActivity(login);
                                finish();
                            }else {
                                Toast.makeText(inicio2.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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

        }
        else if (id == R.id.nav_dudas) {
            Intent htm=new Intent(this,pop.class);
            startActivity(htm);
            finish();
            overridePendingTransition(R.anim.despa,R.anim.despaou);
        }else if (id == R.id.nav_videos) {
            Intent htm=new Intent(this,videos.class);
            startActivity(htm);
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

        }

        else if (id == R.id.nav_salir) {
            Toast.makeText(this,"Haz cerrado sesion",Toast.LENGTH_SHORT).show();
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
                Intent intent=new Intent(inicio2.this,actualizar.class);
                startActivity(intent);
            }
        });
    }
}
