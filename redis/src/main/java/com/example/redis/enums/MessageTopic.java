package com.example.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTopic {

    BUSINESS_LINE("业务线变更消息"),
    PAYMENT_PLATFORM_AUDIT("付款平台运营审批消息"),
    PAYMENT_RISK_CONTROL_AUDIT("付款平台风控审批消息"),
    FINANCING_REPAY_APPLY_AUDIT("融资还款申请审批消息"),
    /**
     * 资产审核后推送付款oa
     */
    PUSH_OA("付款推送oa"),

    ASSET_COMMON_SEAL("风控审核池资产审批消息"),
    INVOICE_UPLOAD("附件上传"),
    ;

    private final String desc;

    public String getTopic() {
        return "QUEUE:" + this.name();
    }
}
