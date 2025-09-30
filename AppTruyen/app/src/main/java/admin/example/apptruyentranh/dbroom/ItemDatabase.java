package admin.example.apptruyentranh.dbroom;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import admin.example.apptruyentranh.model.Item;

@Database(entities = {ItemDAO.class}, version = 2)

public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase db;
    static final String NAMEDATABASE = "item.db";
    static Migration migration1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL(" ALTER TABLE Item ADD COLUMN time TEXT ");
            supportSQLiteDatabase.execSQL("ALTER TABLE Item ADD COLUMN viewer INTEGER NOT NULL DEFAULT 0");
            supportSQLiteDatabase.execSQL("ALTER TABLE Item ADD COLUMN heart INTEGER NOT NULL DEFAULT 0");
        }
    };


    public abstract DAO ItemDao();

    public static ItemDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, NAMEDATABASE).
                    allowMainThreadQueries()
                    .addMigrations(migration1_2)
                    .build();
        }

        return db;
    }

    public static void addItemIntoDatabase(Item item, Context context, String type) {
        boolean exist = ItemDatabase.getInstance(context).ItemDao().isComicsExists(item.getId());
        if (!exist) {
            ItemDAO itemDAO = null;
            if (type.equalsIgnoreCase("Readed")) {
                itemDAO = new ItemDAO(item.getId(), item.getName(), item.getUpdatedAt(), item.getThumbUrl(), item.getSlug(), item.getChapters().get(0).getServerData().size(), type,String.valueOf(System.currentTimeMillis()),true, false);
            } else {
                itemDAO = new ItemDAO(item.getId(), item.getName(), item.getUpdatedAt(), item.getThumbUrl(), item.getSlug(), item.getChapters().get(0).getServerData().size(), type,String.valueOf(System.currentTimeMillis()), false, true);
            }
            ItemDatabase.getInstance(context).ItemDao().insertItem(itemDAO);


            Log.d("listdao", ItemDatabase.getInstance(context).ItemDao().getListComicss("Readed").size() + "");
            Log.d("listdao", ItemDatabase.getInstance(context).ItemDao().getListComicss("Favorite").size() + "");

        } else {


            ItemDAO itemDAO = ItemDatabase.getInstance(context).ItemDao().getComics(item.getId());
            if (type.equalsIgnoreCase("Readed")) {
                if (!itemDAO.isViewer()) {
                    itemDAO.setViewer(true);
                    ItemDatabase.getInstance(context).ItemDao().updateComics(itemDAO);
                }
            } else {
                if (!itemDAO.isHeart()) {
                    itemDAO.setHeart(true);
                    ItemDatabase.getInstance(context).ItemDao().updateComics(itemDAO);
                }

            }


        }
    }


}
