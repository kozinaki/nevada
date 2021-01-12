package net.kozinaki.store.domain

import net.kozinaki.store.domain.CartItem.Cart
import net.kozinaki.store.domain.Item.ItemId
import net.kozinaki.store.domain.User.UserId
import squants.Quantity

trait ShoppingCart[F[_]] {
  def add(userId: UserId, itemId: ItemId, quantity: Quantity[_]): F[Unit]
  def delete(userId: UserId): F[Unit]
  def get(userId: UserId): F[CartTotal]
  def removeItem(userId: UserId, itemId: ItemId): F[Unit]
  def update(userId: UserId, cart: Cart): F[Unit]
}
