package dirinle.com.models

sealed trait RequestError{
  val msg: String
}

object NegativeOrZeroLimit extends RequestError {
  override val msg: String = "Limit should be a positive number"
}
object TooLongWords extends RequestError {
  override val msg: String = "Impossible to format text, some words have length bigger than limit"
}