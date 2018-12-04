package my.chat.chatsocketio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class ListUsersActivity extends AppCompatActivity {
ImageView regresar;
    RecyclerView mRecicler;
    String TAG = "App Liberti";
    public  int contador=0;
    public  String imgUser="https://cdn.icon-icons.com/icons2/185/PNG/512/Contacts_22705.png";

    GridLayoutManager glm;
    ListAdapter adapter;
    ArrayList<DataList> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        regresar=findViewById(R.id.btnRegresarMainActivity);
        mRecicler = findViewById(R.id.reciclerViewUsers);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //hashConversacion();
            }
        });


        glm = new GridLayoutManager(this, 1);
        mRecicler.setLayoutManager(glm);
        adapter = new ListAdapter(dataSet());
        mRecicler.setAdapter(adapter);



    }
    private ArrayList<DataList> dataSet()
    {
        data.add(contador,new DataList("Jonny",imgUser));
        data.add(contador,new DataList("Luis",imgUser));
        data.add(contador,new DataList("Karen",imgUser));
        return  data;
    }


    private String hashConversacion(){
        String user1="zAdrian";
        String user2="Victor";
        ArrayList<String> contactos = new ArrayList<>();
        contactos.add(user1);
        contactos.add(user2);
        Collections.sort(contactos);
        String concat =contactos.get(0)+contactos.get(1);
        Log.e("COncatenacion",concat);
        String string = "demo";
        String hash = new String(Hex.encodeHex(DigestUtils.md5(concat)));
        Log.e("hash",hash);
        return hash;
    }
}
