package admin.example.apptruyentranh.api;
import admin.example.apptruyentranh.model.Category;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.model.ResponeCategory;
import admin.example.apptruyentranh.model.ResponeChapter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    String BASE_URL="https://otruyenapi.com/v1/api/";
    String BASE_URLIMG="https://img.otruyenapi.com/uploads/comics/";

    @GET("danh-sach/truyen-moi")
    Call<Comics> getComicsUpdate(@Query("page") int page);

    @GET("danh-sach/hoan-thanh")
    Call<Comics> getComicsFinish(@Query("page") int page);

    @GET("danh-sach/sap-ra-mat")
    Call<Comics> getComicsFavorite(@Query("page") int page);

    @GET("the-loai")
    Call<ResponeCategory> getCategory();
    @GET("the-loai/{slug}")
    Call<Comics> getComicsCategory(@Path("slug") String slug,@Query("page") int page);

    @GET("truyen-tranh/{slug}")
    Call<Comics> getComics(@Path("slug") String slug);


    @GET("tim-kiem")
    Call<Comics> getSearchComics(@Query("keyword") String keyword,@Query("page") int page);

    @GET
    Call<ResponeChapter> getContentComics(@Url String url);


}
