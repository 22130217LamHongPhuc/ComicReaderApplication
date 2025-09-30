package admin.example.apptruyentranh.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Comment;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.model.ResponeCategory;
import admin.example.apptruyentranh.model.ResponeChapter;
import admin.example.apptruyentranh.repository.CategoryRepository;
import admin.example.apptruyentranh.repository.ComicsesRepository;
import admin.example.apptruyentranh.ui.SplashActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterChapter;

public class ComicsesViewModel extends ViewModel {

    ComicsesRepository comicsesRepository=new ComicsesRepository();
    CategoryRepository categoryRepository =new CategoryRepository();



    public ComicsesViewModel(){


      if(!SplashActivity.isLoadData){
          comicsesRepository.feachListComics(1,"Finish");
          comicsesRepository.feachListComics(1,"Update");
          comicsesRepository.feachListComics(1,"Favorite");
          Log.d("model11","null");

         categoryRepository.fetchComicsCategory("action",1);
          categoryRepository.fetchListComicsAnotherCategory("anime",1);
          categoryRepository.fetchListComicsAnotherCategory("co-dai",1);
          categoryRepository.fetchListComicsAnotherCategory("ngon-tinh",1);
          categoryRepository.fetchListComicsAnotherCategory("webtoon",1);

      }else{
          Log.d("model11","noonull");

          categoryRepository.fetchCategory();
      }

        SplashActivity.isLoadData=true;
    }

    public MutableLiveData<Comics> getListComicsFinish(){
        return comicsesRepository.getLivedataFinish();
    }
    public MutableLiveData<Comics> getListComicsFavorite(){
        return comicsesRepository.getLivedataFavorite();
    }

    public MutableLiveData<Comics> getListComicsUpdate(){
        return comicsesRepository.getLivedataUpdate();
    }
    public MutableLiveData<ResponeCategory> getListCategory (){
        return categoryRepository.getLiDCategory();
    }
    public MutableLiveData<Comics> getListComicsCategory (){
        return categoryRepository.getLiDComicsCategory();
    }


    public void searchComicsToCategory(String slug,int page) {
        categoryRepository.fetchComicsCategory(slug,page);
    }

    public void searchGetComics (String slug) {
        Log.d("slug2",slug);

        comicsesRepository.feachComics(slug);
    }


    public MutableLiveData<Comics> getComics (){
        return comicsesRepository.getLivedataComics();
    }


    public void searchContentChapter(String chapterApiData, AdapterChapter.DataLoadedCallback callback) {
        comicsesRepository.feachContentComics(chapterApiData,callback);

    }

    public MutableLiveData<ResponeChapter> getContentChapter(){
        return comicsesRepository.getLivedataContentComics();
    }


    public void searchComics(String query, int page) {
        Log.d("FragmentSearch","startREQUEST");

        comicsesRepository.feachSearchComics(query,page);
    }
    public MutableLiveData<Comics> getSearchComis(){
       return comicsesRepository.getLidataListSearchComics();
    }

    public MutableLiveData<Boolean> getStatusHeart(String id,String idUser){
        comicsesRepository.fetchStateHeart(id,idUser);

        return comicsesRepository.getLidataHeart(id);
    }

    public void setcomics() {
        comicsesRepository.setComics();
    }

    public void clickHeart(String id,String idUser) {
        comicsesRepository.clickHeart(id,idUser);
    }

    public void setHeart() {
        comicsesRepository.setHeart();
    }

    public void writeComment(Comment comment) {
        comicsesRepository.writeComment(comment);
    }

    public void loadCmt(String id){
        comicsesRepository.feachComment(id);
    }

    public MutableLiveData<List<Comment>> getComment() {

       return comicsesRepository.getLivedataCmt();
    }

    public void setCmt() {
        comicsesRepository.setCmt();
    }
    public void updateViewAndLike(Item item,boolean isView) {

        comicsesRepository.updateViewAndLike(item,isView);
    }

    public  MutableLiveData<List<Item>> getTopComics(){
        comicsesRepository.listTopComics();
        return comicsesRepository.getLiDataTopItems();

    }

    public void getListStateComics(int page,String state){
        comicsesRepository.feachListComics(page,state);
    }

    public  MutableLiveData<List<Comics>> getListComicsAnotherCategory(){

        return categoryRepository.getLiDListComicsAnotherCategory();

    }


    public void setLiDataComicsToCategory() {
        categoryRepository.setLiDataComicsToCategory();
    }
}
