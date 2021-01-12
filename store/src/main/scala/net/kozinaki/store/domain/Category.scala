package net.kozinaki.store.domain

import java.util.UUID

import io.estatico.newtype.macros.newtype
import net.kozinaki.store.domain.Category.{CategoryId, CategoryName}

case class Category(uuid: CategoryId, name: CategoryName)

object Category {
  @newtype case class CategoryId(value: UUID)
  @newtype case class CategoryName(value: String)
}
