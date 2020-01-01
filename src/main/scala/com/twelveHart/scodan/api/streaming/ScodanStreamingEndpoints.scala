package com.twelveHart.scodan.api.streaming

import sttp.client._
import sttp.model.Uri

trait ScodanStreamingEndpoints {
  protected val baseUri: String

  protected def bannersEndpoint(apiKey: String) = uri"$baseUri/shodan/banners?key=$apiKey&t=json"

  protected def asnsEndpoint(asns: List[String])(apiKey: String): Uri = {
    val list = asns.mkString(",")
    uri"$baseUri/shodan/asn/$list?key=$apiKey"
  }

  protected def countriesEndpoint(countries: List[String])(apiKey: String): Uri = {
    val list = countries.mkString(",")
    uri"$baseUri/shodan/countries/$list?key=$apiKey"
  }

  protected def portsEndpoint(ports: List[Int])(apiKey: String): Uri = {
    val list = ports.mkString(",")
    uri"$baseUri/shodan/ports/$list?key=$apiKey"
  }

  protected def alertsEndpoint(apiKey: String): Uri = uri"$baseUri/shodan/alert?key=$apiKey"

  protected def alertsByIdEndpoint(alertId: String)(apiKey: String): Uri =
    uri"$baseUri/shodan/alert/$alertId?key=$apiKey"

}
