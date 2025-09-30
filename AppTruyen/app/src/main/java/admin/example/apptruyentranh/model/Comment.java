package admin.example.apptruyentranh.model;

public class Comment {

    String id;
    String idCommics;
    String cmt;
    String publisher;
    String imageUser;
    String userName;

    Comment(){}


    public Comment(String id, String idCommics, String cmt, String publisher, String imageUser, String userName) {
        this.id = id;
        this.idCommics = idCommics;
        this.cmt = cmt;
        this.publisher = publisher;
        this.imageUser = imageUser;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCommics() {
        return idCommics;
    }
}
