package dirinle.com.models

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class FormattingResponse(text: String)

object FormattingResponse{
  implicit val encoder: Encoder.AsObject[FormattingResponse] = deriveEncoder[FormattingResponse]
}
