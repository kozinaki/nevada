package net.kozinaki.store.domain

import java.util.UUID

import io.estatico.newtype.macros.newtype
import net.kozinaki.store.domain.Brand.{BrandId, BrandName}

case class Brand(uuid: BrandId, name: BrandName)

object Brand {
  @newtype case class BrandId(value: UUID)
  @newtype case class BrandName(value: String)
}
