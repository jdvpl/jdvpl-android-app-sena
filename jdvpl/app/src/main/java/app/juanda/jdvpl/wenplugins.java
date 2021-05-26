package app.juanda.jdvpl;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;


public class wenplugins extends AppCompatActivity {
    WebView web1;
    final String url="https://www.shopify.com/partners/blog/sublime-text-plugins-2018";
    Button uno;
    private ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wenplugins);

        Button regresar=findViewById(R.id.flecha);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        web1=findViewById(R.id.wwebplugisn);
        WebSettings ws=web1.getSettings();
        ws.setJavaScriptEnabled(true);

        web1.setWebViewClient(new WebViewClient());
        web1.loadUrl(url);
        uno=findViewById(R.id.btnregre);
        p=findViewById(R.id.progre1);
        p.setMax(100);


        web1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                p.setProgress(newProgress);
                p.setVisibility(View.VISIBLE);
                uno.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                uno.setVisibility(View.VISIBLE);
                p.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void reg(View view) {
        finish();
    }
}
