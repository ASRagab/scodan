package com.twelveHart.scodan.api.request

import com.twelveHart.scodan.api.model.Filter

// name: [String] The name to describe the network alert.
// filters: [Object] An object specifying the criteria that an alert should trigger. The only supported option at the moment is the "ip" filter.
// filters.ip: [String] A list of IPs or network ranges defined using CIDR notation.
// expires (optional): [Integer] Number of seconds that the alert should be active.

case class CreateAlertRequest(name: String, filters: Filter, expires: Int)
