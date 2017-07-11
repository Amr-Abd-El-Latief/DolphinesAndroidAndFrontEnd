package net.hopesun.dolphins.integration;

import android.util.Log;

import net.hopesun.dolphins.models.MoreDetails;
import net.hopesun.dolphins.models.MoreDetailsRes;
import net.hopesun.dolphins.models.UploadImageRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmedabd-elbaky on 5/13/17.
 */

public class Backend {
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://dolphinsbackend.mybluemix.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void uploadImage(String imageBase64, final UploadImageResults uploadImageResults){
        WebServices service = retrofit.create(WebServices.class);
        service.uploadImage(new PostImage(imageBase64)).enqueue(new Callback<UploadImageRes>() {
            @Override
            public void onResponse(Call<UploadImageRes> call, Response<UploadImageRes> response) {
                Log.d("RESULT", response.body().toString());
                uploadImageResults.res(response.body());
            }

            @Override
            public void onFailure(Call<UploadImageRes> call, Throwable t) {
                uploadImageResults.res(null);
                t.printStackTrace();
            }
        });
    }

    public static void moreDetails(MoreDetails moreDetails, final MoreDetailsResults moreDetailsResults){
        WebServices service = retrofit.create(WebServices.class);
        service.moreDetails(moreDetails).enqueue(new Callback<MoreDetailsRes>() {
            @Override
            public void onResponse(Call<MoreDetailsRes> call, Response<MoreDetailsRes> response) {
                Log.d("RESULT", response.body().toString());
                moreDetailsResults.res(response.body());
            }

            @Override
            public void onFailure(Call<MoreDetailsRes> call, Throwable t) {
                moreDetailsResults.res(null);
                t.printStackTrace();
            }
        });
    }

    public interface UploadImageResults{
        public void res(UploadImageRes uploadImageRes);
    }

    public interface MoreDetailsResults{
        public void res(MoreDetailsRes moreDetailsRes);
    }
}
