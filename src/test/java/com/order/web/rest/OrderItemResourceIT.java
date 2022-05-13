package com.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.order.IntegrationTest;
import com.order.domain.OrderItem;
import com.order.domain.enumeration.OrderStatus;
import com.order.domain.enumeration.PaymentStatus;
import com.order.repository.OrderItemRepository;
import com.order.service.dto.OrderItemDTO;
import com.order.service.mapper.OrderItemMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderItemResourceIT {

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_ITEM_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_ID = "AAAAAAAAAA";
    private static final String UPDATED_STORE_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Double DEFAULT_SERVICE_CHARGE = 1D;
    private static final Double UPDATED_SERVICE_CHARGE = 2D;

    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.CREATED;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.PENDING;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.HALF_PAID;

    private static final String DEFAULT_INVOICE_IDS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_INFO = "BBBBBBBBBB";

    private static final Double DEFAULT_DUE_AMOUNT = 1D;
    private static final Double UPDATED_DUE_AMOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/order-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .itemId(DEFAULT_ITEM_ID)
            .qty(DEFAULT_QTY)
            .amount(DEFAULT_AMOUNT)
            .itemInfo(DEFAULT_ITEM_INFO)
            .storeId(DEFAULT_STORE_ID)
            .discount(DEFAULT_DISCOUNT)
            .serviceCharge(DEFAULT_SERVICE_CHARGE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .invoiceIds(DEFAULT_INVOICE_IDS)
            .invoiceInfo(DEFAULT_INVOICE_INFO)
            .dueAmount(DEFAULT_DUE_AMOUNT);
        return orderItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createUpdatedEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .itemId(UPDATED_ITEM_ID)
            .qty(UPDATED_QTY)
            .amount(UPDATED_AMOUNT)
            .itemInfo(UPDATED_ITEM_INFO)
            .storeId(UPDATED_STORE_ID)
            .discount(UPDATED_DISCOUNT)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .invoiceIds(UPDATED_INVOICE_IDS)
            .invoiceInfo(UPDATED_INVOICE_INFO)
            .dueAmount(UPDATED_DUE_AMOUNT);
        return orderItem;
    }

    @BeforeEach
    public void initTest() {
        orderItem = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderItem() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();
        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);
        restOrderItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testOrderItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testOrderItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testOrderItem.getItemInfo()).isEqualTo(DEFAULT_ITEM_INFO);
        assertThat(testOrderItem.getStoreId()).isEqualTo(DEFAULT_STORE_ID);
        assertThat(testOrderItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testOrderItem.getServiceCharge()).isEqualTo(DEFAULT_SERVICE_CHARGE);
        assertThat(testOrderItem.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrderItem.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrderItem.getInvoiceIds()).isEqualTo(DEFAULT_INVOICE_IDS);
        assertThat(testOrderItem.getInvoiceInfo()).isEqualTo(DEFAULT_INVOICE_INFO);
        assertThat(testOrderItem.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void createOrderItemWithExistingId() throws Exception {
        // Create the OrderItem with an existing ID
        orderItem.setId(1L);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkItemIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setItemId(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setQty(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setAmount(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList
        restOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].itemInfo").value(hasItem(DEFAULT_ITEM_INFO)))
            .andExpect(jsonPath("$.[*].storeId").value(hasItem(DEFAULT_STORE_ID)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceCharge").value(hasItem(DEFAULT_SERVICE_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].invoiceIds").value(hasItem(DEFAULT_INVOICE_IDS)))
            .andExpect(jsonPath("$.[*].invoiceInfo").value(hasItem(DEFAULT_INVOICE_INFO)))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc
            .perform(get(ENTITY_API_URL_ID, orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderItem.getId().intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.itemInfo").value(DEFAULT_ITEM_INFO))
            .andExpect(jsonPath("$.storeId").value(DEFAULT_STORE_ID))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.serviceCharge").value(DEFAULT_SERVICE_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.invoiceIds").value(DEFAULT_INVOICE_IDS))
            .andExpect(jsonPath("$.invoiceInfo").value(DEFAULT_INVOICE_INFO))
            .andExpect(jsonPath("$.dueAmount").value(DEFAULT_DUE_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem
        OrderItem updatedOrderItem = orderItemRepository.findById(orderItem.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItem are not directly saved in db
        em.detach(updatedOrderItem);
        updatedOrderItem
            .itemId(UPDATED_ITEM_ID)
            .qty(UPDATED_QTY)
            .amount(UPDATED_AMOUNT)
            .itemInfo(UPDATED_ITEM_INFO)
            .storeId(UPDATED_STORE_ID)
            .discount(UPDATED_DISCOUNT)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .invoiceIds(UPDATED_INVOICE_IDS)
            .invoiceInfo(UPDATED_INVOICE_INFO)
            .dueAmount(UPDATED_DUE_AMOUNT);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(updatedOrderItem);

        restOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testOrderItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testOrderItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOrderItem.getItemInfo()).isEqualTo(UPDATED_ITEM_INFO);
        assertThat(testOrderItem.getStoreId()).isEqualTo(UPDATED_STORE_ID);
        assertThat(testOrderItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrderItem.getServiceCharge()).isEqualTo(UPDATED_SERVICE_CHARGE);
        assertThat(testOrderItem.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrderItem.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrderItem.getInvoiceIds()).isEqualTo(UPDATED_INVOICE_IDS);
        assertThat(testOrderItem.getInvoiceInfo()).isEqualTo(UPDATED_INVOICE_INFO);
        assertThat(testOrderItem.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderItemWithPatch() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem using partial update
        OrderItem partialUpdatedOrderItem = new OrderItem();
        partialUpdatedOrderItem.setId(orderItem.getId());

        partialUpdatedOrderItem
            .itemId(UPDATED_ITEM_ID)
            .amount(UPDATED_AMOUNT)
            .itemInfo(UPDATED_ITEM_INFO)
            .storeId(UPDATED_STORE_ID)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .invoiceIds(UPDATED_INVOICE_IDS);

        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testOrderItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testOrderItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOrderItem.getItemInfo()).isEqualTo(UPDATED_ITEM_INFO);
        assertThat(testOrderItem.getStoreId()).isEqualTo(UPDATED_STORE_ID);
        assertThat(testOrderItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testOrderItem.getServiceCharge()).isEqualTo(UPDATED_SERVICE_CHARGE);
        assertThat(testOrderItem.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrderItem.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrderItem.getInvoiceIds()).isEqualTo(UPDATED_INVOICE_IDS);
        assertThat(testOrderItem.getInvoiceInfo()).isEqualTo(DEFAULT_INVOICE_INFO);
        assertThat(testOrderItem.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateOrderItemWithPatch() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem using partial update
        OrderItem partialUpdatedOrderItem = new OrderItem();
        partialUpdatedOrderItem.setId(orderItem.getId());

        partialUpdatedOrderItem
            .itemId(UPDATED_ITEM_ID)
            .qty(UPDATED_QTY)
            .amount(UPDATED_AMOUNT)
            .itemInfo(UPDATED_ITEM_INFO)
            .storeId(UPDATED_STORE_ID)
            .discount(UPDATED_DISCOUNT)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .invoiceIds(UPDATED_INVOICE_IDS)
            .invoiceInfo(UPDATED_INVOICE_INFO)
            .dueAmount(UPDATED_DUE_AMOUNT);

        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testOrderItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testOrderItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOrderItem.getItemInfo()).isEqualTo(UPDATED_ITEM_INFO);
        assertThat(testOrderItem.getStoreId()).isEqualTo(UPDATED_STORE_ID);
        assertThat(testOrderItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrderItem.getServiceCharge()).isEqualTo(UPDATED_SERVICE_CHARGE);
        assertThat(testOrderItem.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrderItem.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrderItem.getInvoiceIds()).isEqualTo(UPDATED_INVOICE_IDS);
        assertThat(testOrderItem.getInvoiceInfo()).isEqualTo(UPDATED_INVOICE_INFO);
        assertThat(testOrderItem.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeDelete = orderItemRepository.findAll().size();

        // Delete the orderItem
        restOrderItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
