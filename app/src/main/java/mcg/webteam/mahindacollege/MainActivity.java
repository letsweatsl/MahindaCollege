package mcg.webteam.mahindacollege;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.squareup.picasso.Picasso;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class   MainActivity extends AppCompatActivity implements checkVersion.OnUpdateListner {

    private DatabaseReference eventsDatabase;
    private RecyclerView appListView;
    private CardView card;
    private DatabaseReference mDatabase;
    public WebView webview;
    public String rlink;
    public ProgressBar loadbar;
    public boolean postsate =false ;
    public boolean twitterstate = false ;
    public boolean eventstate = false ;
    public boolean livestate = false ;
    public Button btnWatch;
    public TextView txtwatch;
    public FirebaseRemoteConfig firebaseRemoteConfig =
            FirebaseRemoteConfig.getInstance();
    FirebaseRecyclerAdapter<TopAppClass,AppViewHolder> FBRA;
    FirebaseRecyclerAdapter<eventsClass,EventsVholder> EventsRA;
    String ShowOrHideWebViewInitialUse = "show";

    Typeface Arimo;
    Typeface MSans;
  //  Typeface ;
   // Typeface Arimo;


    private boolean scroll ;
    @Override
    protected void onStart() {

        super.onStart();
         FBRA = new FirebaseRecyclerAdapter<TopAppClass, AppViewHolder>(
                TopAppClass.class,
                R.layout.cardlayout,
                AppViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(AppViewHolder viewHolder, TopAppClass model, int position) {

                final String post_key = getRef(position).getKey().toString();

                viewHolder.setDesc(model.getDesc());
                viewHolder.setTitle(model.getName());
                viewHolder.setImg(getApplicationContext(),model.getImg());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent openPost = new Intent(MainActivity.this,ShowPost.class);
                        openPost.putExtra("postID",post_key);

                        startActivity(openPost);

                    }
                });

            }
/*
            protected  void onDataChanged(){
              //  if(loadbar!=null){
                    Context context = getApplicationContext();
                    CharSequence text = "check here";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


            } */

        };
        EventsRA = new FirebaseRecyclerAdapter<eventsClass, EventsVholder>(
                eventsClass.class,
                R.layout.eventcard,
                EventsVholder.class,
                eventsDatabase

        ) {
            @Override
            protected void populateViewHolder(EventsVholder viewHolder, eventsClass model, int position) {
                viewHolder.setDesc(model.getDesc());
                viewHolder.setDate(model.getDate());
                viewHolder.setMonth(model.getMonth());
                viewHolder.setTime(model.getTime());
                viewHolder.setVenue(model.getVenue());
            }


        };
        appListView.setAdapter(FBRA);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postsate=true;

        scroll =true;

        if(getIntent().getExtras()!=null){
            rlink =getIntent().getExtras().getString("link");

            Uri uriUrl = Uri.parse("www.google.com");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
          //  startActivity(launchBrowser);
             PendingIntent pi = PendingIntent.getActivity(this,0,launchBrowser,PendingIntent.FLAG_ONE_SHOT);
            //     Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
            //   PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);


        }
        checkVersion.with(this).onUpdateCheck(this).check();


        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/arimo.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());


       // Arimo = Typeface.createFromAsset(getAssets(), "font/arimo.ttf");
     //   MSans = Typeface.createFromAsset(getAssets(), "font/merri.ttf");

      //  webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
       // webview.loadUrl("https://www.google.com");

        card =(CardView) findViewById(R.id.card);
        webview = (WebView)findViewById(R.id.webview);
        appListView = (RecyclerView)findViewById(R.id.recyView);
        loadbar = (ProgressBar) findViewById(R.id.loadingbar) ;

//card.setBackgroundColor();
        card.setCardBackgroundColor(ContextCompat.getColor(this,R.color.colorCard));

        webview.setWebViewClient(new CustomWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);



        appListView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        appListView.setLayoutManager(mLayoutManager);

        appListView.setAdapter(FBRA);



        checkCon();
