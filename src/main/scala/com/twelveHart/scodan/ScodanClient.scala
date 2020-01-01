package com.twelveHart.scodan

import cats.Monad
import sttp.client._

object ScodanClient {

  def restApi[F[_]: Monad](apiKey: String)(implicit backend: SttpBackend[F, Nothing, NothingT]) =
    new ScodanRestApi[F](apiKey)

  def streamingApi[F[_]: Monad, S](apiKey: String)(
    implicit backend: SttpBackend[F, S, NothingT]
  ) = new ScodanStreamingApi[F, S](apiKey)
}
