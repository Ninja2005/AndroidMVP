package com.hqumath.androidmvp.bean;

/**
 * ****************************************************************
 * 文件名称: ProductInfo
 * 作    者: Created by gyd
 * 创建时间: 2019/2/14 15:51
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ProductInfo {
    private String productName;//产品名称
    private String productProfit;//预期年化收益率

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductProfit() {
        return productProfit;
    }

    public void setProductProfit(String productProfit) {
        this.productProfit = productProfit;
    }
}
