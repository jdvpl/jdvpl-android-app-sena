package app.juanda.jdvpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class olvidaste extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText ecorreo;
    Button recuperar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_olvidaste);

        firebaseAuth=FirebaseAuth.getInstance();
        ecorreo=findViewById(R.id.corolv);
        recuperar=findViewById(R.id.btnol);

        Bundle datico=getIntent().getExtras();
        String datos=datico.getString("prueba").toString();
        ecorreo.setText(datos);

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ecorreo.getText().toString().isEmpty()){
                    Toast.makeText(olvidaste.this,"debes ingresar el correo",Toast.LENGTH_LONG).show();
                }else {
                firebaseAuth.sendPasswordResetEmail(ecorreo.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(olvidaste.this,"podras restablecer la contraseña en tu correo",Toast.LENGTH_LONG).show();



                        }else {
                            Toast.makeText(olvidaste.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }


                });
            }
            }
        });

    }

    public void find(View view) {
        finish();
    }

    public void lkdjsfjsd(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        startActivity(intent);
    }
}
