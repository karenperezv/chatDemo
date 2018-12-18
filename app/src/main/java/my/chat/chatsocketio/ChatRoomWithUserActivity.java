package my.chat.chatsocketio;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.GlobalUploadPolicy;
import com.cloudinary.android.policy.UploadPolicy;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import my.chat.chatsocketio.myCloud.MyConfig;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ChatRoomWithUserActivity extends AppCompatActivity{
    public  String U1="Luis";
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
    my.chat.chatsocketio.chatCompleto completo= new chatCompleto();
    ArrayList<String> listdata = new ArrayList<String>();
    JSONObject jObj = new JSONObject();
    private RecyclerView.LayoutManager layoutManager;
    private HetAdapter adapter;
    private List<Item> items = new ArrayList<>();
    private Boolean hasConnection = false;
    String timepo;
    private  static String valor="Default";

    //------------camera-------////
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;

    private String mPath;


    Socket mSocket;
    {
        try {
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
        mOptionButton = (Button) findViewById(R.id.btnCamera);
try {
    MediaManager.init(this, MyConfig.getMyconfigs());
    MediaManager.get().setGlobalUploadPolicy(
            new GlobalUploadPolicy.Builder()
                    .maxConcurrentRequests(3)
                    .networkPolicy(UploadPolicy.NetworkType.UNMETERED)
                    .build());
}catch (Exception e){

}

        if(mayRequestStoragePermission())
            mOptionButton.setEnabled(true);
        else
            mOptionButton.setEnabled(false);


        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });



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


        final Runnable runnable2= new Runnable() {
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
                handler.removeCallbacks(runnable2);
                finish();
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

            String mensajeCortado="123";
            if (messagex.length()>=50) {
                mensajeCortado = messagex.substring(0, 25);
            }else{
                mensajeCortado="123";
            }
            if (mensajeCortado.equals("http://res.cloudinary.com")){
                items.add(new myImage(messagex, currentDateTimeString));

            }else {
                items.add(new Item1(messagex, currentDateTimeString, imgU1));
/*                mRecicler.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this);
                ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                adapter = new HetAdapter(items);
                mRecicler.setLayoutManager(layoutManager);
                mRecicler.setAdapter(adapter);
                mRecicler.smoothScrollToPosition(mRecicler.getAdapter().getItemCount());//recorrecr recicler*/
            }
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
                    Log.e("Datos**************", String.valueOf(mJsonArray));
                    try {
                        Log.e("Valor chat individual ",valor);
                        if (mJsonArray.getString(3).equals(valor)) {
                            Log.e("if","SI mandas esto reconoces mi condicion");

                        }else{
                            m.setVar0(String.valueOf(mJsonArray.getString(0)));
                            m.setVar1(String.valueOf(mJsonArray.getString(1)));
                            m.setVar2(String.valueOf(mJsonArray.getString(2)));
                            m.setVar3(String.valueOf(mJsonArray.getString(3)));
                            Log.e("Created ",  mJsonArray.getString(3));

                            String mensajeCortado="123";
                            if (m.getVar2().length()>=50) {
                                mensajeCortado = m.getVar2().substring(0, 25);
                            }else{
                                mensajeCortado="123";
                            }

                            if (!U1.equals(String.valueOf(mJsonArray.getString(0)))) {
                                 if (mensajeCortado.equals("http://res.cloudinary.com")){
                                     items.add(new otherImage( m.getVar2(), m.getVar3()));
                                 }else {
                                     items.add(new Item2(m.getVar1(), m.getVar0(), m.getVar2(), m.getVar3(), imgU2));
                                  /*   mRecicler.setHasFixedSize(true);
                                     layoutManager = new LinearLayoutManager(getApplicationContext());
                                     ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                                     adapter = new HetAdapter(items);
                                     mRecicler.setLayoutManager(layoutManager);
                                     mRecicler.setAdapter(adapter);
                                     mRecicler.smoothScrollToPosition(adapter.getItemCount());//recorrer recicler
                                     JSONObject obj2 = new JSONObject();
                                     obj2.put("hash", hashConversacion());
                                     mSocket.emit("status", obj2);*/
                                 }
                                mRecicler.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(getApplicationContext());
                                ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                                adapter = new HetAdapter(items);
                                mRecicler.setLayoutManager(layoutManager);
                                mRecicler.setAdapter(adapter);
                                mRecicler.smoothScrollToPosition(adapter.getItemCount());//recorrer recicler
                                /*pasar dato a true de los que regresa el otro usuario*/
                                JSONObject obj2 = new JSONObject();
                                obj2.put("hash", hashConversacion());
                                mSocket.emit("status", obj2);

                            }
                        }
                            valor = String.valueOf(m.getVar3());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;

                    }


                }

            });
        }

    };




    private final  Emitter.Listener chatCompleto= new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONArray ja = (JSONArray) args[0];
                Log.e("array", ja.toString());

                if (ja.length() == 1) {
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject row = ja.getJSONObject(i);
                        completo.setCreated(row.getString("created"));
                        completo.setId(row.getString("_id"));
                        completo.setUsuario1(row.getString("usuario1"));
                        completo.setUsuario2(row.getString("usuario2"));
                        completo.setMensaje(row.getString("msg"));

                        String va=completo.getCreated();
                        Log.e("prueba",completo.getUsuario1()+" "+ completo.getUsuario2()+" "+ completo.getMensaje()+" "+completo.getCreated());
                        String fecha = completo.getCreated().substring(11,16);
                        String hora = completo.getCreated().substring(0,10);
                        String time=fecha+" "+hora;

                        String datorecortado;
                        if (completo.getMensaje().length()>=50){
                            datorecortado=completo.getMensaje().substring(0,25);
                        }else{
                            datorecortado="123";
                        }

                        if (!U1.equals(completo.getUsuario1())) {
                            if (datorecortado.equals("http://res.cloudinary.com")||datorecortado.equals("http:\\/\\/res.cloudinary.c")){
                                items.add(new otherImage(completo.getMensaje(),completo.getCreated()));
                            }else {
                                items.add(new Item2(completo.getUsuario2(), completo.getUsuario1(), completo.getMensaje(), time, imgU2));
                            }
                        }else{
                            if (datorecortado.equals("http://res.cloudinary.com")||datorecortado.equals("http:\\/\\/res.cloudinary.c")){
                                items.add(new myImage(completo.getMensaje(),completo.getCreated()));
                            }else {
                                items.add(new Item1(completo.getMensaje(), time, imgU1));
                            }
                        }

                        valor = va;

                    }
                }else{

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject row = ja.getJSONObject(i);
                    completo.setCreated(row.getString("created"));
                    completo.setId(row.getString("_id"));
                    completo.setUsuario1(row.getString("usuario1"));
                    completo.setUsuario2(row.getString("usuario2"));
                    completo.setMensaje(row.getString("msg"));

                    String va = completo.getCreated();
                    String fecha = completo.getCreated().substring(11, 16);
                    String hora = completo.getCreated().substring(0, 10);
                    String time = fecha + " " + hora;




                    String datorecortado;
                    if (completo.getMensaje().length()>=50){
                        datorecortado=completo.getMensaje().substring(0,25);
                    }else{
                        datorecortado="123";
                    }



                    if (!U1.equals(completo.getUsuario1())) {
                        if (datorecortado.equals("http://res.cloudinary.com")){
                            items.add(new otherImage(completo.getMensaje(),completo.getCreated()));
                        }else {
                            items.add(new Item2(completo.getUsuario2(), completo.getUsuario1(), completo.getMensaje(), time, imgU2));
                        }
                    }else{
                        if (datorecortado.equals("http://res.cloudinary.com")){
                            items.add(new myImage(completo.getMensaje(),completo.getCreated()));
                        }else {
                            items.add(new Item1(completo.getMensaje(), time, imgU1));
                        }
                    }

                    valor = va;

                    Log.e("Valor de chatva", va);
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

    //camera//



    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomWithUserActivity.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    try {
                        final String nameimg="camera-"+nameConcatImg();
                        String requestId = MediaManager.get().upload(mPath)
                                .option("public_id",nameimg)
                                .option("folder","imgLibertiApp")
                                .callback(new UploadCallback() {
                                    @Override
                                    public void onStart(String requestId) {
                                        // your code here
                                        Log.d("Image","Cargando....");
                                    }
                                    @Override
                                    public void onProgress(String requestId, long bytes, long totalBytes) {
                                        // example code starts here
                                        Double progress = (double) bytes/totalBytes;
                                        // post progress to app UI (e.g. progress bar, notification)
                                        // example code ends here
                                        Log.d("Progreso",String.valueOf(progress));
                                    }
                                    @Override
                                    public void onSuccess(String requestId, Map resultData) {
                                        // your code here
                                        Log.d("Image","Subida con exito");
                                        String url= MediaManager.get().url().generate("imgLibertiApp/"+nameimg+".jpg");
                                        getSendAllMessageJson(hashConversacion(), U1, U1, U2, U2, U1, U2,url);
                                    }
                                    @Override
                                    public void onError(String requestId, ErrorInfo error) {
                                        // your code here
                                        Log.d("Image","Error al subir intente de nuevo");
                                    }
                                    @Override
                                    public void onReschedule(String requestId, ErrorInfo error) {
                                        // your code here
                                        Log.d("Image","no se");
                                    }})
                                .dispatch();



                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //mSetImage.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    Log.e("Uploading", path.getPath());
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            path, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);

                    cursor.close();
                    Log.d("Uploading file: %s", filePath);
                    Bitmap name=BitmapFactory.decodeFile(filePath);
                    Log.d("Nombre", String.valueOf(name));


                    Log.e("FILEPATH",filePath);
                    Log.e("FilepathColumn",String.valueOf(filePathColumn));

                    try {
                        // String requestId = MediaManager.get ().upload (filePath) .dispatch ();
                        // String requestId3 = MediaManager.get (). upload (filePath).unsigned ( " sample_preset " ).dispatch ();
                        final String nameimg="img-"+nameConcatImg();
                        String requestId = MediaManager.get().upload(filePath)
                                .option("rename","pruebaLiberti")
                                .option("public_id",nameimg)
                                .option("folder","imgLibertiApp")
                                .callback(new UploadCallback() {
                                    @Override
                                    public void onStart(String requestId) {
                                        // your code here
                                        Log.d("Image","Cargando....");
                                    }
                                    @Override
                                    public void onProgress(String requestId, long bytes, long totalBytes) {
                                        // example code starts here
                                        Double progress = (double) bytes/totalBytes;
                                        // post progress to app UI (e.g. progress bar, notification)
                                        // example code ends here
                                        Log.d("Progreso",String.valueOf(progress));
                                    }
                                    @Override
                                    public void onSuccess(String requestId, Map resultData) {
                                        // your code here
                                        Log.d("Image","Subida con exito");
                                        String url= MediaManager.get().url().generate("imgLibertiApp/"+nameimg+".jpg");
                                        getSendAllMessageJson(hashConversacion(), U1, U1, U2, U2, U1, U2,url);
                                      /*  items.add(new myImage(url,"timeCreate"));
                                        mRecicler.setHasFixedSize(true);
                                        layoutManager = new LinearLayoutManager(getApplicationContext());
                                        ((LinearLayoutManager) layoutManager).setReverseLayout(false);
                                        adapter = new HetAdapter(items);
                                        mRecicler.setLayoutManager(layoutManager);
                                        mRecicler.setAdapter(adapter);
                                        mRecicler.smoothScrollToPosition(adapter.getItemCount());*/

                                    }
                                    @Override
                                    public void onError(String requestId, ErrorInfo error) {
                                        // your code here
                                        Log.d("Image","Error al subir intente de nuevo");
                                    }
                                    @Override
                                    public void onReschedule(String requestId, ErrorInfo error) {
                                        // your code here
                                        Log.d("Image","no se");
                                    }})
                                .dispatch();



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 //   mSetImage.setImageURI(path);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ChatRoomWithUserActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomWithUserActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("zender",exito2);
        mSocket.off("rAllChat",chatCompleto);


    }

    public String nameConcatImg(){
        Calendar calendarNow = new GregorianCalendar(TimeZone.getTimeZone("America/Mexico_City"));
        String currentDateTimeString=new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(calendarNow.getInstance().getTime());//checar fecha
       return U1+currentDateTimeString;
    }
}


