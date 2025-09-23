package com.tech_challenge_fiap.data.models;

import com.tech_challenge_fiap.utils.enums.PaymentStatusEnumEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@DynamoDbBean
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDataModel {

    private String id;
    private String qrImage;
    private String qrCode;
    private PaymentStatusEnumEntity status;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("qrImage")
    public String getQrImage() {
        return qrImage;
    }

    public void setQrImage(String qrImage) {
        this.qrImage = qrImage;
    }

    @DynamoDbAttribute("qrCode")
    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @DynamoDbAttribute("status")
    public PaymentStatusEnumEntity getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEnumEntity status) {
        this.status = status;
    }
}