package my.chat.chatsocketio;

public interface ItemExistentMessage {
    int getViewType();
}

class DataMessageRead  implements ItemExistentMessage {
    String user2;
    String message;

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataMessageRead(String user2, String message) {
        this.user2 = user2;
        this.message = message;
    }

    @Override
    public int getViewType() {
        return 1;
    }
}

class DataMessageNotRead implements ItemExistentMessage {
    String user2;
    String mensaje;

    public DataMessageNotRead(String user2, String mensaje) {
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

    @Override
    public int getViewType() {
        return 2;
    }
}

class DataChatImageRead implements ItemExistentMessage {
    String user2;
    String urlImg;

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public DataChatImageRead(String user2, String urlImg) {
        this.user2 = user2;
        this.urlImg = urlImg;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    @Override
    public int getViewType() {
        return 3;
    }
}

class DataChatImage implements ItemExistentMessage {
String user2;
String urlImg;

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public DataChatImage(String user2, String urlImg) {
        this.user2 = user2;
        this.urlImg = urlImg;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    @Override
    public int getViewType() {
        return 4;
    }
}
