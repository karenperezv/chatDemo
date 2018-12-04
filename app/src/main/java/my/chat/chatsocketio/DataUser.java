package my.chat.chatsocketio;

public class DataUser {
    String mensaje;
    String hora;
    String photo;

    public DataUser(String mensaje, String hora, String photo) {
        this.mensaje = mensaje;
        this.hora = hora;
        this.photo = photo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}