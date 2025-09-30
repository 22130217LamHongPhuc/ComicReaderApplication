package admin.example.apptruyentranh.model;


import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import javax.annotation.processing.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Generated("jsonschema2pojo")
public class ResponeCategory implements Serializable {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private Data2 data;

public String getStatus(){
        return status;
        }

public void setStatus(String status){
        this.status=status;
        }

public String getMessage(){
        return message;
        }

public void setMessage(String message){
        this.message=message;
        }

public Data2 getData(){
        return data;
        }

public void setData(Data2 data){
        this.data=data;
        }


@Generated("jsonschema2pojo")
class Data2<T> implements Serializable{

    @SerializedName("items")
    @Expose
    private List<Category> items;

    public List<Category> getItems() {
        return items;
    }

    public void setItems(List<Category> items) {
        this.items = items;
    }

}

public List<Category> getlist(){
    return data.getItems();
}



}