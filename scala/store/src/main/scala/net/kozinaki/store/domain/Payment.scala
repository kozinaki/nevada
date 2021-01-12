package net.kozinaki.store.domain

import net.kozinaki.store.domain.User.UserId
import squants.market.Money

case class Payment(id: UserId, total: Money, card: Card)
