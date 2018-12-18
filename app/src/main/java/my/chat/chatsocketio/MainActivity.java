package my.chat.chatsocketio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
ImageView bsms;
RecyclerView mRecycler;
RecyclerView mRecyclerchatREAD;
Boolean status=false;
String U1="Luis";
public  String U2;
public String dateCreated="Default";
String cadenaUser="DEFAULT";

   /* ArrayList<chatExistente2> data = new ArrayList<>();
    ArrayList<DataChatRead2>  dataChatReads = new ArrayList<>();
    GridLayoutManager glmChatRead;
    GridLayoutManager glm;
    MessageAdapter adapter;
    AdapterChatRead2 adapterchatread;*/

   private RecyclerView.LayoutManager layoutManager;
    private AdapterChatRead adapter;

    private List<ItemExistentMessage> items = new ArrayList<>();
    private List<ItemExistentMessage> items2 = new ArrayList<>();



    final int duracion = 1000;
    final Handler handler = new Handler();
     Runnable runnable;



    Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://chatsln.herokuapp.com/chats");
        } catch (URISyntaxException e) {

        }
    }



    public String TAG="Prueba";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bsms=findViewById(R.id.btnNuevoMensaje);
        mRecycler=findViewById(R.id.rcvChatExistente);
        mRecyclerchatREAD=findViewById(R.id.rcvNoLeidos);
        mSocket.connect();
        //---------------------------------------------------------------------




        Handler h=new Handler();
        final int duracion2=2000;
        final  Handler handler2=new Handler();

        final Runnable runab2=new Runnable() {
            @Override
            public void run() {
/*                glm = new GridLayoutManager(getApplicationContext(), 1);
                mRecycler.setLayoutManager(glm);
                adapter = new MessageAdapter(data);
                mRecycler.setAdapter(adapter);
                //Rellenar second recicler view
                glmChatRead = new GridLayoutManager(getApplicationContext(), 1);
               mRecyclerchatREAD.setLayoutManager(glmChatRead);
                adapterchatread=new AdapterChatRead2(dataChatReads);
                mRecyclerchatREAD.setAdapter(adapterchatread);*/
                mRecyclerchatREAD.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                adapter = new AdapterChatRead(items);
                mRecyclerchatREAD.setLayoutManager(layoutManager);
                mRecyclerchatREAD.setAdapter(adapter);
                mRecyclerchatREAD.smoothScrollToPosition(mRecyclerchatREAD.getAdapter().getItemCount());
                //second recicler
                mRecycler.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                adapter = new AdapterChatRead(items2);
                mRecycler.setLayoutManager(layoutManager);
                mRecycler.setAdapter(adapter);
                mRecycler.smoothScrollToPosition(mRecycler.getAdapter().getItemCount());

                handler2.removeCallbacks(this,0000);



            }
        };


                runnable= new Runnable() {
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
                  //
                   // handler.postDelayed(this, duracion);
                }
            }
        };
        handler.postDelayed(runnable, duracion);



//__________________________Uptade Activity second plan_______________________________________________________


        final int duracionActivity = 15000;

        final Handler handlerActivity = new Handler();
        final Runnable runnableActivity = new Runnable() {
            @Override
            public void run(){
                try{
                    Log.e("Activity run","ejecucion de thread sin problemas");
                    //SwipeRefreshLayout.OnRefreshListener(this);
                    //recreate();
                    onRefresh();
                }
                catch (Exception e) {
                    // manejas la excepcion en caso de error.
                }
                finally{
                    //
                     handlerActivity.postDelayed(this, duracionActivity);
                }
            }
        };
        handlerActivity.postDelayed(runnableActivity, duracionActivity);

//_________________________________________________________________________________



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
                    Log.e("array", String.valueOf(arr));
                    String Usuario1 = arr.get(0).toString();
                    String Usuario2 = arr.get(1).toString();
                    String mensaje = arr.get(2).toString();
                    String hash = arr.get(3).toString();
                    Log.e("Usuario", Usuario1+" "+Usuario2+" "+mensaje+" "+hash);

                    if (U1.equals(Usuario1)||U1.equals(Usuario2)){
                        JSONObject obj = new JSONObject();
                        obj.put("hash", hash);
                        mSocket.emit("sender", obj);
                        mSocket.on("zender",exito2);
                        //U2=Usuario2;
                        //data.add(new chatExistente(Usuario2,mensaje));
                    }/*else if (U1.equals(Usuario2)){
                        JSONObject obj = new JSONObject();
                        obj.put("hash", hash);
                        mSocket.emit("sender", obj);
                        mSocket.on("zender",exito2);
                        //U2=Usuario2;
                        // data.add(new chatExistente(Usuario2,mensaje));
                    }*/
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
                    Log.e("Datos", String.valueOf(mJsonArray));

                    try {
                            String val0=String.valueOf(mJsonArray.getString(0));
                            String val1=String.valueOf(mJsonArray.getString(1));
                            String val2=String.valueOf(mJsonArray.getString(2));
                            String val3=String.valueOf(mJsonArray.getString(3));//fecha de creacion
                            String stts=String.valueOf(mJsonArray.getString(4));
                          //  Log.e("valor 0", "__" + String.valueOf(mJsonArray.getString(4)) + "_");
                                //Log.i(TAG, "run: " + args.length);
                        Log.e("---***---",val0+" "+val1+" "+val2+" "+val3+" "+stts);
                        String mensajeCortado="123";
                        if (val2.length()>=50) {
                            mensajeCortado = val2.substring(0, 25);
                        }else{
                            mensajeCortado="123";
                        }


                        if (!dateCreated.equals(val3)){
                            if (U1.equals(val0)){
                                if (mensajeCortado.equals("http://res.cloudinary.com")){
                                    items.add(new DataChatImageRead(val1,val2));
                                    dateCreated = val3;
                                }else {
                                    //  dataChatReads.add(new DataChatRead2(val1,val2));
                                    items.add(new DataMessageRead(val1, val2));
                                    dateCreated = val3;
                                }
                            }else if (!U1.equals(val0)){
                                if (stts == "true") {
                                    if (mensajeCortado.equals("http://res.cloudinary.com")){
                                        items.add(new DataChatImageRead(val0,val2));
                                        dateCreated=val3;
                                    }else{
                                        //dataChatReads.add(new DataChatRead2(val0, val2));
                                        items.add(new DataMessageRead(val0, val2));
                                        dateCreated=val3;
                                    }
                                } else {
                                    //   data.add(new chatExistente2(val0, val2));
                                    if (mensajeCortado.equals("http://res.cloudinary.com")) {
                                        items2.add(new DataChatImage(val0,val2));
                                        dateCreated = val3;
                                    } else{
                                        items2.add(new DataMessageNotRead(val0, val2));
                                    dateCreated = val3;
                                }
                                }
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


    public void onRefresh() {
        mSocket.off("rExistingChat",chatCompleto);
        //  for (int i=0;i<=data.size();i++ ){
        //    data.remove(i);
        // }
        //  for (int i=0;i<=dataChatReads.size();i++ ){
        //      dataChatReads.remove(i);
        //}
        handler.postDelayed(runnable, duracion);
        //data.clear();
        //dataChatReads.clear();
        items.clear();
        items2.clear();
        mRecycler.setAdapter(null);
        mRecyclerchatREAD.setAdapter(null);
    }


    protected void onDestroy(){
        super.onDestroy();
        mSocket.off("rExistingChat",chatCompleto);
    }

}
