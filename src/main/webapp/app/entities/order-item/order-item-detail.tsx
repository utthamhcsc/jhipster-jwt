import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-item.reducer';

export const OrderItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderItemDetailsHeading">
          <Translate contentKey="myApp.orderItem.detail.title">OrderItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.id}</dd>
          <dt>
            <span id="itemId">
              <Translate contentKey="myApp.orderItem.itemId">Item Id</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.itemId}</dd>
          <dt>
            <span id="qty">
              <Translate contentKey="myApp.orderItem.qty">Qty</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.qty}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="myApp.orderItem.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.amount}</dd>
          <dt>
            <span id="itemInfo">
              <Translate contentKey="myApp.orderItem.itemInfo">Item Info</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.itemInfo}</dd>
          <dt>
            <span id="storeId">
              <Translate contentKey="myApp.orderItem.storeId">Store Id</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.storeId}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="myApp.orderItem.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.discount}</dd>
          <dt>
            <span id="serviceCharge">
              <Translate contentKey="myApp.orderItem.serviceCharge">Service Charge</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.serviceCharge}</dd>
          <dt>
            <span id="orderStatus">
              <Translate contentKey="myApp.orderItem.orderStatus">Order Status</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.orderStatus}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="myApp.orderItem.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.paymentStatus}</dd>
          <dt>
            <span id="invoiceIds">
              <Translate contentKey="myApp.orderItem.invoiceIds">Invoice Ids</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.invoiceIds}</dd>
          <dt>
            <span id="invoiceInfo">
              <Translate contentKey="myApp.orderItem.invoiceInfo">Invoice Info</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.invoiceInfo}</dd>
          <dt>
            <span id="dueAmount">
              <Translate contentKey="myApp.orderItem.dueAmount">Due Amount</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.dueAmount}</dd>
          <dt>
            <Translate contentKey="myApp.orderItem.order">Order</Translate>
          </dt>
          <dd>{orderItemEntity.order ? orderItemEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-item/${orderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderItemDetail;
