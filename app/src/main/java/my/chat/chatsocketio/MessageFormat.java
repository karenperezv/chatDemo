package my.chat.chatsocketio;

public class MessageFormat {
    private String Username;
    private String Message;
    private String fecha;
    private String foto;

    public  MessageFormat(String Username,String Message,String fecha){

    }
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
