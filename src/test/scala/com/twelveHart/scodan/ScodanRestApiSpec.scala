package com.twelveHart.scodan

import cats.Id
import fixtures.RestApiFixtures
import sttp.client.testing.SttpBackendStub
import sttp.model.Method

class ScodanRestApiSpec extends BaseApiSpec {

  trait Fixture extends RestApiFixtures {
    implicit val backend: SttpBackendStub[Id, Nothing]
    lazy val apiKey = "test"

    lazy val client = new ScodanRestApi(apiKey)
  }

  "getHostInfo" should "return a hostInfo response" in new Fixture {
    val ip = "8.8.8.8"
    implicit override val backend =
      SttpBackendStub.synchronous
        .whenRequestMatches(req => req.method == Method.GET && req.uri.path.containsSlice(List("shodan", "host", ip)))
        .thenRespond(Right(hostInfo))

    val response = client.getHostInfo(ip)
    response.body shouldBe Symbol("right")
    response.body.map(res => res shouldBe hostInfo)
  }

}
