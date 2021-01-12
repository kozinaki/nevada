package net.kozinaki.store.domain

import java.util.UUID

import io.estatico.newtype.macros.newtype
import net.kozinaki.store.domain.User._

case class User(id: UserId, name: UserName)

object User {
  @newtype case class UserId(value: UUID)
  @newtype case class UserName(value: String)
  @newtype case class Password(value: String)
  @newtype case class JwtToken(value: String)
}
