package my.chat.chatsocketio;

import android.net.Uri;

public interface Item {
    int getViewType();
}
class Item1 implements Item {
    String mensaje;
    String hora;
    String photo;

    public Item1(String mensaje, String hora, String photo) {
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

    @Override
    public int getViewType() {
        return 1;
    }
}
class Item2 implements Item {
    String username;
    String username2;
    String msg;
    String hora;
    String foto;

    public Item2(String username, String username2, String msg, String hora, String foto) {
        this.username = username;
        this.username2 = username2;
        this.msg = msg;
        this.hora = hora;
        this.foto = foto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int getViewType() {
        return 2;
    }
}