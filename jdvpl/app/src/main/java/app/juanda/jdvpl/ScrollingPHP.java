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

public class ScrollingPHP extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scrolling_php);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PHP");
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

    public void videocss3(View view) {
        Intent j=new Intent(this,youtubephp.class);
        startActivity(j);
        overridePendingTransition(R.anim.up,R.anim.down);
    }

    public void youtubemys(View view) {
        Intent m=new Intent(this,YoutubeMysql.class);
        startActivity(m);
    }
}
