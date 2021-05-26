package app.juanda.jdvpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class loginredes extends AppCompatActivity {
    private static final int MY_RQUEST_CODE = 123;
    List<AuthUI.IdpConfig> providers;
    Button salir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginredes);
        salir=findViewById(R.id.salir);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(loginredes.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        salir.setEnabled(false);
                        showSignInOptions();
                        salir.setVisibility(View.INVISIBLE);
                        Intent sls=new Intent(loginredes.this,login.class);
                        startActivity(sls);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginredes.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        showSignInOptions();
    }

    private void showSignInOptions() {
        providers= Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.Mytheme)
                        .build(),MY_RQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ss=new Intent(loginredes.this, login.class);
        startActivity(ss);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==MY_RQUEST_CODE){
            IdpResponse response=IdpResponse.fromResultIntent(data);
            if (resultCode==RESULT_OK){
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this,"Bienvenido  "+user.getDisplayName(),Toast.LENGTH_SHORT).show();
                salir.setEnabled(true);


            }else {

                Intent s=new Intent(this,login.class);
                startActivity(s);
                finish();

            }
        }
    }

    public void iniciodra(View view) {
        Intent seor=new Intent(loginredes.this,inicio.class);
        startActivity(seor);
        finish();
    }
}
