package my.chat.chatsocketio;

public class DataChatRead {
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

    public DataChatRead(String user2, String message) {
        this.user2 = user2;
        this.message = message;
    }
}
