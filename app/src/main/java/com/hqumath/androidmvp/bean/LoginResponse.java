package com.hqumath.androidmvp.bean;

/**
 * ****************************************************************
 * 文件名称: LoginResponse
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 17:24
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginResponse {
    private String loginAccount;//手机号
    private String certificatesId;//身份证
    private String isRealNameAuth;//是否实名认证 y n
    private String riskLevel;//风险等级 0未风评 1-5风险等级
    private String name;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getCertificatesId() {
        return certificatesId;
    }

    public void setCertificatesId(String certificatesId) {
        this.certificatesId = certificatesId;
    }

    public String getIsRealNameAuth() {
        return isRealNameAuth;
    }

    public void setIsRealNameAuth(String isRealNameAuth) {
        this.isRealNameAuth = isRealNameAuth;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
