package com.order.service.dto;

import com.order.domain.enumeration.OrderStatus;
import com.order.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.order.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    private String userId;

    private String contactPersonName;

    private String contactNumber;

    private String alternateNumber;

    private Double serviceCharge;

    private Double invoiceDiscount;

    private Double discount;

    private Boolean isFromGlobalStore;

    private String billingAddress;

    private String billingLocation;

    private String delivaryAddress;

    private String delivaryLocation;

    private String details;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private String invoiceIds;

    private String invoiceInfo;

    private Double dueAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getInvoiceDiscount() {
        return invoiceDiscount;
    }

    public void setInvoiceDiscount(Double invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Boolean getIsFromGlobalStore() {
        return isFromGlobalStore;
    }

    public void setIsFromGlobalStore(Boolean isFromGlobalStore) {
        this.isFromGlobalStore = isFromGlobalStore;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingLocation() {
        return billingLocation;
    }

    public void setBillingLocation(String billingLocation) {
        this.billingLocation = billingLocation;
    }

    public String getDelivaryAddress() {
        return delivaryAddress;
    }

    public void setDelivaryAddress(String delivaryAddress) {
        this.delivaryAddress = delivaryAddress;
    }

    public String getDelivaryLocation() {
        return delivaryLocation;
    }

    public void setDelivaryLocation(String delivaryLocation) {
        this.delivaryLocation = delivaryLocation;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(String invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public String getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", contactPersonName='" + getContactPersonName() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", alternateNumber='" + getAlternateNumber() + "'" +
            ", serviceCharge=" + getServiceCharge() +
            ", invoiceDiscount=" + getInvoiceDiscount() +
            ", discount=" + getDiscount() +
            ", isFromGlobalStore='" + getIsFromGlobalStore() + "'" +
            ", billingAddress='" + getBillingAddress() + "'" +
            ", billingLocation='" + getBillingLocation() + "'" +
            ", delivaryAddress='" + getDelivaryAddress() + "'" +
            ", delivaryLocation='" + getDelivaryLocation() + "'" +
            ", details='" + getDetails() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", invoiceIds='" + getInvoiceIds() + "'" +
            ", invoiceInfo='" + getInvoiceInfo() + "'" +
            ", dueAmount=" + getDueAmount() +
            "}";
    }
}
