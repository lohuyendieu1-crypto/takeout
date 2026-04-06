package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 訂單
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /**
     * 訂單狀態 1待付款 2待接單 3已接單 4派送中 5已完成 6已取消
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    /**
     * 支付狀態 0未支付 1已支付 2退款
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    // 訂單號
    private String number;

    // 訂單狀態 1待付款 2待接單 3已接單 4派送中 5已完成 6已取消 7退款
    private Integer status;

    // 下單用戶id
    private Long userId;

    // 地址id
    private Long addressBookId;

    // 下單時間
    private LocalDateTime orderTime;

    // 結帳時間
    private LocalDateTime checkoutTime;

    // 支付方式 1微信，2支付寶
    private Integer payMethod;

    // 支付狀態 0未支付 1已支付 2退款
    private Integer payStatus;

    // 實收金額
    private BigDecimal amount;

    // 備注
    private String remark;

    // 用戶名
    private String userName;

    // 手機號
    private String phone;

    // 地址
    private String address;

    // 收貨人
    private String consignee;

    // 訂單取消原因
    private String cancelReason;

    // 訂單拒絕原因
    private String rejectionReason;

    // 訂單取消時間
    private LocalDateTime cancelTime;

    // 預計送達時間
    private LocalDateTime estimatedDeliveryTime;

    // 配送狀態  1立即送出  0選擇具體時間
    private Integer deliveryStatus;

    // 送達時間
    private LocalDateTime deliveryTime;

    // 打包費
    private int packAmount;

    // 餐具數量
    private int tablewareNumber;

    // 餐具數量狀態  1按餐量提供  0選擇具體數量
    private Integer tablewareStatus;
}