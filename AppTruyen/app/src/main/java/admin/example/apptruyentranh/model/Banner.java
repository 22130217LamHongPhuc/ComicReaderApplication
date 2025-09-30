package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Banner implements Parcelable {
    // Các thuộc tính của lớp Banner
    private String slug;
    private String image;

    // Constructor, getter, và setter


    Banner(){}





    // Các phương thức khác

    protected Banner(Parcel in) {
        slug = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(slug);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getSlug() {
        return slug;
    }
}
