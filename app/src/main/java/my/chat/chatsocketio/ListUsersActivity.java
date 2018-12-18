package my.chat.chatsocketio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

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
        data.add(contador,new DataList("Pepe",imgUser));
        data.add(contador,new DataList("Maria",imgUser));
        data.add(contador,new DataList("Miquel",imgUser));
        data.add(contador,new DataList("PAloma",imgUser));
        return  data;
    }
    

}
