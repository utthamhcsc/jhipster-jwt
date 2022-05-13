import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrder } from 'app/shared/model/order.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { getEntity, updateEntity, createEntity, reset } from './order.reducer';

export const OrderUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const orderEntity = useAppSelector(state => state.order.entity);
  const loading = useAppSelector(state => state.order.loading);
  const updating = useAppSelector(state => state.order.updating);
  const updateSuccess = useAppSelector(state => state.order.updateSuccess);
  const orderStatusValues = Object.keys(OrderStatus);
  const paymentStatusValues = Object.keys(PaymentStatus);
  const handleClose = () => {
    props.history.push('/order');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orderEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          orderStatus: 'CREATED',
          paymentStatus: 'PAID',
          ...orderEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myApp.order.home.createOrEditLabel" data-cy="OrderCreateUpdateHeading">
            <Translate contentKey="myApp.order.home.createOrEditLabel">Create or edit a Order</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="order-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('myApp.order.userId')} id="order-userId" name="userId" data-cy="userId" type="text" />
              <ValidatedField
                label={translate('myApp.order.contactPersonName')}
                id="order-contactPersonName"
                name="contactPersonName"
                data-cy="contactPersonName"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.contactNumber')}
                id="order-contactNumber"
                name="contactNumber"
                data-cy="contactNumber"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.alternateNumber')}
                id="order-alternateNumber"
                name="alternateNumber"
                data-cy="alternateNumber"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.serviceCharge')}
                id="order-serviceCharge"
                name="serviceCharge"
                data-cy="serviceCharge"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.invoiceDiscount')}
                id="order-invoiceDiscount"
                name="invoiceDiscount"
                data-cy="invoiceDiscount"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.discount')}
                id="order-discount"
                name="discount"
                data-cy="discount"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.isFromGlobalStore')}
                id="order-isFromGlobalStore"
                name="isFromGlobalStore"
                data-cy="isFromGlobalStore"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('myApp.order.billingAddress')}
                id="order-billingAddress"
                name="billingAddress"
                data-cy="billingAddress"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.billingLocation')}
                id="order-billingLocation"
                name="billingLocation"
                data-cy="billingLocation"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.delivaryAddress')}
                id="order-delivaryAddress"
                name="delivaryAddress"
                data-cy="delivaryAddress"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.delivaryLocation')}
                id="order-delivaryLocation"
                name="delivaryLocation"
                data-cy="delivaryLocation"
                type="text"
              />
              <ValidatedField label={translate('myApp.order.details')} id="order-details" name="details" data-cy="details" type="text" />
              <ValidatedField
                label={translate('myApp.order.orderStatus')}
                id="order-orderStatus"
                name="orderStatus"
                data-cy="orderStatus"
                type="select"
              >
                {orderStatusValues.map(orderStatus => (
                  <option value={orderStatus} key={orderStatus}>
                    {translate('myApp.OrderStatus.' + orderStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('myApp.order.paymentStatus')}
                id="order-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate('myApp.PaymentStatus.' + paymentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('myApp.order.invoiceIds')}
                id="order-invoiceIds"
                name="invoiceIds"
                data-cy="invoiceIds"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.invoiceInfo')}
                id="order-invoiceInfo"
                name="invoiceInfo"
                data-cy="invoiceInfo"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.order.dueAmount')}
                id="order-dueAmount"
                name="dueAmount"
                data-cy="dueAmount"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrderUpdate;
