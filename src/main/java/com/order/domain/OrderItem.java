package com.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.order.domain.enumeration.OrderStatus;
import com.order.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "item_id", nullable = false)
    private String itemId;

    @NotNull
    @Column(name = "qty", nullable = false)
    private Double qty;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "item_info")
    private String itemInfo;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "service_charge")
    private Double serviceCharge;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "orderItems" }, allowSetters = true)
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return this.itemId;
    }

    public OrderItem itemId(String itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Double getQty() {
        return this.qty;
    }

    public OrderItem qty(Double qty) {
        this.setQty(qty);
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getAmount() {
        return this.amount;
    }

    public OrderItem amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getItemInfo() {
        return this.itemInfo;
    }

    public OrderItem itemInfo(String itemInfo) {
        this.setItemInfo(itemInfo);
        return this;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getStoreId() {
        return this.storeId;
    }

    public OrderItem storeId(String storeId) {
        this.setStoreId(storeId);
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public OrderItem discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getServiceCharge() {
        return this.serviceCharge;
    }

    public OrderItem serviceCharge(Double serviceCharge) {
        this.setServiceCharge(serviceCharge);
        return this;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public OrderItem orderStatus(OrderStatus orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public OrderItem paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getInvoiceIds() {
        return this.invoiceIds;
    }

    public OrderItem invoiceIds(String invoiceIds) {
        this.setInvoiceIds(invoiceIds);
        return this;
    }

    public void setInvoiceIds(String invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public String getInvoiceInfo() {
        return this.invoiceInfo;
    }

    public OrderItem invoiceInfo(String invoiceInfo) {
        this.setInvoiceInfo(invoiceInfo);
        return this;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public Double getDueAmount() {
        return this.dueAmount;
    }

    public OrderItem dueAmount(Double dueAmount) {
        this.setDueAmount(dueAmount);
        return this;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem order(Order order) {
        this.setOrder(order);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", itemId='" + getItemId() + "'" +
            ", qty=" + getQty() +
            ", amount=" + getAmount() +
            ", itemInfo='" + getItemInfo() + "'" +
            ", storeId='" + getStoreId() + "'" +
            ", discount=" + getDiscount() +
            ", serviceCharge=" + getServiceCharge() +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", invoiceIds='" + getInvoiceIds() + "'" +
            ", invoiceInfo='" + getInvoiceInfo() + "'" +
            ", dueAmount=" + getDueAmount() +
            "}";
    }
}
