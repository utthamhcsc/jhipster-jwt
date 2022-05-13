package com.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.order.domain.enumeration.OrderStatus;
import com.order.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "alternate_number")
    private String alternateNumber;

    @Column(name = "service_charge")
    private Double serviceCharge;

    @Column(name = "invoice_discount")
    private Double invoiceDiscount;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "is_from_global_store")
    private Boolean isFromGlobalStore;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "billing_location")
    private String billingLocation;

    @Column(name = "delivary_address")
    private String delivaryAddress;

    @Column(name = "delivary_location")
    private String delivaryLocation;

    @Column(name = "details")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "invoice_ids")
    private String invoiceIds;

    @Column(name = "invoice_info")
    private String invoiceInfo;

    @Column(name = "due_amount")
    private Double dueAmount;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Order userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactPersonName() {
        return this.contactPersonName;
    }

    public Order contactPersonName(String contactPersonName) {
        this.setContactPersonName(contactPersonName);
        return this;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public Order contactNumber(String contactNumber) {
        this.setContactNumber(contactNumber);
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAlternateNumber() {
        return this.alternateNumber;
    }

    public Order alternateNumber(String alternateNumber) {
        this.setAlternateNumber(alternateNumber);
        return this;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public Double getServiceCharge() {
        return this.serviceCharge;
    }

    public Order serviceCharge(Double serviceCharge) {
        this.setServiceCharge(serviceCharge);
        return this;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getInvoiceDiscount() {
        return this.invoiceDiscount;
    }

    public Order invoiceDiscount(Double invoiceDiscount) {
        this.setInvoiceDiscount(invoiceDiscount);
        return this;
    }

    public void setInvoiceDiscount(Double invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public Order discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Boolean getIsFromGlobalStore() {
        return this.isFromGlobalStore;
    }

    public Order isFromGlobalStore(Boolean isFromGlobalStore) {
        this.setIsFromGlobalStore(isFromGlobalStore);
        return this;
    }

    public void setIsFromGlobalStore(Boolean isFromGlobalStore) {
        this.isFromGlobalStore = isFromGlobalStore;
    }

    public String getBillingAddress() {
        return this.billingAddress;
    }

    public Order billingAddress(String billingAddress) {
        this.setBillingAddress(billingAddress);
        return this;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingLocation() {
        return this.billingLocation;
    }

    public Order billingLocation(String billingLocation) {
        this.setBillingLocation(billingLocation);
        return this;
    }

    public void setBillingLocation(String billingLocation) {
        this.billingLocation = billingLocation;
    }

    public String getDelivaryAddress() {
        return this.delivaryAddress;
    }

    public Order delivaryAddress(String delivaryAddress) {
        this.setDelivaryAddress(delivaryAddress);
        return this;
    }

    public void setDelivaryAddress(String delivaryAddress) {
        this.delivaryAddress = delivaryAddress;
    }

    public String getDelivaryLocation() {
        return this.delivaryLocation;
    }

    public Order delivaryLocation(String delivaryLocation) {
        this.setDelivaryLocation(delivaryLocation);
        return this;
    }

    public void setDelivaryLocation(String delivaryLocation) {
        this.delivaryLocation = delivaryLocation;
    }

    public String getDetails() {
        return this.details;
    }

    public Order details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public Order orderStatus(OrderStatus orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public Order paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getInvoiceIds() {
        return this.invoiceIds;
    }

    public Order invoiceIds(String invoiceIds) {
        this.setInvoiceIds(invoiceIds);
        return this;
    }

    public void setInvoiceIds(String invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public String getInvoiceInfo() {
        return this.invoiceInfo;
    }

    public Order invoiceInfo(String invoiceInfo) {
        this.setInvoiceInfo(invoiceInfo);
        return this;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public Double getDueAmount() {
        return this.dueAmount;
    }

    public Order dueAmount(Double dueAmount) {
        this.setDueAmount(dueAmount);
        return this;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Set<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        if (this.orderItems != null) {
            this.orderItems.forEach(i -> i.setOrder(null));
        }
        if (orderItems != null) {
            orderItems.forEach(i -> i.setOrder(this));
        }
        this.orderItems = orderItems;
    }

    public Order orderItems(Set<OrderItem> orderItems) {
        this.setOrderItems(orderItems);
        return this;
    }

    public Order addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
        return this;
    }

    public Order removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
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
