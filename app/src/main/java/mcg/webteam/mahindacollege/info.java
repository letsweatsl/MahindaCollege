package mcg.webteam.mahindacollege;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class info extends AppCompatActivity {

    private TextView header,desc,link,linkmcg;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/arimo.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());



        header =(TextView) findViewById(R.id.infotitle);
        desc=(TextView) findViewById(R.id.infodesc);
        link=(TextView) findViewById(R.id.infolink);
        linkmcg=(TextView) findViewById(R.id.infolinkmcg);
        //header.setText("Developed with " + ("\ufe0f")+"by Web Team MCG" );
        header.setText("Developed by Web Team MCG" );
        desc.setText("The Web Developing Team of Mahinda College, the first ever school cricket web casting crew in Sri Lanka" );
        link.setText("www.mahindacollege.lk");
        linkmcg.setText("www.webteam.lk");


    }

    public void onClickLink (View v){

         Uri uriUrl = Uri.parse("http://mahindacollege.lk/");
          Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
          startActivity(launchBrowser);
    }
    public void onClickLinkMCG(View v){

        Uri uriUrl = Uri.parse("http://webteam.lk/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
