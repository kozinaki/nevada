package net.kozinaki.store.domain

import net.kozinaki.store.domain.User.UserName

case class Card(username: UserName, number: Int, expiration: String, ccv: Int)
