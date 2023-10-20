package dirinle.com

import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.Port
import com.comcast.ip4s.Ipv4Address
import dirinle.com.models.{Config, ErrorMessages}
import dirinle.com.services.{FormatterService, HttpService}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import org.typelevel.log4cats.{LoggerFactory, SelfAwareStructuredLogger}
import org.typelevel.log4cats.slf4j.Slf4jFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._

object Main extends IOApp {
  implicit val logging: LoggerFactory[IO] = Slf4jFactory[IO]
  val logger: SelfAwareStructuredLogger[IO] = LoggerFactory[IO].getLogger

  val formatterService = new FormatterService()
  val httpService = new HttpService(formatterService)
  val httpApp = Router("/" -> httpService.routes).orNotFound
  val config = ConfigSource.default.load[Config]

  def runServer(host: Ipv4Address, port: Port) = EmberServerBuilder
    .default[IO]
    .withHost(host)
    .withPort(port)
    .withHttpApp(httpApp)
    .build
    .use(_ => IO.never)
    .as(ExitCode.Success)

  def run(args: List[String]): IO[ExitCode] = {
    config match {
      case Right(configuration) =>
        val resultsOfParsing = Ipv4Address.fromString(configuration.host) -> Port.fromString(configuration.port)
        resultsOfParsing match {
          case (Some(host), Some(port)) => runServer(host = host, port = port)
          case _ => logger.error(ErrorMessages.invalidValuesInConfig).map(_ => ExitCode.Error)
        }
      case _ => logger.error(ErrorMessages.invalidConfig).map(_ => ExitCode.Error)
    }
  }
}
