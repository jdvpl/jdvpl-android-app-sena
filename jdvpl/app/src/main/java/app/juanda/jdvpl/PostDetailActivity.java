package app.juanda.jdvpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {


    ImageView imgPost,imgUserPost,imgCurrentUser;
    TextView txtPostDesc,txtPostDateName,txtPostTitle,txttele,cr,crnu,crdire,crciu,depar,pais;
    EditText editTextComment;
    Button btnAddComment,maps,potwhatss;

    String PostKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    static String COMMENT_KEY = "Comment" ;
    DatabaseReference databaseReference;
    private String mpostkey=null;
    private String muser=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_post_detail);


        // ini Views
        RvComment = findViewById(R.id.rv_comment);
        imgPost =findViewById(R.id.post_detail_img);
        imgUserPost = findViewById(R.id.post_detail_user_img);
        imgCurrentUser = findViewById(R.id.post_detail_currentuser_img);

        txtPostTitle = findViewById(R.id.post_detail_title);
        txtPostDesc = findViewById(R.id.post_detail_desc);
        txtPostDateName = findViewById(R.id.post_detail_date_name);
        txttele=findViewById(R.id.post_detail_telefono);

        editTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Posts");

        mpostkey=getIntent().getExtras().getString("postKey");
        muser = getIntent().getExtras().getString("userId");


        //mapas
        cr=findViewById(R.id.cr);
        crnu=findViewById(R.id.crnu);
        crdire=findViewById(R.id.crdire);
        crciu=findViewById(R.id.crciu);
        depar=findViewById(R.id.depar);
        pais=findViewById(R.id.pais);
        potwhatss=findViewById(R.id.potwhatssap);

        potwhatss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txttele.getText().toString().isEmpty()){
                    potwhatss.setVisibility(View.INVISIBLE);
                }else {
                    potwhatss.setVisibility(View.VISIBLE);
                    String juan = txttele.getText().toString();
                    Uri uri = Uri.parse("https://wa.me/+57" + juan);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        maps = findViewById(R.id.btnmaps);
            maps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cr.getText().toString().isEmpty()) {
                        crnu.setText("");
                        crdire.setText("");
                        crciu.setText("");
                        depar.setText("");
                        pais.setText("");
                        maps.setVisibility(View.INVISIBLE);
                    }else{
                    maps.setVisibility(View.VISIBLE);
                    String l = cr.getText().toString();
                    String y = crnu.getText().toString();
                    String m = crdire.getText().toString();
                    String c = crciu.getText().toString();
                    String d = depar.getText().toString();
                    String p = pais.getText().toString();
                    String url = "https://www.google.com/maps/search/" + l + ".+" + y + "+%23" + m + ",+" + c + ",+" + d + ",+" + p + "/";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    }
                }

            });




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // add Comment button click listner

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (!editTextComment.getText().toString().isEmpty()){


                btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey).push();
                String comment_content = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                Comment comment = new Comment(comment_content,uid,uimg,uname);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comentario agregado");
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("error al ingresar el comentario : "+e.getMessage());

                    }
                });
            }else {
                showMessage("debes escribir algo ");
            }
            }

        });


        Button regresar=findViewById(R.id.flecha);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String postImage = getIntent().getExtras().getString("postImage") ;
        Glide.with(this).load(postImage).into(imgPost);

        String postTitle = getIntent().getExtras().getString("title");
        txtPostTitle.setText(postTitle);

        String userpostImage = getIntent().getExtras().getString("userPhoto");
        Glide.with(this).load(userpostImage).into(imgUserPost);

        String postDescription = getIntent().getExtras().getString("description");
        txtPostDesc.setText(postDescription);


        String cra=getIntent().getExtras().getString("cr");
        cr.setText(cra);

        String crnum=getIntent().getExtras().getString("numero");
        crnu.setText(crnum);

        String dsa=getIntent().getExtras().getString("direcc");
        crdire.setText(dsa);

        String dcg=getIntent().getExtras().getString("ciudad");
        crciu.setText(dcg);

        String der=getIntent().getExtras().getString("departamento");
        depar.setText(der);

        String pai=getIntent().getExtras().getString("pais");
        pais.setText(pai);

        String userid = getIntent().getExtras().getString("userid");


        // setcomment user image

        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imgCurrentUser);

        PostKey = getIntent().getExtras().getString("postKey");

        String date = timestampToString(getIntent().getExtras().getLong("postDate"));
        txtPostDateName.setText(date);
        iniRvComment();

        String posttele=getIntent().getExtras().getString("telefono");
        txttele.setText(posttele);

        txttele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String juan=txttele.getText().toString();
                Uri uri = Uri.parse("https://wa.me/+57"+juan);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }
    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment);
                }

                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                RvComment.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    private void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }




    public void eliminarnd(View view) {
        Button bo=findViewById(R.id.eliminar);

        muser = getIntent().getExtras().getString("userId");
        firebaseUser.getUid();
            if (muser.equals(firebaseUser.getUid())){
                bo.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder=new AlertDialog.Builder(PostDetailActivity.this);
                builder.setTitle("¿Estas seguro de eliminar?");
                builder.setMessage("Eliminar completamente este Post");
                builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(mpostkey).removeValue();
                        firebaseDatabase.getReference().child(mpostkey).removeValue();
                        finish();
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


                //separacioaaaaaaaaaaaaaaaaaaaa


            }else {
                bo.setVisibility(View.INVISIBLE);
                Toast.makeText(this,"No eres el dueño de este Post!",Toast.LENGTH_SHORT).show();
            }

    }
}


