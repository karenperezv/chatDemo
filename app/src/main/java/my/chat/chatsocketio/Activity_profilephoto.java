package my.chat.chatsocketio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_profilephoto extends AppCompatActivity {
public String Picture = "http://i.imgur.com/DvpvklR.png";

TextView nick;
CircleImageView photo;
ImageView ret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilephoto);

        photo = findViewById(R.id.viewPhoto);
        nick = findViewById(R.id.textNickname);
        ret=findViewById(R.id.btnRegresarPerfil);
        final Bundle n = this.getIntent().getExtras();
        String datos = n.getString("name2");

      // nick = datos.toString();


        nick.setText(datos);
        Picasso.get().load(Picture).into(photo);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ListUsersActivity.class);
                startActivity(intent);
            }
        });

    }

}
