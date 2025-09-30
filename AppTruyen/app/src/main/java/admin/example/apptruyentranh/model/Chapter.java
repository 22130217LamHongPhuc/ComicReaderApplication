package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Chapter implements Serializable,Parcelable {
    @SerializedName("server_name")
    @Expose
    private String serverName;
    @SerializedName("server_data")
    @Expose
    private List<ServerDatum> serverData;



    public Chapter(){}
    protected Chapter(Parcel in) {
        serverName = in.readString();
        serverData = in.createTypedArrayList(ServerDatum.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serverName);
        dest.writeTypedList(serverData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public List<ServerDatum> getServerData() {
        return serverData;
    }

    public void setServerData(List<ServerDatum> serverData) {
        this.serverData = serverData;
    }


}

