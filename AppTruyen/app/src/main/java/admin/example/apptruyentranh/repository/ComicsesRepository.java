package admin.example.apptruyentranh.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import admin.example.apptruyentranh.api.ApiService;
import admin.example.apptruyentranh.api.RetrofitBase;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Comment;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.model.ResponeChapter;
import admin.example.apptruyentranh.ui.adapter.AdapterChapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicsesRepository {
    public static ComicsesRepository comicsesRepository;
    MutableLiveData<Comics> lidataComicsUpdate = new MutableLiveData<>();
    MutableLiveData<Comics> lidataComicsFinish = new MutableLiveData<>();
    MutableLiveData<Comics> lidataComicsFavorite = new MutableLiveData<>();
    MutableLiveData<Comics> lidataComics = new MutableLiveData<>();

    MutableLiveData<ResponeChapter> lidataContentComics = new MutableLiveData<>();
    MutableLiveData<Comics> lidataListSearchComics = new MutableLiveData<>();
    MutableLiveData<Boolean> lidataStateHeart = new MutableLiveData<>();
    MutableLiveData<List<Comment>> liDataComment = new MutableLiveData<>();

    MutableLiveData<List<Item>> liDataTopItems = new MutableLiveData<>();
    private ValueEventListener EventListenerCmt;
    DatabaseReference referenceCmt = FirebaseDatabase.getInstance().getReference("Comment");


    public static ComicsesRepository getInstance() {
        if (comicsesRepository == null) {
            comicsesRepository = new ComicsesRepository();
        }
        return comicsesRepository;
    }

    public ComicsesRepository() {

    }

    public void feachListComics(int page, String type) {
        MutableLiveData<Comics> listComics = new MutableLiveData<>();

        ApiService apiService = RetrofitBase.getApiServervice();
        RetrofitBase.setBaseUrlMain();
        if (type.equalsIgnoreCase("Update")) {
            apiService.getComicsUpdate(page).enqueue(new Callback<Comics>() {
                @Override
                public void onResponse(Call<Comics> call, Response<Comics> response) {


                    lidataComicsUpdate.setValue(response.body());

                    List<Item> listLoad=response.body().getData().getItems();
                    for (int i = 0; i <listLoad.size() ; i++) {
                        Log.d("sss",listLoad.get(i).getName());
                    }


                }

                @Override
                public void onFailure(Call<Comics> call, Throwable throwable) {

                }
            });
        } else if (type.equalsIgnoreCase("Finish")) {
            apiService.getComicsFinish(page).enqueue(new Callback<Comics>() {
                @Override
                public void onResponse(Call<Comics> call, Response<Comics> response) {


                    lidataComicsFinish.setValue(response.body());


                }

                @Override
                public void onFailure(Call<Comics> call, Throwable throwable) {

                }
            });

        } else if (type.equalsIgnoreCase("Favorite")) {
            apiService.getComicsFavorite(page).enqueue(new Callback<Comics>() {
                @Override
                public void onResponse(Call<Comics> call, Response<Comics> response) {


                    lidataComicsFavorite.setValue(response.body());


                }

                @Override
                public void onFailure(Call<Comics> call, Throwable throwable) {

                }
            });

        }


    }

    public void feachComics(String slug) {
        Log.d("slug3", slug);

        ApiService apiService = RetrofitBase.getApiServervice();
        apiService.getComics(slug).enqueue(new Callback<Comics>() {
            @Override
            public void onResponse(Call<Comics> call, Response<Comics> response) {
                lidataComics.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Comics> call, Throwable throwable) {

            }
        });

    }

    public MutableLiveData<Comics> getLivedataUpdate() {
        return lidataComicsUpdate;
    }

    public MutableLiveData<Comics> getLivedataFinish() {
        return lidataComicsFinish;
    }

    public MutableLiveData<Comics> getLivedataFavorite() {
        return lidataComicsFavorite;
    }


    public MutableLiveData<Comics> getLivedataComics() {
        return lidataComics;
    }

    public MutableLiveData<ResponeChapter> getLivedataContentComics() {
        return lidataContentComics;
    }


    public void feachContentComics(String chapterApiData, AdapterChapter.DataLoadedCallback callback) {
        Log.d("api", chapterApiData);

        ApiService apiService = RetrofitBase.getApiServervice();
        Log.d("apiretrofit", RetrofitBase.getBaseUrl());


        RetrofitBase.setBaseUrl(chapterApiData);


        Log.d("apiretrofit", RetrofitBase.getBaseUrl());


        apiService.getContentComics(chapterApiData).enqueue(new Callback<ResponeChapter>() {
            @Override
            public void onResponse(Call<ResponeChapter> call, Response<ResponeChapter> response) {
                lidataContentComics.setValue(response.body());
                callback.onDataLoaded();
            }

            @Override
            public void onFailure(Call<ResponeChapter> call, Throwable throwable) {

            }
        });
    }

    public void feachSearchComics(String query, int page) {
        ApiService apiService = RetrofitBase.getApiServervice();
        Log.d("apiretrofit", RetrofitBase.getBaseUrl());
        RetrofitBase.setBaseUrlMain();

        apiService.getSearchComics(query,page).enqueue(new Callback<Comics>() {
            @Override
            public void onResponse(Call<Comics> call, Response<Comics> response) {
                lidataListSearchComics.setValue(response.body());
                Log.d("FragmentSearch","startRespone");

            }

            @Override
            public void onFailure(Call<Comics> call, Throwable throwable) {

            }
        });

    }

    public void fetchStateHeart(String id,String idUser) {
        FirebaseDatabase.getInstance().getReference("Heart").child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("solan", "1");
                        boolean check = false;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Log.d("solan", check + "");
                            Log.d("solan", snap.getKey());
                            if (snap.getKey().equalsIgnoreCase(idUser)) {
                                check = true;
                                break;
                            }
                        }

                        lidataStateHeart.setValue(check);
                        Log.d("solan", check + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý lỗi ở đây nếu cần
                    }
                });
    }


    public MutableLiveData<Comics> getLidataListSearchComics() {
        return lidataListSearchComics;
    }


    public MutableLiveData<Boolean> getLidataHeart(String id) {

        return lidataStateHeart;
    }

    public void setComics() {
        lidataComics.setValue(null);
    }

    public void clickHeart(String id,String idUser) {
        FirebaseDatabase.getInstance().getReference("Heart").child(id).child(idUser).setValue(true).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            lidataStateHeart.setValue(true);
                        }
                    }
                }
        );

    }

    public void setHeart() {
        lidataStateHeart.setValue(null);

    }

    public void writeComment(Comment comment) {
        FirebaseDatabase.getInstance().getReference("Comment").child(comment.getIdCommics()).child(comment.getId()).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void feachComment(String id) {
        EventListenerCmt = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    List<Comment> list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        if (snap.getKey().equalsIgnoreCase(id)) {
                            for (DataSnapshot snapchild : snap.getChildren()) {
                                Comment comment = snapchild.getValue(Comment.class);
                                list.add(comment);
                            }
                        }


                    }


                    liDataComment.setValue(list);
                    list = null;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        referenceCmt.addValueEventListener(EventListenerCmt);
    }

    public MutableLiveData<List<Comment>> getLivedataCmt() {
        return liDataComment;
    }

    public void setCmt() {
        liDataComment.setValue(null);
        referenceCmt.removeEventListener(EventListenerCmt);
    }

    public void updateViewAndLike(Item item, boolean isView) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Item").child(item.getId());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Item itemFb = snapshot.getValue(Item.class);

                    HashMap<String, Object> map = new HashMap<>();
                    if (isView) {
                        map.put("viewer", itemFb.getViewer() + 1);
                        reference.updateChildren(map);

                    } else {
                        map.put("like", itemFb.getLike() + 1);
                        reference.updateChildren(map);
                    }

                } else {
                    item.setViewer(item.getViewer()+1);
                    reference.setValue(item);
                }
                Log.d("likeee",item.getViewer()+1+"");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


        public void listTopComics () {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Item");
            databaseReference.orderByChild("viewer").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Item> items = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Item item = snapshot.getValue(Item.class);
                        items.add(item);
                    }
                    liDataTopItems.setValue(items);

                    // Xử lý danh sách items đã lấy được
                    for (Item item : items) {
                        Log.d("Item", "Name: " + item.getName() + ", Views: " + item.getViewer());
                    }

                    items = null;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý lỗi nếu có
                    Log.e("FirebaseError", databaseError.getMessage());
                }
            });


        }


        public MutableLiveData<List<Item>> getLiDataTopItems(){
        return liDataTopItems;
        }


}
