package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import javax.annotation.processing.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SeoOnPage implements Serializable, Parcelable {


    @SerializedName("updated_time")
    @Expose
    private Long updatedTime;


    protected SeoOnPage(Parcel in) {
        if (in.readByte() == 0) {
            updatedTime = null;
        } else {
            updatedTime = in.readLong();
        }
    }

    public static final Creator<SeoOnPage> CREATOR = new Creator<SeoOnPage>() {
        @Override
        public SeoOnPage createFromParcel(Parcel in) {
            return new SeoOnPage(in);
        }

        @Override
        public SeoOnPage[] newArray(int size) {
            return new SeoOnPage[size];
        }
    };

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (updatedTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(updatedTime);
        }
    }
}