package net.kozinaki.store.domain

import cats.{Monad, MonadError}
import cats.syntax.flatMap._
import cats.syntax.functor._
import net.kozinaki.store.domain.Order.OrderId
import net.kozinaki.store.domain.User.UserId

final class CheckoutProgram[F[_]: MonadError[*[_], Throwable]](paymentClient: PaymentClient[F],
                                              shoppingCart: ShoppingCart[F],
                                              orders: Orders[F]
                                         ) {

  def checkout(userId: UserId, card: Card): F[OrderId] =
    for {
      cart        <- shoppingCart.get(userId)
      paymentId   <- paymentClient.process(Payment(userId, cart.total, card))
      orderId     <- orders.create(userId, paymentId, cart.items, cart.total)
      _           <- shoppingCart.delete(userId)
    } yield orderId
}
