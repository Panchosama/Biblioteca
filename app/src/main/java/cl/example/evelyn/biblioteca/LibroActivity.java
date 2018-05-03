package cl.example.evelyn.biblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LibroActivity extends AppCompatActivity {
    private TextView titulo;
    private TextView autor;
    private TextView fecha;
    private TextView editor;
    private TextView desc;
    private TextView pag;
    private Switch prestado;
    private EditText descripcion;
    private Button btnGuardar;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);

        titulo=(TextView)findViewById(R.id.txtTitulo);
        autor=(TextView)findViewById(R.id.txtAutor);
        fecha=(TextView)findViewById(R.id.txtFecha);
        editor=(TextView)findViewById(R.id.txtEditor);
        desc=(TextView)findViewById(R.id.txtDesc);
        pag=(TextView)findViewById(R.id.txtPaginas);
        prestado=(Switch) findViewById(R.id.prestado);
        descripcion=(EditText) findViewById(R.id.txtDescripcionEstado);
        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        btnVolver=(Button) findViewById(R.id.btnVolver);

        final LibroBD  conexion = new LibroBD(this,"ConexionBD",null, 1);

        Bundle b= getIntent().getExtras();



        titulo.setText(b.getString("titulo"));
        autor.append(" "+b.getString("autor"));
        fecha.append(" "+ b.getString("fecha"));
        editor.append(" "+ b.getString("editorial"));
        pag.append(" "+ b.getString("pag"));
        desc.append(" "+b.getString("desc"));
        prestado.setChecked(b.getBoolean("estado"));
        descripcion.setText(b.getString("descripcionestado"));
        final String id = b.getString("id");
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje;

                    mensaje = conexion.actualizarEstado(prestado.isChecked(),descripcion.getText().toString(),id);
                    Toast.makeText(LibroActivity.this,mensaje,Toast.LENGTH_SHORT).show();



            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mensaje = new Intent(LibroActivity.this, ListarActivity.class);
                Bundle bundle = new Bundle();
                mensaje.putExtras(bundle);
                startActivity(mensaje);
            }
        });

    }
}
