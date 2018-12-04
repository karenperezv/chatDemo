package my.chat.chatsocketio;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public class ChatRoomWithUserActivity extends AppCompatActivity{
    public  String U1="Karen";
    public  String U2;
    public  String imgU1="http://www.imagenes-paradescargar.com/wp-content/uploads/2017/02/imagenes-para-enamorar-con-perritos-bonitas.jpg";
    public  String imgU2="http://www.imagenes-paradescargar.com/wp-content/uploads/2017/02/imagenes-para-enamorar-con-perritos-con-frases-bonitas.jpg";
    TextView UserName2;
    Button enviarSMS;
    ImageView prueba;
    ImageView conec;
    EditText newSMS;
    RecyclerView mRecicler;
    String TAG = "App Liberti";
    otherSMS m=new otherSMS();
    chatCompleto completo= new chatCompleto();
    ArrayList<String> listdata = new ArrayList<String>();
    JSONObject jObj = new JSONObject();
    private RecyclerView.LayoutManager layoutManager;
    private HetAdapter adapter;
    private List<Item> items = new ArrayList<>();
    private Boolean hasConnection = false;
    String timepo;
    private  static String valor = "Valor";


    Socket mSocket;
    {
        try {
           // mSocket = IO.socket("https://chat-sl.herokuapp.com/chats");//url1
            mSocket = IO.socket("https://chatsln.herokuapp.com/chats");
        } catch (URISyntaxException e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_with_user);
        enviarSMS = findViewById(R.id.btnNewSend);
        prueba=findViewById(R.id.btnReturninicio);
        newSMS = findViewById(R.id.txtNewMessage);
        mRecicler = findViewById(R.id.reciclerMessager);
        UserName2=findViewById(R.id.txtUsername2);
        conec=findViewById(R.id.conexion);

        mSocket.connect();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecicler.setLayoutManager(layoutManager);
        Log.i("Conexion", String.valueOf(mSocket.connected()));
        final Bundle n = this.getIntent().getExtras();
        String datos = n.getString("name2");
        U2=datos;
        UserName2.setText(U2);


        final Handler handler = new Handler();
        final int duracion = 1000;

        final Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try{
                        JSONObject obj = new JSONObject();
                            Log.e("RUN","Ejecucion de hilo 2");
                            obj.put("hash", hashConversacion());
                            mSocket.emit("sender", obj);
                            Log.d("Estado ", String.valueOf(mSocket.connected()));
                            mSocket.on("zender",exito2);
                }
                catch (Exception e) {
                    // manejas la excepcion en caso de error.
                }
                finally{
                    // ejecutamos otra vez el handlers

                     handler.postDelayed(this, duracion);
                }
            }
        };


        //Run llenado de REcicler
        final  Handler handlerRec=new Handler();
        final Runnable run3=new Runnable() {
            @Override
            public void run() {
                mRecicler.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                adapter = new HetAdapter(items);
                mRecicler.setLayoutManager(layoutManager);
                mRecicler.setAdapter(adapter);
                mRecicler.smoothScrollToPosition(mRecicler.getAdapter().getItemCount());//ultima posicion
                handlerRec.removeCallbacks(this,0000);
                handler.postDelayed(runnable2,duracion);

            }
        };


        /*Run2*/
        final Handler myHandler = new Handler();
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try{
                    if(mSocket.connected()==true){
                        // handler.postDelayed(this, duracion);

                        Log.e("Statis conexion", String.valueOf(mSocket.connected()));
                        JSONObject obj = new JSONObject();
                        obj.put("hash", hashConversacion());
                        mSocket.emit("allChat", obj);
                        Log.d("Estado ", String.valueOf(mSocket.connected()));
                        mSocket.on("rAllChat",chatCompleto);
                        myHandler.removeCallbacks(this,0000);
                        handlerRec.postDelayed(run3, duracion);//execute two thread


                    }else {
                        myHandler.postDelayed(this, duracion);
                        mSocket.connect();
                        Log.e("Statis conexion", String.valueOf(mSocket.connected()));

                    }
                }
                catch (Exception e) {
                    // manejas la excepcion en caso de error.
                }
                finally{
                    //   handler.postDelayed(this, duracion);
                }
            }
        };
        myHandler.postDelayed(myRunnable, duracion);


        /*_______________________________________________________________________________________________________*/


        enviarSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSocket.connect();
                if (newSMS.getText().length() != 0) {
                        getSendAllMessageJson(hashConversacion(), U1, U1, U2, U2, U1, U2, newSMS.getText().toString());
                        newSMS.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "INGRESE UN MENSAJE", Toast.LENGTH_SHORT).show();
                }
            }

        });

        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ListUsersActivity.class);
                startActivity(intent);
               /* mSocket.disconnect();
                mSocket.off("zender", exito2);
                handler.removeCallbacksAndMessages(null);*/
            }
        });

        conec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.connect();
                Log.e("Conexion", String.valueOf(mSocket.connected()));
            }
        });


    }





    private void getSendAllMessageJson(String hash, String username, String nickname, String username2, String nickname2,String usuario1id,String ussuario2id, String messagex) {
        try {
            jObj.put("hash", hash);
            jObj.put("username", username);
            jObj.put("nickname", nickname);
            jObj.put("username2", username2);
            jObj.put("nickname2", nickname2);
            jObj.put("usuario1id", usuario1id);
            jObj.put("usuario2id", ussuario2id);
            jObj.put("msg", messagex);
            jObj.put("stts","false");
            mSocket.emit("receiver", jObj);
            Log.i("Conexion", String.valueOf(mSocket.connected()));
            Log.i("Jonny", jObj.toString());
            String currentDateTimeString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());//checar fecha
            items.add(new Item1(messagex, currentDateTimeString,imgU1));
            //items.add(new Item2(m.getVar0(), m.getVar1(),m.getVar2(), m.getVar3(), R.drawable.ic_launcher_background));
            mRecicler.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            ((LinearLayoutManager) layoutManager).setReverseLayout(false);
            adapter = new HetAdapter(items);
            mRecicler.setLayoutManager(layoutManager);
            mRecicler.setAdapter(adapter);
            mRecicler.smoothScrollToPosition(mRecicler.getAdapter().getItemCount());//recorrecr recicler
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private final Emitter.Listener exito2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray mJsonArray = (JSONArray) args[0];
                    Log.e("Datos_________", String.valueOf(mJsonArray));
                    try {
                        if (valor.equals( String.valueOf(mJsonArray))) {
                            Log.e("if","SI mandas esto reconoces mi condicion");

                        }else{
                            Log.e("CONdicion", valor);
                            m.setVar0(String.valueOf(mJsonArray.getString(0)));
                            m.setVar1(String.valueOf(mJsonArray.getString(1)));
                            m.setVar2(String.valueOf(mJsonArray.getString(2)));
                            m.setVar3(String.valueOf(mJsonArray.getString(3)));
                            Log.e("valor 0", "__" + String.valueOf(mJsonArray.getString(0)) + "_");
                            Log.i(TAG, "run: " + args.length);

                            if (!U1.equals(String.valueOf(mJsonArray.getString(0)))) {
                                items.add(new Item2(m.getVar0(), m.getVar1(), m.getVar2(), m.getVar3(), imgU2));
                                mRecicler.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(getApplicationContext());
                                ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                                adapter = new HetAdapter(items);
                                mRecicler.setLayoutManager(layoutManager);
                                mRecicler.setAdapter(adapter);
                                mRecicler.smoothScrollToPosition(adapter.getItemCount());//recorrer recicler
                                /*pasar dato a true de los que regresa el otro usuario*/
                                JSONObject obj2=new JSONObject();
                                obj2.put("hash",hashConversacion());
                                mSocket.emit("status",obj2);

                            }


                            valor = String.valueOf(mJsonArray);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;

                    }


                }

            });
        }

    };


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    private final  Emitter.Listener chatCompleto= new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONArray ja = (JSONArray)args[0];
                Log.e("array",ja.toString());
                for (int i = 0; i < ja.length(); i++) {

                    JSONObject row = ja.getJSONObject(i);
                    completo.setCreated(row.getString("created"));
                    completo.setId(row.getString("_id"));
                    completo.setUsuario1(row.getString("usuario1"));
                    completo.setUsuario2(row.getString("usuario2"));
                    completo.setMensaje(row.getString("msg"));

                    Log.e("prueba",completo.getUsuario1()+" "+ completo.getUsuario2()+" "+ completo.getMensaje()+" "+completo.getCreated());
                    String fecha = completo.getCreated().substring(11,16);
                    String hora = completo.getCreated().substring(0,10);
                    String time=fecha+" "+hora;
                    if (!U1.equals(completo.getUsuario1())) {
                        Log.e("Usuario 2",i+" "+completo.getUsuario1());
                        items.add(new Item2(completo.getUsuario2(), completo.getUsuario1(), completo.getMensaje(), time, imgU2));
                    }else{
                        Log.e("Usuario 1",i+" "+completo.getUsuario1());
                        items.add(new Item1(completo.getMensaje(),time,imgU1));
                    }

                }
            }catch (Exception e){

            }
        }

    };


    private String hashConversacion(){
        ArrayList<String> contactos = new ArrayList<>();
        contactos.add(U1);
        contactos.add(U2);
        Collections.sort(contactos);
        String concat =contactos.get(0)+contactos.get(1);
        String hash = new String(Hex.encodeHex(DigestUtils.md5(concat)));
        Log.e("hash",hash);
        return hash;
    }
}
