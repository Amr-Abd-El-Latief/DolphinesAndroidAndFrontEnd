package net.hopesun.dolphins.integration;

import net.hopesun.dolphins.models.MoreDetails;
import net.hopesun.dolphins.models.MoreDetailsRes;
import net.hopesun.dolphins.models.UploadImageRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ahmedabd-elbaky on 5/13/17.
 */

public interface WebServices {
    @POST("uploadImage")
    Call<UploadImageRes> uploadImage(@Body PostImage postImage);

    @POST("moreDetails")
    Call<MoreDetailsRes> moreDetails(@Body MoreDetails moreDetails);
}
