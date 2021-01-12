package net.kozinaki.store.domain

import net.kozinaki.store.domain.Category.CategoryName

trait Categories[F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[Unit]
}
