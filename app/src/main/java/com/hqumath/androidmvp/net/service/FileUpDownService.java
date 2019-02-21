package com.hqumath.androidmvp.net.service;

import com.hqumath.androidmvp.bean.BaseResultEntity;
import com.hqumath.androidmvp.bean.LoginResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.*;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: LoginService
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 17:11
 * 文件描述: 登录接口
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface FileUpDownService {
    //文件上传
    @Multipart
    @POST("ZS0100093?appKey=mobile")
    Observable<BaseResultEntity> uploadFile(@Part MultipartBody.Part img);

}
