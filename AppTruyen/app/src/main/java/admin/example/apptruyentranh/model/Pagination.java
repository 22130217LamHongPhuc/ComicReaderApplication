package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public
class Pagination  implements Serializable , Parcelable {

    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("totalItemsPerPage")
    @Expose
    private Integer totalItemsPerPage;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("pageRanges")
    @Expose
    private Integer pageRanges;

    protected Pagination(Parcel in) {
        if (in.readByte() == 0) {
            totalItems = null;
        } else {
            totalItems = in.readInt();
        }
        if (in.readByte() == 0) {
            totalItemsPerPage = null;
        } else {
            totalItemsPerPage = in.readInt();
        }
        if (in.readByte() == 0) {
            currentPage = null;
        } else {
            currentPage = in.readInt();
        }
        if (in.readByte() == 0) {
            pageRanges = null;
        } else {
            pageRanges = in.readInt();
        }
    }

    public static final Creator<Pagination> CREATOR = new Creator<Pagination>() {
        @Override
        public Pagination createFromParcel(Parcel in) {
            return new Pagination(in);
        }

        @Override
        public Pagination[] newArray(int size) {
            return new Pagination[size];
        }
    };

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalItemsPerPage() {
        return totalItemsPerPage;
    }

    public void setTotalItemsPerPage(Integer totalItemsPerPage) {
        this.totalItemsPerPage = totalItemsPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageRanges() {
        return pageRanges;
    }

    public void setPageRanges(Integer pageRanges) {
        this.pageRanges = pageRanges;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (totalItems == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalItems);
        }
        if (totalItemsPerPage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalItemsPerPage);
        }
        if (currentPage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(currentPage);
        }
        if (pageRanges == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(pageRanges);
        }
    }
}

