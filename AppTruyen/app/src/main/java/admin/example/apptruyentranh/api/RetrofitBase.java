package admin.example.apptruyentranh.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {
    public static Retrofit retrofitCurrent;
    private static String BASE_URL = "https://otruyenapi.com/v1/api/";
    private static String BASE_URLMain = "https://otruyenapi.com/v1/api/";


    public static ApiService getApiServervice() {
        if (retrofitCurrent == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            Gson gsonBuilder = new GsonBuilder().setLenient().create();

            retrofitCurrent = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                    .build();

        }


        return retrofitCurrent.create(ApiService.class);
    }


    public static void setBaseUrl(String baseUrl) {
        RetrofitBase.BASE_URL = baseUrl;
    }

    public static String getBaseUrl() {
        return RetrofitBase.BASE_URL;
    }

    public static void setBaseUrlMain() {

        BASE_URL = BASE_URLMain;


}

}
