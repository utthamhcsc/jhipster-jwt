import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order.reducer';

export const OrderDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const orderEntity = useAppSelector(state => state.order.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailsHeading">
          <Translate contentKey="myApp.order.detail.title">Order</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="myApp.order.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{orderEntity.userId}</dd>
          <dt>
            <span id="contactPersonName">
              <Translate contentKey="myApp.order.contactPersonName">Contact Person Name</Translate>
            </span>
          </dt>
          <dd>{orderEntity.contactPersonName}</dd>
          <dt>
            <span id="contactNumber">
              <Translate contentKey="myApp.order.contactNumber">Contact Number</Translate>
            </span>
          </dt>
          <dd>{orderEntity.contactNumber}</dd>
          <dt>
            <span id="alternateNumber">
              <Translate contentKey="myApp.order.alternateNumber">Alternate Number</Translate>
            </span>
          </dt>
          <dd>{orderEntity.alternateNumber}</dd>
          <dt>
            <span id="serviceCharge">
              <Translate contentKey="myApp.order.serviceCharge">Service Charge</Translate>
            </span>
          </dt>
          <dd>{orderEntity.serviceCharge}</dd>
          <dt>
            <span id="invoiceDiscount">
              <Translate contentKey="myApp.order.invoiceDiscount">Invoice Discount</Translate>
            </span>
          </dt>
          <dd>{orderEntity.invoiceDiscount}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="myApp.order.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{orderEntity.discount}</dd>
          <dt>
            <span id="isFromGlobalStore">
              <Translate contentKey="myApp.order.isFromGlobalStore">Is From Global Store</Translate>
            </span>
          </dt>
          <dd>{orderEntity.isFromGlobalStore ? 'true' : 'false'}</dd>
          <dt>
            <span id="billingAddress">
              <Translate contentKey="myApp.order.billingAddress">Billing Address</Translate>
            </span>
          </dt>
          <dd>{orderEntity.billingAddress}</dd>
          <dt>
            <span id="billingLocation">
              <Translate contentKey="myApp.order.billingLocation">Billing Location</Translate>
            </span>
          </dt>
          <dd>{orderEntity.billingLocation}</dd>
          <dt>
            <span id="delivaryAddress">
              <Translate contentKey="myApp.order.delivaryAddress">Delivary Address</Translate>
            </span>
          </dt>
          <dd>{orderEntity.delivaryAddress}</dd>
          <dt>
            <span id="delivaryLocation">
              <Translate contentKey="myApp.order.delivaryLocation">Delivary Location</Translate>
            </span>
          </dt>
          <dd>{orderEntity.delivaryLocation}</dd>
          <dt>
            <span id="details">
              <Translate contentKey="myApp.order.details">Details</Translate>
            </span>
          </dt>
          <dd>{orderEntity.details}</dd>
          <dt>
            <span id="orderStatus">
              <Translate contentKey="myApp.order.orderStatus">Order Status</Translate>
            </span>
          </dt>
          <dd>{orderEntity.orderStatus}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="myApp.order.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{orderEntity.paymentStatus}</dd>
          <dt>
            <span id="invoiceIds">
              <Translate contentKey="myApp.order.invoiceIds">Invoice Ids</Translate>
            </span>
          </dt>
          <dd>{orderEntity.invoiceIds}</dd>
          <dt>
            <span id="invoiceInfo">
              <Translate contentKey="myApp.order.invoiceInfo">Invoice Info</Translate>
            </span>
          </dt>
          <dd>{orderEntity.invoiceInfo}</dd>
          <dt>
            <span id="dueAmount">
              <Translate contentKey="myApp.order.dueAmount">Due Amount</Translate>
            </span>
          </dt>
          <dd>{orderEntity.dueAmount}</dd>
        </dl>
        <Button tag={Link} to="/order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order/${orderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderDetail;
