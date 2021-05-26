package app.juanda.jdvpl;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class videos extends YouTubeBaseActivity {

    YouTubePlayerView player;
    private final String API_key = "AIzaSyAVZDkRirnlu8sVzFs-G-mCCblsJ-rbUQA";

    FirebaseUser currentUsuario;
    FirebaseAuth mAuth;

    //comentarios
    ImageView currentUser;
    EditText editTextComment;
    ImageButton btnAddComment;
    RecyclerView RvComment;
    //para la conexion a la base de datos de firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<comentario>options;
    FirebaseRecyclerAdapter<comentario,myRecycleViewHolder> adapter;
    comentario selectedcom;
    String selectedKey;
    Button regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_videos);




        player = findViewById(R.id.playerf);
        player.initialize(API_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {

                    youTubePlayer.loadPlaylist("PLkA7zujFJarE9qLbjpQG_t-zDUGH3Sm2C");
                    //youTubePlayer.loadVideo(VIDEO_CODE);
                    youTubePlayer.getFullscreenControlFlags();
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(videos.this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUsuario = mAuth.getCurrentUser();
        ImageView colocarIm=findViewById(R.id.userfoto);
        Glide.with(this).load(currentUsuario.getPhotoUrl()).into(colocarIm);

        editTextComment=findViewById(R.id.comenttitle);
        btnAddComment=findViewById(R.id.btnadd);
        RvComment=findViewById(R.id.recicle_vista);
        RvComment.setLayoutManager(new LinearLayoutManager(this));
        regresar=findViewById(R.id.flecha);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Videos");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextComment.getText().toString().isEmpty()){
                    postcomment();
                }else {
                showMessage("debes escribir algo ");
            }


            }
        });
        displaycomment();
    }

    @Override
    protected void onStop() {
        if(adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    private void postcomment() {
        String comment=editTextComment.getText().toString();
        comentario comentario=new comentario(comment);

        databaseReference.push().setValue(comentario);
        adapter.notifyDataSetChanged();
        editTextComment.setText("");

    }

    private void displaycomment() {
        options=new FirebaseRecyclerOptions.Builder<comentario>().setQuery(databaseReference,comentario.class).build();

        adapter= new FirebaseRecyclerAdapter<comentario, myRecycleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myRecycleViewHolder myRecycleViewHolder, int position, @NonNull final comentario comentario) {
                myRecycleViewHolder.txt_commentarios.setText(comentario.getUcomentario());

                myRecycleViewHolder.setItemClickListened(new ItemClickListened() {
                    @Override
                    public void onClick(View view, int position) {
                        selectedcom=comentario;
                        selectedKey=getSnapshots().getSnapshot(position).getKey();
                        Log.d("Key Item",""+selectedKey);

                        //bind data

                        editTextComment.setText(comentario.getUcomentario());
                    }
                });
            }

            @NonNull
            @Override
            public myRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View itemView=LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item,viewGroup,false);

                return new myRecycleViewHolder(itemView);
            }
        };
        adapter.startListening();
        RvComment.setAdapter(adapter);


    }


    private void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


}

