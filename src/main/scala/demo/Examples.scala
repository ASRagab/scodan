package demo

import java.nio.ByteBuffer

import cats.Monad
import cats.effect.{Blocker, ExitCode, IO}
import cats.implicits._
import cats.syntax.either._
import com.twelveHart.scodan.{ScodanClient, ScodanError}
import util._
import fs2.text.utf8Encode
import io.circe.jawn.CirceSupportParser
import jawnfs2._
import org.asynchttpclient.DefaultAsyncHttpClient
import org.typelevel.jawn.AsyncParser
import sttp.client._
import sttp.client.asynchttpclient.cats.AsyncHttpClientCatsBackend
import sttp.client.asynchttpclient.fs2.AsyncHttpClientFs2Backend

import scala.concurrent.ExecutionContext

/*
 * TODO:
 *  1. Rest Client Calls from Endpoints
 *  2. Modelling Responses
 *  3. Unit Tests
 *  4. Integration Tests
 */

object AsyncExample extends App {
  val apiKey                             = sys.env("API_KEY")
  val httpClient: DefaultAsyncHttpClient = new DefaultAsyncHttpClient()
  implicit val contextShift              = IO.contextShift(ExecutionContext.global)
  implicit val backend                   = AsyncHttpClientCatsBackend.usingClient[IO](httpClient)
  val rest                               = ScodanClient.restApi[IO](apiKey)

  val request = rest.getHostInfo("8.8.8.8")
  inspectResponse(request).unsafeRunSync()
  httpClient.close()
}

object SyncExample {
  val apiKey                                                     = sys.env("API_KEY")
  implicit val backend: SttpBackend[Identity, Nothing, NothingT] = HttpURLConnectionBackend()
  val rest                                                       = ScodanClient.restApi[Identity](apiKey)

  val request = rest.getHostInfo("8.8.8.8")
  inspectResponse(request)
}

object StreamingExample extends App {
  val apiKey                = sys.env("API_KEY")
  implicit val facade       = CirceSupportParser.facade
  implicit val contextShift = IO.contextShift(ExecutionContext.global)

  val httpClient: DefaultAsyncHttpClient = new DefaultAsyncHttpClient()
  implicit val backend                   = AsyncHttpClientFs2Backend.usingClient[IO](httpClient)
  val streaming                          = ScodanClient.streamingApi[IO, fs2.Stream[IO, ByteBuffer]](apiKey)

  Blocker[IO]
    .use { blocker =>
      streaming
        .getBanners()
        .flatMap { res =>
          res.body
            .map(
              _.parseJson(AsyncParser.ValueStream)
                .map(_.toString)
                .through(utf8Encode)
                .through(fs2.io.stdout(blocker))
                .compile
                .drain
                .as(ExitCode.Success)
            )
            .fold(err => IO(println(err.message)) >> IO(ExitCode.Error), identity)
        }
    }
    .map(code => System.exit(code.code))
    .unsafeRunSync()
}

object util {

  def inspectResponse[F[_]: Monad, A](response: F[Response[Either[ScodanError, A]]]): F[Either[Unit, Unit]] =
    response.flatMap(res => implicitly[Monad[F]].pure(res.body.bimap(println, println)))
}
