package com.hqumath.androidmvp.net.service;

import com.hqumath.androidmvp.bean.BaseResultEntity;
import com.hqumath.androidmvp.bean.LoginResponse;
import com.hqumath.androidmvp.bean.ProductListResponse;
import com.hqumath.androidmvp.bean.UserInfoEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import java.util.List;
import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: MainService
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 17:11
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface MainService {

    //获取用户信息
    @GET("users/{userName}")
    Observable<Response<UserInfoEntity>> getUserInfo(@Path("userName") String userName);


    //产品列表
    @FormUrlEncoded
    @POST("ZS0200001")
    Observable<BaseResultEntity<ProductListResponse>> getProductList(@FieldMap Map<String, Object> maps);

    //文件上传
    @Multipart
    @POST("ZS0100093?appKey=mobile")
    Observable<BaseResultEntity> uploadFile(@Part MultipartBody.Part img);

    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String url);
}
