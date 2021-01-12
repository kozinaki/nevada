package net.kozinaki.store.domain

import net.kozinaki.store.domain.Order.{OrderId, PaymentId}
import net.kozinaki.store.domain.User.UserId
import squants.Money

trait Orders[F[_]] {
  def get(userId: UserId, orderId: OrderId): F[Option[Order]]
  def findBy(userId: UserId): F[List[Order]]
  def create(userId: UserId, paymentId: PaymentId, items: List[CartItem], total: Money): F[OrderId]
}
