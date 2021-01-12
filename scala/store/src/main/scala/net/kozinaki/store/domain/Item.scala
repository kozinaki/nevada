package net.kozinaki.store.domain

import java.util.UUID

import io.estatico.newtype.macros.newtype
import net.kozinaki.store.domain.Brand.BrandId
import net.kozinaki.store.domain.Category.CategoryId
import net.kozinaki.store.domain.Item.{ItemDescription, ItemId, ItemName}
import squants.market.Money

case class Item(uuid: ItemId, name: ItemName, description: ItemDescription, price: Money, brand: Brand, category: Category)

case class CreateItem(name: ItemName, description: ItemDescription, price: Money, brandId: BrandId, categoryId: CategoryId)

case class UpdateItem(id: ItemId, price: Money)

object Item {
  @newtype case class ItemId(value: UUID)
  @newtype case class ItemName(value: String)
  @newtype case class ItemDescription(value: String)
}