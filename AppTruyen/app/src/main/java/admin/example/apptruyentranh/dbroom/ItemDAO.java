package admin.example.apptruyentranh.dbroom;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.airbnb.lottie.L;

@Entity(tableName = "Item",primaryKeys = {"id"})
public class ItemDAO implements Parcelable {

    @NonNull
    String id;
    String name;
    String UpdateDate;

    String image;
    String slug;
    int quantityChapter;
    String time;
    String type;
    boolean viewer;
    boolean heart;


    public ItemDAO(@NonNull String id, String name, String updateDate, String image, String slug, int quantityChapter, String type,String time, boolean viewer, boolean heart) {
        this.id = id;
        this.name = name;
        UpdateDate = updateDate;
        this.image = image;
        this.slug = slug;
        this.quantityChapter = quantityChapter;
        this.time = time;
        this.type = type;
        this.viewer = viewer;
        this.heart = heart;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected ItemDAO(Parcel in) {
        id = in.readString();
        name = in.readString();
        UpdateDate = in.readString();
        image = in.readString();
        slug = in.readString();
        quantityChapter = in.readInt();
        type = in.readString();
        time=in.readString();
        viewer = in.readByte() != 0;
        heart = in.readByte() != 0;
    }

    public static final Creator<ItemDAO> CREATOR = new Creator<ItemDAO>() {
        @Override
        public ItemDAO createFromParcel(Parcel in) {
            return new ItemDAO(in);
        }

        @Override
        public ItemDAO[] newArray(int size) {
            return new ItemDAO[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getQuantityChapter() {
        return quantityChapter;
    }

    public void setQuantityChapter(int quantityChapter) {
        this.quantityChapter = quantityChapter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isViewer() {
        return viewer;
    }

    public void setViewer(boolean viewer) {
        this.viewer = viewer;
    }

    public boolean isHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }

    public ItemDAO() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(UpdateDate);
        parcel.writeString(image);
        parcel.writeString(slug);
        parcel.writeInt(quantityChapter);
        parcel.writeString(time);
        parcel.writeString(type);
        parcel.writeByte((byte) (viewer ? 1 : 0));
        parcel.writeByte((byte) (heart ? 1 : 0));
    }


}
