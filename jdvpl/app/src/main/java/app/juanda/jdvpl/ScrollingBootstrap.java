package app.juanda.jdvpl;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ScrollingBootstrap extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scrolling_bootstrap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Bootstrap");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fade fade= new Fade();
        View decor=getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);

        }

    }

    public void paginaboots(View view) {
        Intent boo=new Intent(this,webBootstrap.class);
        startActivity(boo);
    }

    public void bootrapvideo(View view) {
        Intent boostrap=new Intent(this,YoutubeBootstrap.class);
        startActivity(boostrap);
    }
}
