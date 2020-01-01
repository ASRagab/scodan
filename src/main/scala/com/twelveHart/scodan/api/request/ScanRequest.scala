package com.twelveHart.scodan.api.request

// ips:[String] A comma-separated list of IPs or netblocks (in CIDR notation) that should get crawled.
case class ScanRequest(ips: String)
