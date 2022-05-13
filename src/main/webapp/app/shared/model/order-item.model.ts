import { IOrder } from 'app/shared/model/order.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IOrderItem {
  id?: number;
  itemId?: string;
  qty?: number;
  amount?: number;
  itemInfo?: string | null;
  storeId?: string | null;
  discount?: number | null;
  serviceCharge?: number | null;
  orderStatus?: OrderStatus | null;
  paymentStatus?: PaymentStatus | null;
  invoiceIds?: string | null;
  invoiceInfo?: string | null;
  dueAmount?: number | null;
  order?: IOrder | null;
}

export const defaultValue: Readonly<IOrderItem> = {};
