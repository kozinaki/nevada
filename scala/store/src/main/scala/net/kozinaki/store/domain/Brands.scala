package net.kozinaki.store.domain

import net.kozinaki.store.domain.Brand.BrandName

trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[Unit]
}
