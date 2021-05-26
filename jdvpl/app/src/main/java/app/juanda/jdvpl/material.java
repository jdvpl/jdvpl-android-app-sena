package app.juanda.jdvpl;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class material extends YouTubeBaseActivity {
    YouTubePlayerView playermaterial;
    private final String API_key = "AIzaSyAVZDkRirnlu8sVzFs-G-mCCblsJ-rbUQA";
    ImageButton btn_xampp,btn_sublime;
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.material);
        myDialog = new Dialog(this);

        playermaterial = findViewById(R.id.playmaterial);
        playermaterial.initialize(API_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {

                    List<String> videoList=new ArrayList<>();
                    videoList.add("BZoxXW1qXNA");
                    videoList.add("nDuSO7I8z1Y");
                    youTubePlayer.loadVideos(videoList);
                    youTubePlayer.getFullscreenControlFlags();
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(material.this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
            }
        });
        btn_xampp=findViewById(R.id.btn_webxampp);
        btn_xampp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webx=new Intent(material.this,webxampp.class);
                startActivity(webx);
            }
        });
        btn_sublime=findViewById(R.id.websublime);
        btn_sublime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webx=new Intent(material.this,webSublime.class);
                startActivity(webx);
            }
        });
       Button regresar=findViewById(R.id.flecha);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void ShowPopup(View view) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent plu=new Intent(getApplicationContext(),wenplugins.class);
                startActivity(plu);
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}

