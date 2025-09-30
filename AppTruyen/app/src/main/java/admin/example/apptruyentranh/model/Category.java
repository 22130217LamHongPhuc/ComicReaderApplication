package admin.example.apptruyentranh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.processing.Generated;

@JsonAdapter(Category.CategoryAdapter.class)
public class Category  implements Serializable, Parcelable {


    private String id;

    private String name;

    private String slug;

    public Category(){}


    public Category(String id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    protected Category(Parcel in) {
        id = in.readString();
        name = in.readString();
        slug = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(slug);
    }


    public static class CategoryAdapter extends TypeAdapter<Category> {
        @Override
        public void write(JsonWriter out, Category category) throws IOException {
            out.beginObject();
            out.name("id").value(category.id);
            out.name("slug").value(category.slug);
            out.name("name").value(category.name);
            out.endObject();
        }

        @Override
        public Category read(JsonReader in) throws IOException {
            in.beginObject();
            String id = null;
            String slug = null;
            String name = null;
            while (in.hasNext()) {
                String fieldName = in.nextName();
                switch (fieldName) {
                    case "id":
                    case "_id":
                        id = in.nextString();
                        break;
                    case "slug":
                        slug = in.nextString();
                        break;
                    case "name":
                        name = in.nextString();
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }
            in.endObject();
            return new Category(id, name, slug);
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}