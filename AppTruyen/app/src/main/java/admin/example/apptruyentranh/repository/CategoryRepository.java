package admin.example.apptruyentranh.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import admin.example.apptruyentranh.api.ApiService;
import admin.example.apptruyentranh.api.RetrofitBase;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.ResponeCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    MutableLiveData<ResponeCategory> liDCategory = new MutableLiveData<>();
    MutableLiveData<Comics> liDComicsCategory = new MutableLiveData<>();
    MutableLiveData<List<Comics>> liDListComicsAnotherCategory = new MutableLiveData<>();
    List<Comics> comicsList=new ArrayList<>();

    public CategoryRepository() {

    }


    public void fetchCategory() {
        ApiService apiService = RetrofitBase.getApiServervice();
        apiService.getCategory().enqueue(new Callback<
                ResponeCategory>() {
            @Override
            public void onResponse(Call<ResponeCategory> call, Response<ResponeCategory> response) {
                Log.d("catt", response.body().getlist().size() + "");
                liDCategory.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponeCategory> call, Throwable throwable) {

            }
        });



    }


    public void fetchComicsCategory(String slug, int page) {

        ApiService apiService = RetrofitBase.getApiServervice();
        apiService.getComicsCategory(slug, page).enqueue(new Callback<Comics>() {
            @Override
            public void onResponse(Call<Comics> call, Response<Comics> response) {
                liDComicsCategory.setValue(response.body());
                Log.d("setlaue", response.body().getData().getTitlePage());


                Log.d("setlaue", response.body().getData().getTitlePage());
            }

            @Override
            public void onFailure(Call<Comics> call, Throwable throwable) {

            }
        });
    }

    public void fetchListComicsAnotherCategory(String slug, int page) {

        ApiService apiService = RetrofitBase.getApiServervice();
        apiService.getComicsCategory(slug, page).enqueue(new Callback<Comics>() {
            @Override
            public void onResponse(Call<Comics> call, Response<Comics> response) {
                if(comicsList.size()<3){
                    comicsList.add(response.body());
                }else{
                    comicsList.add(response.body());
                    liDListComicsAnotherCategory.setValue(comicsList);

                }



            }

            @Override
            public void onFailure(Call<Comics> call, Throwable throwable) {

            }
        });
    }

    public MutableLiveData<ResponeCategory> getLiDCategory(){
        return liDCategory;
    }

    public MutableLiveData<Comics> getLiDComicsCategory(){
        return liDComicsCategory;
    }

    public MutableLiveData<List<Comics>> getLiDListComicsAnotherCategory() {
        return liDListComicsAnotherCategory;
    }

    public void setLiDataComicsToCategory() {
        liDComicsCategory.setValue(null);
    }
}
