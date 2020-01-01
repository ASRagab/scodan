package com.twelveHart.scodan

import cats.{Functor, Monad}
import cats.implicits._
import sttp.client._
import com.twelveHart.scodan.api.request.CreateAlertRequest
import com.twelveHart.scodan.api.response.{AlertCreatedResult, HostInfo}
import com.twelveHart.scodan.api.rest.{RestApi, ScodanEndpoints, ScodanExploitsEndpoints}
import sttp.client.{NothingT, Response, SttpBackend}
import sttp.model.Header

class ScodanRestApi[F[_]: Monad](apiKey: String, headers: List[Header] = List.empty[Header])(
  implicit override val backend: SttpBackend[F, Nothing, NothingT]
) extends RestApi[F]
  with ScodanEndpoints
  with ScodanExploitsEndpoints {
  import SttpImplicits._
  import io.circe.generic.auto._

  override protected val baseUri: String     = "https://api.shodan.io"
  protected val exploitsBaseUri: String      = "https://exploits.shodan.io/api"
  protected val defaultHeaders: List[Header] = headers

  def getHostInfo(
    ip: String,
    history: Boolean = false,
    minify: Boolean = false,
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, HostInfo]]] = {
    val params = Map("history" -> history.toString, "minify" -> minify.toString)
    get[HostInfo](hostInfoEndpoint(ip, params)(requestApiKey getOrElse apiKey)).map(_.convert(1))
  }

  def getHostCount(
    query: String,
    facets: Option[String] = None,
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, String]]] =
    getAsString(hostCountEndpoint(query, facets)(requestApiKey getOrElse apiKey)).map(_.convert(1))

  def getHostSearch(
    query: String,
    facets: Option[String],
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, String]]] =
    getAsString(hostSearchEndpoint(query, facets)(requestApiKey getOrElse apiKey)).map(_.convert(1))

  def getHostSearchTokens(
    query: String,
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, String]]] =
    getAsString(hostSearchTokensEndpoint(query)(requestApiKey getOrElse apiKey)).map(_.convert(1))

  def getPorts(requestApiKey: Option[String] = None): F[Response[Either[ScodanError, List[Int]]]] =
    get[List[Int]](portsEndpoint(requestApiKey getOrElse apiKey)).map(_.convert(1))

  def getProtocols(requestApiKey: Option[String] = None): F[Response[Either[ScodanError, Map[String, String]]]] =
    get[Map[String, String]](protocolsEndpoint(requestApiKey getOrElse apiKey)).map(_.convert(1))

  def postAlert(
    req: CreateAlertRequest,
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, AlertCreatedResult]]] =
    post[CreateAlertRequest, AlertCreatedResult](createAlertEndpoint(requestApiKey getOrElse apiKey))(Some(req))
      .map(_.convert(1))

  // Exploits
  def getExploitsSearch(
    query: String,
    facets: Option[String],
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, String]]] =
    getAsString(exploitsSearchEndpoint(query, facets)(requestApiKey getOrElse apiKey)).map(_.convert(1))

  def getExploitsCount(
    query: String,
    facets: Option[String],
    requestApiKey: Option[String] = None
  ): F[Response[Either[ScodanError, String]]] =
    getAsString(exploitsCountEndpoint(query, facets)(requestApiKey getOrElse apiKey)).map(_.convert(1))

}
