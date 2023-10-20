package dirinle.com.services

import cats.effect._
import dirinle.com.models.TextToFormat.decoder
import dirinle.com.models.{FormattingResponse, TextToFormat}
import org.http4s._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.dsl.io._

class HttpService (formatterService: FormatterService) {
  implicit private val requestDecoder: EntityDecoder[IO, TextToFormat] = jsonOf[IO, TextToFormat]
  implicit private val responseEncoder: EntityEncoder[IO, FormattingResponse] = jsonEncoderOf[IO, FormattingResponse]

  val routes = HttpRoutes.of[IO] {
    case req @ POST -> Root / "format" => req.as[TextToFormat].flatMap {textToFormat =>
      formatterService.format(textToFormat) match {
        case Left(error) => BadRequest(error.msg)
        case Right(value) => Ok(value)
      }
    }
  }
}
