package my.chat.chatsocketio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_profilephoto extends AppCompatActivity {
public String Nickname = "Karen";
public String Picture = "http://i.imgur.com/DvpvklR.png";

TextView nick;
CircleImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilephoto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        photo = findViewById(R.id.viewPhoto);
        nick = findViewById(R.id.textNickname);
        final Bundle n = this.getIntent().getExtras();
        String datos = n.getString("name2");

      // nick = datos.toString();


        nick.setText(datos);
        Picasso.get().load(Picture).into(photo);

    }

}
