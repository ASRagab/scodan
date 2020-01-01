package com.twelveHart.scodan

import cats.Monad
import cats.implicits._
import com.twelveHart.scodan.api.streaming.{ScodanStreamingEndpoints, StreamingApi}
import sttp.client.{NothingT, Response, SttpBackend}
import sttp.model.Header

class ScodanStreamingApi[F[_]: Monad, S](apiKey: String, headers: List[Header] = List.empty[Header])(
  implicit override val backend: SttpBackend[F, S, NothingT]
) extends StreamingApi[F, S]
  with ScodanStreamingEndpoints {
  import SttpImplicits._

  protected def defaultHeaders: List[Header] = headers
  protected val baseUri: String              = "https://stream.shodan.io"

  def getBanners(
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, S]]] =
    get(bannersEndpoint(requestApiKey getOrElse apiKey)).map(_.convert())

}
