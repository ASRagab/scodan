package com.twelveHart.scodan.api.streaming

import java.nio.ByteBuffer

import fs2.Stream
import sttp.client.{Response, SttpBackend, basicRequest, _}
import sttp.model.{Header, Uri}

private[scodan] trait StreamingApi[F[_], S] {
  implicit def backend: SttpBackend[F, S, NothingT]

  protected def defaultHeaders: List[Header]

  protected def get(uri: Uri): F[Response[Either[String, S]]] =
    basicRequest.headers(defaultHeaders: _*).get(uri).response(asStream[S]).send()

}
