package my.chat.chatsocketio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
ImageView bsms;
RecyclerView mRecycler;
RecyclerView mRecyclerchatREAD;
Boolean status=false;
String U1="Karen";
String U2;


    ArrayList<chatExistente> data = new ArrayList<>();
    ArrayList<DataChatRead>  dataChatReads = new ArrayList<>();
    GridLayoutManager glmChatRead;
    GridLayoutManager glm;
    MessageAdapter adapter;
    AdapterChatRead adapterchatread;



    Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://chatsln.herokuapp.com/chats");
        } catch (URISyntaxException e) {

        }
    }



    public String TAG="Prueba";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bsms=findViewById(R.id.btnNuevoMensaje);
        mRecycler=findViewById(R.id.rcvChatExistente);
        mRecyclerchatREAD=findViewById(R.id.rcvNoLeidos);
        mSocket.connect();
        //---------------------------------------------------------------------
        Handler h = new Handler();
        final int duracion2=2000;
        final  Handler handler2=new Handler();



        final Runnable runab2=new Runnable() {
            @Override
            public void run() {
                glm = new GridLayoutManager(getApplicationContext(), 1);
                mRecycler.setLayoutManager(glm);
                adapter = new MessageAdapter(data);
                mRecycler.setAdapter(adapter);
                /*Rellenar second recicler view*/
                glmChatRead = new GridLayoutManager(getApplicationContext(), 1);
               mRecyclerchatREAD.setLayoutManager(glmChatRead);
                adapterchatread=new AdapterChatRead(dataChatReads);
                mRecyclerchatREAD.setAdapter(adapterchatread);
                handler2.removeCallbacks(this,0000);

            }
        };


        final int duracion = 1000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run(){
                try{
                    if(mSocket.connected()==true){
                       // handler.postDelayed(this, duracion);
                        Log.e("Statis conexion", String.valueOf(mSocket.connected()));

                        JSONObject obj=new JSONObject();
                        obj.put("hash","JK");
                        mSocket.emit("existingChat", obj);
                        mSocket.on("rExistingChat",chatCompleto);
                        Log.e("ss", String.valueOf(mSocket.connected()));
//___________________________-Llenar recicler
                        handler2.postDelayed(runab2, duracion2);
//_____________________________fin llenar recicler
                        handler.removeCallbacks(this,0000);

                    }else {
                        handler.postDelayed(this, duracion);
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
        handler.postDelayed(runnable, duracion);





        if (status==true){
            Log.e("MSG","en este momento puedo enviar el sms");
        }




        //----------------------------------------------------------------------
        bsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), ListUsersActivity.class);
                startActivity(intent);
            }

        });


    }

    private final  Emitter.Listener chatCompleto= new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONArray ja = (JSONArray)args[0];
                Log.e("Chatexisting", String.valueOf(ja));
                for (int i = 0; i < ja.length(); i++) {
                    JSONArray arr=ja.getJSONArray(i);
                    Log.e("Cadena", String.valueOf(arr));

                        String Usuario1 = arr.get(0).toString();
                    String Usuario2 = arr.get(1).toString();
                    String mensaje = arr.get(2).toString();
                    String hash = arr.get(3).toString();
                        Log.e("Usuario", Usuario1+" "+Usuario2+" "+mensaje+" "+hash);
                        if (U1.equals(Usuario1)){
                            JSONObject obj = new JSONObject();
                            Log.e("RUN","Ejecucion de hilo 2");
                            obj.put("hash", hash);
                            mSocket.emit("sender", obj);
                            Log.d("Estado ", String.valueOf(mSocket.connected()));
                            mSocket.on("zender",exito2);
                            //data.add(new chatExistente(Usuario2,mensaje));
                        }
                    if (U1.equals(Usuario2)){
                        JSONObject obj = new JSONObject();
                        Log.e("RUN","Ejecucion de hilo 2");
                        obj.put("hash", hash);
                        mSocket.emit("sender", obj);
                        Log.d("Estado ", String.valueOf(mSocket.connected()));
                        mSocket.on("zender",exito2);
                        //data.add(new chatExistente(Usuario2,mensaje));
                    }

                        /*2018-11-21T16:33:31.478Z*/
                    //String fecha = completo.getCreated().substring(11,16);
                    //String hora = completo.getCreated().substring(0,10);
                  //  Log.e("Mensjaes y Usuarios",i+" "+hora+" "+fecha+" "+completo.getUsuario1()+" "+completo.getUsuario2()+" "+completo.getMensaje());
                }
            }catch (Exception e){
            }
        }

    };


    private final Emitter.Listener exito2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray mJsonArray = (JSONArray) args[0];
                    Log.e("Datos_________", String.valueOf(mJsonArray));
                    try {
                            String val0=String.valueOf(mJsonArray.getString(0));
                            String val1=String.valueOf(mJsonArray.getString(1));
                            String val2=String.valueOf(mJsonArray.getString(2));
                            String val3=String.valueOf(mJsonArray.getString(3));
                            String stts=String.valueOf(mJsonArray.getString(4));
                          //  Log.e("valor 0", "__" + String.valueOf(mJsonArray.getString(4)) + "_");
                                //Log.i(TAG, "run: " + args.length);
                        Log.e("PRUEBA",val0+" "+val1+" "+val2+" "+val2+" "+stts);
                        if (U1.equals(val0)){
                            dataChatReads.add(new DataChatRead(val1,val2));
                        }
                        if (!U1.equals(val0)){
                            if (stts == "true") {
                                dataChatReads.add(new DataChatRead(val1, val2));
                            } else {
                                data.add(new chatExistente(val1, val2));
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;

                    }


                }

            });
        }

    };


}
