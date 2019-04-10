package mcg.webteam.mahindacollege;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class ShowPost extends AppCompatActivity {

    private String post_key =null;
    private TextView nTitle;
    private TextView nDesc;
    private TextView link;
    private ImageView mImg;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/arimo.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());


        post_key = getIntent().getExtras().getString("postID");
      //  post_key ="L4VrnQxd8TxWyHeNZgR";

        mDatabase = FirebaseDatabase.getInstance().getReference().child("TopFeeds");

        nDesc = (TextView)findViewById(R.id.postDesc);
        nTitle=(TextView)findViewById(R.id.postTopic);
        link = (TextView)findViewById(R.id.linkRef);
        mImg=(ImageView)findViewById(R.id.postImage);

        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = (String) dataSnapshot.child("name").getValue();
                String desc = (String) dataSnapshot.child("fullDesc").getValue();
               String image = (String) dataSnapshot.child("Img").getValue();
                String linkadd = (String) dataSnapshot.child("linkAdd").getValue();
                String linkname = (String) dataSnapshot.child("linkName").getValue();
                //String title = (String) dataSnapshot.child("name").getValue();
                String LinkbtnShow = "<a href='" + linkadd +"'>" + linkname +"</a>";


                if(linkadd == null || linkname==null){

                    link.setText("#MakeHistory");
                }
                else{
                    link.setText(Html.fromHtml(LinkbtnShow));
                }
               if(  nTitle.getText()=="#FOTG18"){

                    Context context = getApplicationContext();
                    CharSequence text = "datafound!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);

                }

                nTitle.setText(name);
                nDesc.setText(desc);
                Picasso.with(ShowPost.this).load(image).into(mImg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
