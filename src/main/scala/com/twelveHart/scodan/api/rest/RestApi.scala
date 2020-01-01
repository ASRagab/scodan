package com.twelveHart.scodan.api.rest

import io.circe.{Decoder, Encoder, Error}
import sttp.client._
import sttp.client.circe._
import sttp.model.{Header, Uri}

private[scodan] trait RestApi[F[_]] {
  protected def defaultHeaders: List[Header]

  implicit def backend: SttpBackend[F, Nothing, NothingT]

  protected def get[Res: Decoder](
    uri: Uri,
  ): F[Response[Either[ResponseError[Error], Res]]] =
    basicRequest.headers(defaultHeaders: _*).get(uri).response(asJson[Res]).send()

  protected def getAsString(
    uri: Uri,
  ): F[Response[Either[String, String]]] =
    basicRequest.headers(defaultHeaders: _*).get(uri).response(asString).send()

  protected def post[Req: Encoder, Res: Decoder](uri: Uri)(
    body: Option[Req]
  ): F[Response[Either[ResponseError[Error], Res]]] =
    basicRequest.headers(defaultHeaders: _*).post(uri).body(body).response(asJson[Res]).send()

  protected def postAsString[Req: Encoder](uri: Uri)(
    body: Option[Req]
  ): F[Response[Either[String, String]]] =
    basicRequest.headers(defaultHeaders: _*).post(uri).body(body).response(asString).send()

  protected def put[Req: Encoder, Res: Decoder](uri: Uri)(
    body: Option[Req]
  ): F[Response[Either[ResponseError[Error], Res]]] =
    basicRequest.headers(defaultHeaders: _*).put(uri).body(body).response(asJson[Res]).send()

  protected def putAsString[Req: Encoder](uri: Uri)(
    body: Option[Req]
  ): F[Response[Either[String, String]]] =
    basicRequest.headers(defaultHeaders: _*).put(uri).body(body).response(asString).send()

  protected def delete(uri: Uri): F[Response[Either[String, String]]] =
    basicRequest.headers(defaultHeaders: _*).delete(uri).response(asString).send()
}
