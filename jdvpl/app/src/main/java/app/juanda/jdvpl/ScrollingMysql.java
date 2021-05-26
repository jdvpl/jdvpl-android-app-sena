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

public class ScrollingMysql extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scrolling_mysql);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       getSupportActionBar().setTitle("MYSQL");
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

    public void videoxampp(View view) {
        Intent xapp=new Intent(this,YoutubeXampp.class);
        startActivity(xapp);
    }

    public void paginatipo(View view) {
        Intent tipo=new Intent(this,webtipos.class);
        startActivity(tipo);
    }

    public void mysqvideo(View view) {
        Intent my=new Intent(this,YoutubeMysql.class);
        startActivity(my);
    }
}
