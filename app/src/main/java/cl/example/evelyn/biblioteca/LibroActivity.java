package cl.example.evelyn.biblioteca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LibroActivity extends AppCompatActivity {
    private TextView titulo;
    private TextView autor;
    private TextView fecha;
    private TextView editor;
    private TextView desc;
    private TextView pag;

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

        Bundle b= getIntent().getExtras();



        titulo.setText(b.getString("titulo"));
        autor.append(" "+b.getString("autor"));
        fecha.append(" "+ b.getString("fecha"));
        editor.append(" "+ b.getString("editorial"));
        pag.append(" "+ b.getString("pag"));
        desc.append(" "+b.getString("desc"));

    }
}
