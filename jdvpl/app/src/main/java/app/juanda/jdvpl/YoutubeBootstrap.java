package app.juanda.jdvpl;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeBootstrap extends YouTubeBaseActivity {

    YouTubePlayerView player3;
    private final String API_key = "AIzaSyAVZDkRirnlu8sVzFs-G-mCCblsJ-rbUQA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_bootstrap);

        Button regresar=findViewById(R.id.flecha);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        player3 = findViewById(R.id.playerf3);
        player3.initialize(API_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {

                    youTubePlayer.loadVideo("-SwjzWZ-2_4");
                    //youTubePlayer.loadVideo(VIDEO_CODE);
                    youTubePlayer.getFullscreenControlFlags();
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubeBootstrap.this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