/*

        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (scroll == false) {
                    return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
                } else {
                    return (motionEvent.getAction() != MotionEvent.ACTION_MOVE);
                }
            }
        });

*/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadbar.setVisibility(View.GONE);
            }
        }, 4000);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.booky));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.twittery));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.eventy));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.livey));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    card.setVisibility(View.INVISIBLE);
                    postsate=true;
                    twitterstate=false;
                    eventstate=false;
                    webview.setVisibility(View.GONE);
                    appListView.setAdapter(FBRA);
                    return ;

                }else if(tabLayout.getSelectedTabPosition() == 1){
                    postsate=false;
                    eventstate=false;
                    twitterstate = true;
                    card.setVisibility(View.INVISIBLE);
                    loadbar.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadbar.setVisibility(View.GONE);
                        }
                    }, 2500);

                   // loadbar.setVisibility(View.VISIBLE);

                    checkCon();

                    scroll = true;
                    if (Build.VERSION.SDK_INT < 18) {
                        webview.clearView();
                    } else {
                        webview.loadUrl("about:blank");
                    }
                    appListView.setAdapter(null);
                    webview.bringToFront();
                    webview.setVisibility(View.VISIBLE);
                    webview.getSettings().setJavaScriptEnabled(true);
                    webview.setVerticalScrollBarEnabled(true);
                    webview.getSettings().setDomStorageEnabled(true);
                    webview.loadUrl("file:///android_asset/twitter.html");


                   // webview.loadUrl("http://m.me/MahindaCollege");


                  //  webview.getSettings().setJavaScriptEnabled(true);

                  //  webview.getSettings().setDomStorageEnabled(true);


                    return  ;

                }else if(tabLayout.getSelectedTabPosition() == 2){



                  //  loadbar.setVisibility(View.VISIBLE);
                    postsate=false;
                    twitterstate=false;
                    eventstate=true;
                    card.setVisibility(View.INVISIBLE);

                    webview.setVisibility(View.GONE);
                    appListView.setAdapter(EventsRA);
                    return ;
                    /*
                btnWatch.bringToFront();
                    txtwatch.setVisibility(View.VISIBLE);
                    btnWatch.setVisibility(View.VISIBLE);
*/


                }

                else if(tabLayout.getSelectedTabPosition() == 3){
                    postsate=false;
                    eventstate=false;
                    twitterstate = false;
                    livestate =true;
                    card.setVisibility(View.INVISIBLE);
                    loadbar.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadbar.setVisibility(View.GONE);
                        }
                    }, 2500);

                    // loadbar.setVisibility(View.VISIBLE);

                    checkCon();

                    scroll = true;
                    if (Build.VERSION.SDK_INT < 18) {
                        webview.clearView();
                    } else {
                        webview.loadUrl("about:blank");
                    }
                    appListView.setAdapter(null);
                    webview.bringToFront();
                    webview.setVisibility(View.VISIBLE);
                    webview.getSettings().setJavaScriptEnabled(true);
                    webview.setVerticalScrollBarEnabled(true);
                    webview.getSettings().setDomStorageEnabled(true);
                    webview.loadUrl("http://www.live.mahindacollege.lk/");
                    return  ;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });







        mDatabase = FirebaseDatabase.getInstance().getReference().child("TopFeeds");
        eventsDatabase = FirebaseDatabase.getInstance().getReference().child("Events");


    }

    @Override
    protected void onResume() {
        super.onResume();
   if(postsate==true){
       card.setVisibility(View.INVISIBLE);
       webview.setVisibility(View.GONE);
       appListView.setAdapter(FBRA);

   }
    else if(eventstate==true){
       card.setVisibility(View.INVISIBLE);
       webview.setVisibility(View.GONE);
       appListView.setAdapter(EventsRA);

        }
   else if(twitterstate==true){
       card.setVisibility(View.INVISIBLE);
       loadbar.setVisibility(View.VISIBLE);
       final Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               loadbar.setVisibility(View.GONE);
           }
       }, 2500);

       // loadbar.setVisibility(View.VISIBLE);

       checkCon();

       scroll = true;
       if (Build.VERSION.SDK_INT < 18) {
           webview.clearView();
       } else {
           webview.loadUrl("about:blank");
       }
       appListView.setAdapter(null);
       webview.bringToFront();
       webview.setVisibility(View.VISIBLE);
       webview.getSettings().setJavaScriptEnabled(true);
       webview.setVerticalScrollBarEnabled(true);
       webview.getSettings().setDomStorageEnabled(true);
       webview.loadUrl("file:///android_asset/twitter.html");


       // webview.loadUrl("http://m.me/MahindaCollege");


       //  webview.getSettings().setJavaScriptEnabled(true);

       //  webview.getSettings().setDomStorageEnabled(true);


       return  ;

   }else if(eventstate==true){
       postsate=false;
       eventstate=false;
       twitterstate = false;
       livestate =true;
       card.setVisibility(View.INVISIBLE);
       loadbar.setVisibility(View.VISIBLE);
       final Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               loadbar.setVisibility(View.GONE);
           }
       }, 2500);

       // loadbar.setVisibility(View.VISIBLE);

       checkCon();

       scroll = true;
       if (Build.VERSION.SDK_INT < 18) {
           webview.clearView();
       } else {
           webview.loadUrl("about:blank");
       }
       appListView.setAdapter(null);
       webview.bringToFront();
       webview.setVisibility(View.VISIBLE);
       webview.getSettings().setJavaScriptEnabled(true);
       webview.setVerticalScrollBarEnabled(true);
       webview.getSettings().setDomStorageEnabled(true);
       webview.loadUrl("http://www.live.mahindacollege.lk/");
       return  ;

   }
    }

    @Override
    public void OnUpdateListner(final String appUrl) {

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("New Version Available")
                .setMessage("Please update the app to the latest version")
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uriUrl = Uri.parse(appUrl);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {

            // only make it invisible the FIRST time the app is run
            if (ShowOrHideWebViewInitialUse.equals("show")) {
                webview.setVisibility(webview.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            ShowOrHideWebViewInitialUse = "hide";
            loadbar.setVisibility(View.GONE);

            view.setVisibility(webview.VISIBLE);
            super.onPageFinished(view, url);

        }
        public void onReceivedError(WebView view, int errorCode,String description, String failingUrl){
            webview.loadUrl("file:///android_asset/error.html");
        }

    }

    public static class AppViewHolder extends  RecyclerView.ViewHolder{
        View mView;
        public AppViewHolder(View itemView) {
            super(itemView);
             mView = itemView;
        }

        public void setTitle(String title){
           TextView post_txtName = (TextView) mView.findViewById (R.id.txtCardName);


           //post_txtName.setTypeface(railway);
          post_txtName.setText(title);
        }

        public void setDesc(String desc){
            TextView post_txtDesc = (TextView)  mView.findViewById (R.id.txtCardDesc);
           post_txtDesc.setText(desc);
        }
        public void setImg(Context ctx, String image){
            ImageView post_Image = (ImageView)mView.findViewById(R.id.imgCardView);
            Picasso.with(ctx).load(image).into(post_Image);
        }
    }

    public static class EventsVholder extends  RecyclerView.ViewHolder{
        View mView;
        public EventsVholder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        ////edit this
        public void setDate(String date){
            TextView event_txtDate = (TextView) mView.findViewById (R.id.txtEventDate);
            event_txtDate.setText(date);
        }

        public void setMonth(String month){
            TextView event_txtMonth = (TextView)  mView.findViewById (R.id.txtEventMonth);
            event_txtMonth.setText(month);
        }

        public void setDesc(String desc){
            TextView event_txtDesc = (TextView)  mView.findViewById (R.id.txtEventDesc);
            event_txtDesc.setText(desc);
        }
        public void setTime(String time){
            TextView event_txtTime = (TextView)  mView.findViewById (R.id.txtEventTime);
            event_txtTime.setText(time);
        }
        public void setVenue(String venue){
            TextView event_txtVenue = (TextView)  mView.findViewById (R.id.txtEventVenue);
            event_txtVenue.setText(venue);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.info){
            Intent openPost = new Intent(MainActivity.this,info.class);

            startActivity(openPost);

        }
        return super.onOptionsItemSelected(item);
    }

    public void checkCon(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

            connected = true;
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Sorry your network is unavailable";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);


            connected = false;
        }
    }
    public void onCardClick(View v){

        Uri uriUrl = Uri.parse("http://lq.lk/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
