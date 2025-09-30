package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable, Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("author")
    @Expose
    private List<String> author;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters;
    @SerializedName("category")
    @Expose
    private List<Category> category;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    private int viewer = 0;
    private int like = 0;

    // No-argument constructor
    public Item() {
        this.chapters = new ArrayList<>(); // Initialize chapters as an empty list
    }

    public Item(String id, String name, String slug, String status, String thumbUrl, String content, List<String> author, List<Chapter> chapters, List<Category> category, String updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.status = status;
        this.thumbUrl = thumbUrl;
        this.content = content;
        this.author = author;
        this.chapters = chapters != null ? chapters : new ArrayList<>(); // Handle null chapters
        this.category = category;
        this.updatedAt = updatedAt;
    }

    public Item(String id, String name, String slug, String status, String thumbUrl, String content, List<String> author, List<Chapter> chapters, List<Category> category, String updatedAt, int viewer, int like) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.status = status;
        this.thumbUrl = thumbUrl;
        this.content = content;
        this.author = author;
        this.chapters = chapters != null ? chapters : new ArrayList<>(); // Handle null chapters
        this.category = category;
        this.updatedAt = updatedAt;
        this.viewer = viewer;
        this.like = like;
    }

    public Item(String id, String slug, String name, String thumbUrl, int viewer, int like, String updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.thumbUrl = thumbUrl;
        this.updatedAt = updatedAt;
        this.viewer = viewer;
        this.like = like;
        this.chapters = new ArrayList<>(); // Initialize chapters as an empty list
    }

    // Constructor for Parcelable
    protected Item(Parcel in) {
        id = in.readString();
        name = in.readString();
        slug = in.readString();
        status = in.readString();
        thumbUrl = in.readString();
        content = in.readString();
        author = in.createStringArrayList();
        chapters = in.createTypedArrayList(Chapter.CREATOR);
        category = in.createTypedArrayList(Category.CREATOR);
        updatedAt = in.readString();
        viewer = in.readInt();
        like = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(slug);
        parcel.writeString(status);
        parcel.writeString(thumbUrl);
        parcel.writeString(content);
        parcel.writeStringList(author);
        parcel.writeTypedList(chapters);
        parcel.writeTypedList(category);
        parcel.writeString(updatedAt);
        parcel.writeInt(viewer);
        parcel.writeInt(like);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }



    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
