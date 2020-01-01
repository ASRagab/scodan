package com.twelveHart.scodan.api.response

import java.time.LocalDateTime

import com.twelveHart.scodan.api.model.{Filter, Trigger}

case class AlertCreatedResult(
  name: String,
  created: LocalDateTime,
  triggers: Trigger,
  expires: Int,
  expiration: LocalDateTime,
  filters: Filter,
  id: String,
  size: Int
)
