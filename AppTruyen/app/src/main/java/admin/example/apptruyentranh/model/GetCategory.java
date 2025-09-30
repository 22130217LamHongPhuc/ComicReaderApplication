package admin.example.apptruyentranh.model;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import admin.example.apptruyentranh.R;

public class GetCategory {



    public  static ArrayList<Category> getRandomCategory(List<Category> list) {

        ArrayList<Category> listcate = new ArrayList<>();


        Random rd = new Random();
        for (int i = 0; i < 12; i++) {
            int k = rd.nextInt(53);
            Category category = list.get(k);
            if (listcate.contains(category)) {
                i--;
            } else {
                listcate.add(category);

            }

        }

        return listcate;
    }
}

