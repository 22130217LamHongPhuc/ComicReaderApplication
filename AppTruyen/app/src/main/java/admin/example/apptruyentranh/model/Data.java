package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Data implements Serializable, Parcelable {
    private String titlePage;
    @SerializedName("items")
    @Expose
    private List<Item> items;



    @SerializedName("item")
    @Expose
    private Item item;
    @SerializedName("params")
    @Expose
    private Params params;

    @SerializedName("APP_DOMAIN_FRONTEND")
    @Expose
    private String appDomainFrontend;
    @SerializedName("APP_DOMAIN_CDN_IMAGE")
    @Expose
    private String appDomainCdnImage;


    protected Data(Parcel in) {
        titlePage = in.readString();
        items = in.createTypedArrayList(Item.CREATOR);
        params = in.readParcelable(Params.class.getClassLoader());
        item=in.readParcelable(Item.class.getClassLoader());

        appDomainFrontend = in.readString();
        appDomainCdnImage = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(titlePage);

        parcel.writeTypedList(items);
        parcel.writeParcelable(params, i);
        parcel.writeParcelable(item, i);
        parcel.writeString(appDomainFrontend);
        parcel.writeString(appDomainCdnImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getTitlePage() {
        return titlePage;
    }

    public void setTitlePage(String titlePage) {
        this.titlePage = titlePage;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public String getAppDomainFrontend() {
        return appDomainFrontend;
    }

    public void setAppDomainFrontend(String appDomainFrontend) {
        this.appDomainFrontend = appDomainFrontend;
    }

    public String getAppDomainCdnImage() {
        return appDomainCdnImage;
    }

    public void setAppDomainCdnImage(String appDomainCdnImage) {
        this.appDomainCdnImage = appDomainCdnImage;
    }
    public Item getItem(){
        return item;
    }
}
