package com.hqumath.androidmvp.bean;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: ProductListResponse
 * 作    者: Created by gyd
 * 创建时间: 2019/2/14 15:48
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ProductListResponse {
    private List<ProductInfo> subscribeProductBo;

    public List<ProductInfo> getSubscribeProductBo() {
        return subscribeProductBo;
    }

    public void setSubscribeProductBo(List<ProductInfo> subscribeProductBo) {
        this.subscribeProductBo = subscribeProductBo;
    }
}
