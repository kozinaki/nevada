package net.kozinaki.store.domain

import java.util.UUID

import io.estatico.newtype.macros.newtype
import net.kozinaki.store.domain.CartItem.Quantity
import net.kozinaki.store.domain.Item.ItemId
import squants.Money

case class CartItem(item: Item, quantity: Quantity)
case class CartTotal(items: List[CartItem], total: Money)

object CartItem {
  @newtype case class Quantity(value: Int)
  @newtype case class Cart(items: Map[ItemId, Quantity])
  @newtype case class CartId(value: UUID)
}