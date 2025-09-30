package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Params  implements Serializable, Parcelable {


    @Expose
    private Pagination pagination;


    protected Params(Parcel in) {
        pagination = in.readParcelable(Pagination.class.getClassLoader());
    }

    public static final Creator<Params> CREATOR = new Creator<Params>() {
        @Override
        public Params createFromParcel(Parcel in) {
            return new Params(in);
        }

        @Override
        public Params[] newArray(int size) {
            return new Params[size];
        }
    };

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(pagination, i);
    }
}
