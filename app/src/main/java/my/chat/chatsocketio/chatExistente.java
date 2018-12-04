package my.chat.chatsocketio;

public class chatExistente {
    String user2;
    String mensaje;

    public chatExistente(String user2, String mensaje) {
        this.user2 = user2;
        this.mensaje = mensaje;

    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
