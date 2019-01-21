package com.hqumath.androidmvp.net;

import java.util.HashMap;
import java.util.Map;

public class AppNetConfig {
    //API 服务器
    public static final String baseUrl_PROD = "https://api.zifae.com/zifae/api/";//生产环境
    public static final String baseUrl_PRE = "https://appapipreprd.zifae.com/appapi/api/";//预发布环境
    public static final String baseUrl_UAT = "https://appapiuat.zifae.com/appapi/api/";//uat环境
    public static final String baseUrl_DEV = "https://appapipre.zifae.com/appapi/api/";//开发环境
    public static final String baseUrl_MOCK = "https://www.easy-mock.com/mock/5c25bd17e3774d13bfda54eb/api/";//模拟环境
    public static final String baseUrl_LOCAL = "http://172.16.71.51:8881/api/";//本地环境
    public static final String baseUrl_DEFAULT = baseUrl_DEV;//默认
    public static String baseUrl = baseUrl_DEFAULT;

    //嵌入H5 资源地址
    public static final String baseHtmlUrl_PROD = "https://api.zifae.com/app-resources/";//生产环境
    public static final String baseHtmlUrl_PRE = "https://appapipreprd.zifae.com/app-resources/";//预发布环境
    public static final String baseHtmlUrl_UAT = "https://appapiuat.zifae.com/app-resources/";//uat环境
    public static final String baseHtmlUrl_DEV = "https://appapipre.zifae.com/app-resources/";//开发环境
    public static final String baseHtmlUrl_DEFAULT = baseHtmlUrl_DEV;//默认
    public static String baseHtmlUrl = baseHtmlUrl_DEFAULT;
    //嵌入H5 url （前面加上 baseHtmlUrl）
    public static final String risk_url = "riskAssessment.html";
    public static final String about_zifae_url = "aboutus.html";
    public static final String feed_back_url = "feedback.html";
    public static final String cmsb_recharge_url = "recharge.html";
    public static final String agreement_baincard_url = "certificateAuthorization.html";
    public static final String about_center_url = "aboutcenter.html";//中心概况
    public static final String about_team_url = "aboutteam.html";//管理团队
    public static final String about_chairman_url = "aboutchairman.html";//董事长致辞
    public static final String novice_enrollment_url = "noviceenrollment.html";//入会流程
    public static final String novice_buy_url = "novicebuy.html";//认购流程

    //信息公告 from 从云相
    private static final String baseAfficheUrl = "http://www.zifae.com/";//生产环境
//    private static final String baseAfficheUrl = "http://preprd.zifae.com/jeecms/";//预发布环境
//    private static final String baseAfficheUrl = "http://test.zifae.com:8082/jeecms/";//测试环境
    public static final String affiche_center_url = baseAfficheUrl + "zxgg/index.jhtml";//中心公告
    public static final String affiche_product_url = baseAfficheUrl + "cpgg/index.jhtml";//产品公告

    //协议下载地址
    private static final String baseFileUrl = "https://static.zifae.com/static-resource/file/";//生产环境
//    private static final String baseFileUrl = "https://staticpreprd.zifae.com/static-resource/file/";//预发布环境
//    private static final String baseFileUrl = "https://test.zifae.com:8443/static-resource/file/";//测试环境
    public static final String agreement_url = baseFileUrl + "arguments.pdf";
    public static final String member_management_rules_url = baseFileUrl + "memberManagementRules.pdf";


    //网络请求成功
    public static final String SUCCESS = "success";

    public static final String share_code = "245ea39a3db14c79b41ba104cd0649a6";
    public static final String login_api = "ZS0100003";//用于标识保存session
    public static final String login_show_url = "ZS3003001?appKey=mobile";//验证码图片获取

    //.....所有的项目当中接口的请求url全部配置在这里.....//
    public static Map<String, Object> getBaseMaps() {
        Map<String, Object> baseFiel = new HashMap<>();
        baseFiel.put("appKey", "mobile");
        return baseFiel;
    }
}
