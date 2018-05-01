package cl.example.evelyn.biblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {
    ArrayList<Libro> listaItems ;
    ArrayAdapter adapter;
    Button btnVolver;
    Button btnBuscar;
    ListView lista;
    EditText txtBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        btnVolver = (Button) findViewById(R.id.botonVolver);
        btnBuscar = (Button) findViewById(R.id.botonBuscar);
        final Spinner dropdown = findViewById(R.id.spinner1);
        txtBusqueda =(EditText) findViewById(R.id.txtBusqueda);

        String[] items = new String[]{"Titulo", "Autor"};

        ArrayAdapter<String> filtro = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(filtro);
        lista=(ListView)findViewById(R.id.lista);

        final LibroBD conexion = new LibroBD(
                this,
                "ConexionBD",
                null,
                1);
        //listaItems = conexion.listarLibros();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dropdown.getSelectedItem().toString().equals("Titulo")){
                    listaItems = conexion.listarPorTitulo(txtBusqueda.getText().toString());
                }
                if (dropdown.getSelectedItem().toString().equals("Año publicacion")){
                    listaItems = conexion.listarFecha(txtBusqueda.getText().toString());
                }
                if (dropdown.getSelectedItem().toString().equals("Autor")){
                    listaItems = conexion.listarAutor(txtBusqueda.getText().toString());
                }
                if(listaItems.size()==0){
                    Toast.makeText(ListarActivity.this,"No se encontraron coincidencias", Toast.LENGTH_LONG).show();
                }
                else {
                    adapter = new ArrayAdapter<Libro>(ListarActivity.this,R.layout.support_simple_spinner_dropdown_item, listaItems);
                    lista.setAdapter(adapter);
                }

            }
        });

        /**
         * item de lista a mostrar
         */
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Libro currentItem = (Libro)adapter.getItem(position);

                Intent verLibro = new Intent(ListarActivity.this, LibroActivity.class);
                Bundle bun = new Bundle();
                bun.putString("titulo", currentItem.getTitulo());
                bun.putString("autor", currentItem.getAutor());
                bun.putString("editorial", currentItem.getEditor());
                bun.putString("fecha", currentItem.getFechaPublicacion());
                bun.putString("desc",currentItem.getDescripcion());
                bun.putString("pag",currentItem.getPaginas());
                verLibro.putExtras(bun);
                startActivity(verLibro);

            }
        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mensaje = new Intent(ListarActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                mensaje.putExtras(bundle);
                startActivity(mensaje);
            }
        });

    }
}
