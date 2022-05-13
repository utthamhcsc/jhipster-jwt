package com.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.order.IntegrationTest;
import com.order.domain.Order;
import com.order.domain.enumeration.OrderStatus;
import com.order.domain.enumeration.PaymentStatus;
import com.order.repository.OrderRepository;
import com.order.service.dto.OrderDTO;
import com.order.service.mapper.OrderMapper;
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
 * Integration tests for the {@link OrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATE_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_SERVICE_CHARGE = 1D;
    private static final Double UPDATED_SERVICE_CHARGE = 2D;

    private static final Double DEFAULT_INVOICE_DISCOUNT = 1D;
    private static final Double UPDATED_INVOICE_DISCOUNT = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Boolean DEFAULT_IS_FROM_GLOBAL_STORE = false;
    private static final Boolean UPDATED_IS_FROM_GLOBAL_STORE = true;

    private static final String DEFAULT_BILLING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVARY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVARY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVARY_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DELIVARY_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .userId(DEFAULT_USER_ID)
            .contactPersonName(DEFAULT_CONTACT_PERSON_NAME)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .alternateNumber(DEFAULT_ALTERNATE_NUMBER)
            .serviceCharge(DEFAULT_SERVICE_CHARGE)
            .invoiceDiscount(DEFAULT_INVOICE_DISCOUNT)
            .discount(DEFAULT_DISCOUNT)
            .isFromGlobalStore(DEFAULT_IS_FROM_GLOBAL_STORE)
            .billingAddress(DEFAULT_BILLING_ADDRESS)
            .billingLocation(DEFAULT_BILLING_LOCATION)
            .delivaryAddress(DEFAULT_DELIVARY_ADDRESS)
            .delivaryLocation(DEFAULT_DELIVARY_LOCATION)
            .details(DEFAULT_DETAILS)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .invoiceIds(DEFAULT_INVOICE_IDS)
            .invoiceInfo(DEFAULT_INVOICE_INFO)
            .dueAmount(DEFAULT_DUE_AMOUNT);
        return order;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .userId(UPDATED_USER_ID)
            .contactPersonName(UPDATED_CONTACT_PERSON_NAME)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .alternateNumber(UPDATED_ALTERNATE_NUMBER)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .invoiceDiscount(UPDATED_INVOICE_DISCOUNT)
            .discount(UPDATED_DISCOUNT)
            .isFromGlobalStore(UPDATED_IS_FROM_GLOBAL_STORE)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .billingLocation(UPDATED_BILLING_LOCATION)
            .delivaryAddress(UPDATED_DELIVARY_ADDRESS)
            .delivaryLocation(UPDATED_DELIVARY_LOCATION)
            .details(UPDATED_DETAILS)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .invoiceIds(UPDATED_INVOICE_IDS)
            .invoiceInfo(UPDATED_INVOICE_INFO)
            .dueAmount(UPDATED_DUE_AMOUNT);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);
        restOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testOrder.getContactPersonName()).isEqualTo(DEFAULT_CONTACT_PERSON_NAME);
        assertThat(testOrder.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testOrder.getAlternateNumber()).isEqualTo(DEFAULT_ALTERNATE_NUMBER);
        assertThat(testOrder.getServiceCharge()).isEqualTo(DEFAULT_SERVICE_CHARGE);
        assertThat(testOrder.getInvoiceDiscount()).isEqualTo(DEFAULT_INVOICE_DISCOUNT);
        assertThat(testOrder.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testOrder.getIsFromGlobalStore()).isEqualTo(DEFAULT_IS_FROM_GLOBAL_STORE);
        assertThat(testOrder.getBillingAddress()).isEqualTo(DEFAULT_BILLING_ADDRESS);
        assertThat(testOrder.getBillingLocation()).isEqualTo(DEFAULT_BILLING_LOCATION);
        assertThat(testOrder.getDelivaryAddress()).isEqualTo(DEFAULT_DELIVARY_ADDRESS);
        assertThat(testOrder.getDelivaryLocation()).isEqualTo(DEFAULT_DELIVARY_LOCATION);
        assertThat(testOrder.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrder.getInvoiceIds()).isEqualTo(DEFAULT_INVOICE_IDS);
        assertThat(testOrder.getInvoiceInfo()).isEqualTo(DEFAULT_INVOICE_INFO);
        assertThat(testOrder.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void createOrderWithExistingId() throws Exception {
        // Create the Order with an existing ID
        order.setId(1L);
        OrderDTO orderDTO = orderMapper.toDto(order);

        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].contactPersonName").value(hasItem(DEFAULT_CONTACT_PERSON_NAME)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].alternateNumber").value(hasItem(DEFAULT_ALTERNATE_NUMBER)))
            .andExpect(jsonPath("$.[*].serviceCharge").value(hasItem(DEFAULT_SERVICE_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceDiscount").value(hasItem(DEFAULT_INVOICE_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isFromGlobalStore").value(hasItem(DEFAULT_IS_FROM_GLOBAL_STORE.booleanValue())))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].billingLocation").value(hasItem(DEFAULT_BILLING_LOCATION)))
            .andExpect(jsonPath("$.[*].delivaryAddress").value(hasItem(DEFAULT_DELIVARY_ADDRESS)))
            .andExpect(jsonPath("$.[*].delivaryLocation").value(hasItem(DEFAULT_DELIVARY_LOCATION)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].invoiceIds").value(hasItem(DEFAULT_INVOICE_IDS)))
            .andExpect(jsonPath("$.[*].invoiceInfo").value(hasItem(DEFAULT_INVOICE_INFO)))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.contactPersonName").value(DEFAULT_CONTACT_PERSON_NAME))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER))
            .andExpect(jsonPath("$.alternateNumber").value(DEFAULT_ALTERNATE_NUMBER))
            .andExpect(jsonPath("$.serviceCharge").value(DEFAULT_SERVICE_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.invoiceDiscount").value(DEFAULT_INVOICE_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.isFromGlobalStore").value(DEFAULT_IS_FROM_GLOBAL_STORE.booleanValue()))
            .andExpect(jsonPath("$.billingAddress").value(DEFAULT_BILLING_ADDRESS))
            .andExpect(jsonPath("$.billingLocation").value(DEFAULT_BILLING_LOCATION))
            .andExpect(jsonPath("$.delivaryAddress").value(DEFAULT_DELIVARY_ADDRESS))
            .andExpect(jsonPath("$.delivaryLocation").value(DEFAULT_DELIVARY_LOCATION))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.invoiceIds").value(DEFAULT_INVOICE_IDS))
            .andExpect(jsonPath("$.invoiceInfo").value(DEFAULT_INVOICE_INFO))
            .andExpect(jsonPath("$.dueAmount").value(DEFAULT_DUE_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .userId(UPDATED_USER_ID)
            .contactPersonName(UPDATED_CONTACT_PERSON_NAME)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .alternateNumber(UPDATED_ALTERNATE_NUMBER)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .invoiceDiscount(UPDATED_INVOICE_DISCOUNT)
            .discount(UPDATED_DISCOUNT)
            .isFromGlobalStore(UPDATED_IS_FROM_GLOBAL_STORE)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .billingLocation(UPDATED_BILLING_LOCATION)
            .delivaryAddress(UPDATED_DELIVARY_ADDRESS)
            .delivaryLocation(UPDATED_DELIVARY_LOCATION)
            .details(UPDATED_DETAILS)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .invoiceIds(UPDATED_INVOICE_IDS)
            .invoiceInfo(UPDATED_INVOICE_INFO)
            .dueAmount(UPDATED_DUE_AMOUNT);
        OrderDTO orderDTO = orderMapper.toDto(updatedOrder);

        restOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOrder.getContactPersonName()).isEqualTo(UPDATED_CONTACT_PERSON_NAME);
        assertThat(testOrder.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testOrder.getAlternateNumber()).isEqualTo(UPDATED_ALTERNATE_NUMBER);
        assertThat(testOrder.getServiceCharge()).isEqualTo(UPDATED_SERVICE_CHARGE);
        assertThat(testOrder.getInvoiceDiscount()).isEqualTo(UPDATED_INVOICE_DISCOUNT);
        assertThat(testOrder.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrder.getIsFromGlobalStore()).isEqualTo(UPDATED_IS_FROM_GLOBAL_STORE);
        assertThat(testOrder.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testOrder.getBillingLocation()).isEqualTo(UPDATED_BILLING_LOCATION);
        assertThat(testOrder.getDelivaryAddress()).isEqualTo(UPDATED_DELIVARY_ADDRESS);
        assertThat(testOrder.getDelivaryLocation()).isEqualTo(UPDATED_DELIVARY_LOCATION);
        assertThat(testOrder.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrder.getInvoiceIds()).isEqualTo(UPDATED_INVOICE_IDS);
        assertThat(testOrder.getInvoiceInfo()).isEqualTo(UPDATED_INVOICE_INFO);
        assertThat(testOrder.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(count.incrementAndGet());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(count.incrementAndGet());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(count.incrementAndGet());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderWithPatch() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order using partial update
        Order partialUpdatedOrder = new Order();
        partialUpdatedOrder.setId(order.getId());

        partialUpdatedOrder
            .userId(UPDATED_USER_ID)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .alternateNumber(UPDATED_ALTERNATE_NUMBER)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .invoiceDiscount(UPDATED_INVOICE_DISCOUNT)
            .discount(UPDATED_DISCOUNT)
            .isFromGlobalStore(UPDATED_IS_FROM_GLOBAL_STORE)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .billingLocation(UPDATED_BILLING_LOCATION)
            .delivaryLocation(UPDATED_DELIVARY_LOCATION)
            .orderStatus(UPDATED_ORDER_STATUS);

        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrder))
            )
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOrder.getContactPersonName()).isEqualTo(DEFAULT_CONTACT_PERSON_NAME);
        assertThat(testOrder.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testOrder.getAlternateNumber()).isEqualTo(UPDATED_ALTERNATE_NUMBER);
        assertThat(testOrder.getServiceCharge()).isEqualTo(UPDATED_SERVICE_CHARGE);
        assertThat(testOrder.getInvoiceDiscount()).isEqualTo(UPDATED_INVOICE_DISCOUNT);
        assertThat(testOrder.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrder.getIsFromGlobalStore()).isEqualTo(UPDATED_IS_FROM_GLOBAL_STORE);
        assertThat(testOrder.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testOrder.getBillingLocation()).isEqualTo(UPDATED_BILLING_LOCATION);
        assertThat(testOrder.getDelivaryAddress()).isEqualTo(DEFAULT_DELIVARY_ADDRESS);
        assertThat(testOrder.getDelivaryLocation()).isEqualTo(UPDATED_DELIVARY_LOCATION);
        assertThat(testOrder.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrder.getInvoiceIds()).isEqualTo(DEFAULT_INVOICE_IDS);
        assertThat(testOrder.getInvoiceInfo()).isEqualTo(DEFAULT_INVOICE_INFO);
        assertThat(testOrder.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateOrderWithPatch() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order using partial update
        Order partialUpdatedOrder = new Order();
        partialUpdatedOrder.setId(order.getId());

        partialUpdatedOrder
            .userId(UPDATED_USER_ID)
            .contactPersonName(UPDATED_CONTACT_PERSON_NAME)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .alternateNumber(UPDATED_ALTERNATE_NUMBER)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .invoiceDiscount(UPDATED_INVOICE_DISCOUNT)
            .discount(UPDATED_DISCOUNT)
            .isFromGlobalStore(UPDATED_IS_FROM_GLOBAL_STORE)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .billingLocation(UPDATED_BILLING_LOCATION)
            .delivaryAddress(UPDATED_DELIVARY_ADDRESS)
            .delivaryLocation(UPDATED_DELIVARY_LOCATION)
            .details(UPDATED_DETAILS)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .invoiceIds(UPDATED_INVOICE_IDS)
            .invoiceInfo(UPDATED_INVOICE_INFO)
            .dueAmount(UPDATED_DUE_AMOUNT);

        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrder))
            )
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOrder.getContactPersonName()).isEqualTo(UPDATED_CONTACT_PERSON_NAME);
        assertThat(testOrder.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testOrder.getAlternateNumber()).isEqualTo(UPDATED_ALTERNATE_NUMBER);
        assertThat(testOrder.getServiceCharge()).isEqualTo(UPDATED_SERVICE_CHARGE);
        assertThat(testOrder.getInvoiceDiscount()).isEqualTo(UPDATED_INVOICE_DISCOUNT);
        assertThat(testOrder.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrder.getIsFromGlobalStore()).isEqualTo(UPDATED_IS_FROM_GLOBAL_STORE);
        assertThat(testOrder.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testOrder.getBillingLocation()).isEqualTo(UPDATED_BILLING_LOCATION);
        assertThat(testOrder.getDelivaryAddress()).isEqualTo(UPDATED_DELIVARY_ADDRESS);
        assertThat(testOrder.getDelivaryLocation()).isEqualTo(UPDATED_DELIVARY_LOCATION);
        assertThat(testOrder.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrder.getInvoiceIds()).isEqualTo(UPDATED_INVOICE_IDS);
        assertThat(testOrder.getInvoiceInfo()).isEqualTo(UPDATED_INVOICE_INFO);
        assertThat(testOrder.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(count.incrementAndGet());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(count.incrementAndGet());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(count.incrementAndGet());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, order.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
