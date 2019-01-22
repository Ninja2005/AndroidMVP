package com.hqumath.androidmvp.net.service;


import com.hqumath.androidmvp.bean.BaseResponse;
import com.hqumath.androidmvp.bean.DemoEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 *
 */

public interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<DemoEntity>> demoGet();
}