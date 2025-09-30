package admin.example.apptruyentranh.dbroom;


import android.os.Parcelable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DAO {
    @Insert
    void insertItem(ItemDAO itemDAO);

    @Insert
    void insertItem2(ItemDAO itemDAO);


    @Query("SELECT EXISTS (SELECT 1 FROM Item WHERE id = :id) ")
    boolean isComicsExists(String id);


    @Query("Select * FROM Item WHERE  type=:type")
    List<ItemDAO> getListComicss(String type);

    @Query("Select * FROM Item WHERE  id=:id")
    ItemDAO getComics(String id);



    @Update
    void updateComics(ItemDAO itemDAO);

    @Query("Delete FROM Item")
    void deleteAll();
}
