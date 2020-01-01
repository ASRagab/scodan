package com.twelveHart.scodan.api.response

case class _shodan(
  id: Option[String] = None,
  options: Option[Map[String, String]] = None,
  ptr: Option[Boolean] = None,
  module: Option[String] = None,
  crawler: Option[String]
)

case class Opts(
  raw: Option[String] = None
)

case class Location(
  city: Option[String] = None,
  region_code: Option[String] = None,
  area_code: Option[String] = None,
  longitude: Option[Double] = None,
  country_code3: Option[String] = None,
  country_name: Option[String] = None,
  postal_code: Option[String] = None,
  dma_code: Option[String] = None,
  country_code: Option[String] = None,
  latitude: Option[Double] = None
)

case class Dns(
  resolver_hostname: Option[String] = None,
  recursive: Option[Boolean] = None,
  resolver_id: Option[String] = None,
  software: Option[String] = None
)

case class Data(
  _shodan: Option[_shodan] = None,
  hash: Option[Double] = None,
  os: Option[String] = None,
  opts: Option[Opts] = None,
  ip: Option[Double] = None,
  isp: Option[String] = None,
  port: Option[Double] = None,
  hostnames: Option[List[String]] = None,
  location: Option[Location] = None,
  dns: Option[Dns] = None,
  timestamp: Option[String] = None,
  domains: Option[List[String]] = None,
  org: Option[String] = None,
  data: Option[String] = None,
  asn: Option[String] = None,
  transport: Option[String] = None,
  ip_str: Option[String] = None
)

case class HostInfo(
  ip: Double,
  city: Option[String] = None,
  region_code: Option[String] = None,
  os: Option[String] = None,
  tags: Option[List[String]] = None,
  isp: Option[String] = None,
  area_code: Option[String] = None,
  dma_code: Option[String] = None,
  last_update: Option[String] = None,
  country_code3: Option[String] = None,
  country_name: Option[String] = None,
  hostnames: Option[List[String]] = None,
  postal_code: Option[String] = None,
  longitude: Option[Double] = None,
  country_code: Option[String] = None,
  ip_str: Option[String] = None,
  latitude: Option[Double] = None,
  org: Option[String] = None,
  data: Option[List[Data]] = None,
  asn: Option[String] = None,
  ports: Option[List[Double]] = None
)
