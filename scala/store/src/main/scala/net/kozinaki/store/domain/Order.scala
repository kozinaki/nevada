package net.kozinaki.store.domain

import java.util.UUID

import io.estatico.newtype.macros.newtype
import net.kozinaki.store.domain.CartItem._
import net.kozinaki.store.domain.Item._
import net.kozinaki.store.domain.Order._
import squants.market.Money

case class Order(id: OrderId, pid: PaymentId, items: Map[ItemId, Quantity], total: Money)

object Order {
  @newtype case class OrderId(uuid: UUID)
  @newtype case class PaymentId(uuid: UUID)
}
