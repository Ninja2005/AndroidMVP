package com.hqumath.androidmvp.net.service;

import com.hqumath.androidmvp.bean.CommitEntity;
import com.hqumath.androidmvp.bean.ReposEntity;
import com.hqumath.androidmvp.bean.UserInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

    //获取用户仓库
    @GET("users/JakeWharton/repos")
    Observable<Response<List<ReposEntity>>> getMyRepos(@Query("per_page") int per_page, @Query("page") long page);

    //获取星标仓库
    @GET("users/JakeWharton/starred")
    Observable<Response<List<ReposEntity>>> getStarred(@Query("per_page") int per_page, @Query("page") long page);

    //获取被追随
    @GET("users/{userName}/followers")
    Observable<Response<List<UserInfoEntity>>> getFollowers(@Path("userName") String userName,
                                                            @Query("per_page") int per_page,
                                                            @Query("page") long page);

    //获取仓库信息
    @GET("repos/{userName}/{reposName}")
    Observable<Response<ReposEntity>> getReposInfo(@Path("userName") String userName,
                                                   @Path("reposName") String reposName);

    //获取仓库提交记录 分页
    @GET("repos/{userName}/{reposName}/commits?sha=master")
    Observable<Response<List<CommitEntity>>> getCommits(@Path("userName") String userName,
                                                        @Path("reposName") String reposName,
                                                        @Query("per_page") int per_page, @Query("page") long page);

    //文件上传
    @Multipart
    @POST("ZS0100093?appKey=mobile")
    Observable<Response> uploadFile(@Part MultipartBody.Part img);

    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String url);
}
