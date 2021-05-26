package app.juanda.jdvpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class login extends AppCompatActivity {

    @Override
    public void onBackPressed() {

        AlertDialog.Builder prueba = new AlertDialog.Builder(this);
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
        AlertDialog dialog = prueba.create();
        dialog.show();
    }


    private EditText userMail, userpassword;
    private Button btnLogin, a;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent inicioActivity;
    private ImageView log_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.login);
        a = findViewById(R.id.idioma);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });


        userMail = findViewById(R.id.logcor);
        userpassword = findViewById(R.id.logcon);
        btnLogin = findViewById(R.id.btnlog);
        loginProgress = findViewById(R.id.login_progresbar);
        log_photo = findViewById(R.id.log_photo);
        mAuth = FirebaseAuth.getInstance();
        inicioActivity = new Intent(this, inicio.class);

        log_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registraractividad = new Intent(getApplicationContext(), registro.class);
                startActivity(registraractividad);

            }
        });

        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);
                final String mail = userMail.getText().toString();
                final String contra = userpassword.getText().toString();
                if (mail.isEmpty() || contra.isEmpty()) {
                    showMessage("los campos deben ser completados");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                } else {
                    signIn(mail, contra);

                }
            }
        });

    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"Español", "English", "Français", "日本人"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(getResources().getString(R.string.idiom));
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("es");
                    recreate();
                }
                if (i == 1) {
                    setLocale("en");
                    recreate();
                }
                if (i == 2) {
                    setLocale("fr");
                    recreate();
                }
                if (i == 3) {
                    setLocale("ja");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mdialog = mBuilder.create();
        mdialog.show();

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }


    private void signIn(String mail, String contra) {
        mAuth.signInWithEmailAndPassword(mail, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    loginProgress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    UpdateUI();

                } else
                    showMessage(task.getException().getMessage());
                loginProgress.setVisibility(View.INVISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });
    }

    private void UpdateUI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(this, "Bienvenid@  " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        startActivity(inicioActivity);
        finish();
    }

    private void showMessage(String los_campos_deben_ser_completados) {
        Toast.makeText(getApplicationContext(), los_campos_deben_ser_completados, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            UpdateUI();
        }
    }

    public void sincon(View view) {
        Intent next = new Intent(this, registro.class);
        startActivity(next);
    }

    public void registro(View view) {
        Intent L = new Intent(this, registro.class);
        startActivity(L);
    }


    public void loginred(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Intent sdl = new Intent(this, loginredes.class);
            startActivity(sdl);
        } else {
            Toast.makeText(this, "debes estar conectado a internet", Toast.LENGTH_SHORT).show();
        }


    }

    public void olvidaste(View view) {
        Intent pasar = new Intent(this, olvidaste.class);
        pasar.putExtra("prueba", userMail.getText().toString());
        startActivity(pasar);

    }
}
