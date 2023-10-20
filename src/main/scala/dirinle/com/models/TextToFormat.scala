package dirinle.com.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class TextToFormat(text: String, limitPerLine: Int)

object TextToFormat{
  implicit val decoder: Decoder[TextToFormat] = deriveDecoder[TextToFormat]
}
