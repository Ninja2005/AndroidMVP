package com.hqumath.androidmvp.net.service;

import com.hqumath.androidmvp.bean.BaseResultEntity;
import com.hqumath.androidmvp.bean.LoginResponse;
import com.hqumath.androidmvp.bean.ProductListResponse;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    //产品列表
    @FormUrlEncoded
    @POST("ZS0200001")
    Observable<BaseResultEntity<ProductListResponse>> getProductList(@FieldMap Map<String, Object> maps);
}
