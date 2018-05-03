package cl.example.evelyn.biblioteca;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private EditText txtCodigo;
    private Button btnEnviar;
    private ListView lv;
    private ListAdapter adapter;
    private Button btnGuardar;
    private Button btnListar;
    private Libro libros;
    // public TextView listaCodigos;
    // Constante necesaria para el chequeo de permisos
    private static final int MY_PERMISSIONS_REQUEST_CAMERA=1;

    ArrayList<HashMap<String, String>> libroList;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       // listaCodigos=(TextView)findViewById(R.id.ultimoCodigo);
       //Invoco permisos
       checkPermiso();



       libroList = new ArrayList<>();
       lv = (ListView) findViewById(R.id.list);
       txtCodigo = (EditText) findViewById(R.id.txtCodigo);
       btnEnviar = (Button) findViewById(R.id.btnEnviar);
       btnGuardar = (Button) findViewById(R.id.btnGuardar);
       btnListar = (Button) findViewById(R.id.btnListar);
       final LibroBD  conexion = new LibroBD(this,"ConexionBD",null, 1);

       btnEnviar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               libroList = new ArrayList<>();
               //lv.setAdapter(null);
               new GetLibros().execute();
           }
       });
       btnGuardar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String mensaje;
               if(libros == null){
                   Toast.makeText(MainActivity.this,"Debe ingresar un codigo",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   mensaje = conexion.guardarLibro(libros);
                   Toast.makeText(MainActivity.this,mensaje,Toast.LENGTH_SHORT).show();

               }

           }
       });
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mensaje= new Intent(MainActivity.this,ListarActivity.class);
                startActivity(mensaje);
            }
        });


    }
    /**
     * Establece si el permiso solicitado está aprobado.
     * Si no lo está, lanza diálogo
     */
    protected void checkPermiso(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            // ¿Se debe explicar el motivo del permiso?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No se necesita explicación.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    /**
     * Recibe la respuesta de los permisos asignados
     *
     * @param requestCode Código recibido
     * @param permissions Permisos chequeados
     * @param grantResults Item de permisos recibidos
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Lanza el intent con la cámara
     * @param view
     */
    public void escanearQR(View view){
        Intent intent=new Intent(this, CodeActivity.class);
        startActivityForResult(intent,0);
    }

    /**
     * Respuesta de la cámara
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0){
            if(resultCode== CommonStatusCodes.SUCCESS){
                if (data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");

                    /**
                     * Acá podemos poner lo que queramos que se haga con el resultado!
                     * podemos también cambiar la respuesta a otro activity para que reciba el intent
                     */
                  /*  Toast.makeText(
                            this,
                            barcode.displayValue,
                            Toast.LENGTH_SHORT).show(); */
                    //this.listaCodigos.setText(barcode.displayValue);
                    this.txtCodigo.setText(barcode.displayValue);

                }else{
                    Toast.makeText(
                            this,
                            "No se encontró código",
                            Toast.LENGTH_SHORT).show();

                }
            }else{
                super.onActivityResult(requestCode,resultCode,data);
            }
        }

    }



private class GetLibros extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Se esta cargando la informacion",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+ txtCodigo.getText();
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject json = new JSONObject(jsonStr);
                    Long totalItems = json.getLong("totalItems");

                    if (totalItems > 0)
                    {
                        JSONArray items = json.getJSONArray("items");
                        String description;
                        String title;
                        String authors;
                        String publisher;
                        String publishedDate;
                        String pageCount;

                        // looping through All Contacts
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject c = items.getJSONObject(i);
                            JSONObject volumeInfo = c.getJSONObject("volumeInfo");
                            try {
                                title = volumeInfo.getString("title");
                            }catch (Exception e){
                                title ="Sin título";
                            }
                            try{
                                authors = volumeInfo.getString("authors");
                            }catch (Exception e){
                                authors ="Sin autor";
                            }
                            try{
                                publisher = volumeInfo.getString("publisher");
                            }catch (Exception e){
                                publisher ="Sin editor";
                            }
                            try{
                                publishedDate = volumeInfo.getString("publishedDate");
                            } catch (Exception e){
                                publishedDate ="Sin fecha de publicacion";
                            }

                            try{
                                description = volumeInfo.getString("description");
                            }catch (Exception e){
                                description ="Sin descripcion";
                            }
                            try{
                                pageCount = volumeInfo.getString("pageCount");
                            }catch (Exception e){
                                pageCount =" Sin paginas";
                            }



                            // tmp hash map for single contact
                            HashMap<String, String> libro = new HashMap<>();

                            // adding each child node to HashMap key => value

                            libro.put("title", "Titulo : " + title);
                            libro.put("authors", "Autor : " +  authors);
                            libro.put("publisher", "Editor : " +  publisher);
                            libro.put("publishedDate", "Fecha de publicacion : " +  publishedDate);
                            libro.put("description", "Descripcion : " +  description);
                            libro.put("pageCount", "Cantidad de paginas : " +  pageCount);
                            libros = new Libro("0",title,authors,publisher,publishedDate,description,pageCount,false,"");

                            // agregar libro a la BD
                            libroList.add(libro);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   btnGuardar.setVisibility(View.VISIBLE);
                                }
                            });
                        }

                    }
                    else    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "No es un código ISBN válido, o no hay información del libro",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new SimpleAdapter(MainActivity.this, libroList,
                    R.layout.activity_list_item, new String[]{ "title","authors","publisher","description","publishedDate","pageCount"},
                    new int[]{R.id.titulo, R.id.autor,R.id.editor,R.id.descripcion, R.id.fechaPublicacion,R.id.paginas});
            lv.setAdapter(adapter);
        }
    }

}
