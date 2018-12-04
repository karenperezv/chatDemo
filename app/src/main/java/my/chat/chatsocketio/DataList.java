package my.chat.chatsocketio;

public class DataList {
    String username2;
    String photo;

    public DataList(String username2, String photo) {
        this.username2 = username2;
        this.photo = photo;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
