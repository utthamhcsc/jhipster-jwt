import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IOrder {
  id?: number;
  userId?: string | null;
  contactPersonName?: string | null;
  contactNumber?: string | null;
  alternateNumber?: string | null;
  serviceCharge?: number | null;
  invoiceDiscount?: number | null;
  discount?: number | null;
  isFromGlobalStore?: boolean | null;
  billingAddress?: string | null;
  billingLocation?: string | null;
  delivaryAddress?: string | null;
  delivaryLocation?: string | null;
  details?: string | null;
  orderStatus?: OrderStatus | null;
  paymentStatus?: PaymentStatus | null;
  invoiceIds?: string | null;
  invoiceInfo?: string | null;
  dueAmount?: number | null;
  orderItems?: IOrderItem[] | null;
}

export const defaultValue: Readonly<IOrder> = {
  isFromGlobalStore: false,
};
