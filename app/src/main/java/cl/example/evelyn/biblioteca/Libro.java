package cl.example.evelyn.biblioteca;

/**
 * Created by Manuel on 4/24/2018.
 */

public class Libro {
    private String id;
    private String titulo;
    private String autor;
    private String editor;
    private String fechaPublicacion;
    private String descripcion;
    private String paginas;
    private Boolean estado;
    private String descripcionestado;

    public Libro(String ids,String titulos,String autors,String editors,String fechaPublicacions,String descripcions,String paginass,Boolean estados,String descripcionestados  ){
        id = ids;
        titulo = titulos;
        autor = autors;
        editor = editors;
        fechaPublicacion = fechaPublicacions;
        descripcion = descripcions;
        paginas = paginass;
        estado = estados;
        descripcionestado = descripcionestados;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPaginas() {
        return paginas;
    }

    public void setPaginas(String paginas) {
        this.paginas = paginas;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getDescripcionestado() {
        return descripcionestado;
    }

    public void setDescripcionestado(String descripcionestado) {
        this.descripcionestado = descripcionestado;
    }
    @Override
    public String toString() {
        return titulo + " - AUTOR :"+ autor;
    }
}
