package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class ServerDatum implements Serializable, Parcelable {

    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("chapter_title")
    @Expose
    private String chapterTitle;
    @SerializedName("chapter_api_data")
    @Expose
    private String chapterApiData;


    public ServerDatum(){}
    protected ServerDatum(Parcel in) {

        filename = in.readString();
        chapterName = in.readString();
        chapterTitle = in.readString();
        chapterApiData = in.readString();
    }

    public static final Creator<ServerDatum> CREATOR = new Creator<ServerDatum>() {
        @Override
        public ServerDatum createFromParcel(Parcel in) {
            return new ServerDatum(in);
        }

        @Override
        public ServerDatum[] newArray(int size) {
            return new ServerDatum[size];
        }
    };

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterApiData() {
        return chapterApiData;
    }

    public void setChapterApiData(String chapterApiData) {
        this.chapterApiData = chapterApiData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(filename);
        parcel.writeString(chapterName);
        parcel.writeString(chapterTitle);
        parcel.writeString(chapterApiData);
    }
}