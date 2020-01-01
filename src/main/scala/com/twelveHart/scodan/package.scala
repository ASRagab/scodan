package com.twelveHart

import java.nio.ByteBuffer

import cats.effect.Async
import fs2.Stream
import io.circe.Error
import sttp.client.{HttpError, Response, ResponseError}

package object scodan {
  import cats.syntax.either._

  object SttpImplicits {

    implicit class ResponseStringConverter[A](response: Response[Either[String, A]]) {

      def convert(code: Int = 0): Response[Either[ScodanError, A]] = {
        val body = response.body.leftMap(err => ScodanError(code, err))
        response.copy(body = body)
      }
    }

    implicit class ResponseConverter[A](response: Response[Either[ResponseError[Error], A]]) {

      def convert(code: Int = 0): Response[Either[ScodanError, A]] = {
        val body = response.body.leftMap {
          case HttpError(body) => ScodanError(code, body)
          case err             => ScodanError(code, err.toString)
        }

        response.copy(body = body)
      }
    }

    implicit class StreamResponseConverter[F[_]: Async](response: Response[Either[String, Stream[F, ByteBuffer]]]) {

      def convert(code: Int = 0): Response[Either[ScodanError, Stream[F, ByteBuffer]]] = {
        val body = response.body.leftMap(err => ScodanError(code, err))
        response.copy(body = body)
      }
    }

  }

  case class ScodanError(code: Int, message: String)
}
