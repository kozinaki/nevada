package net.kozinaki.store.domain

import net.kozinaki.store.domain.Order.PaymentId

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[PaymentId]
}
