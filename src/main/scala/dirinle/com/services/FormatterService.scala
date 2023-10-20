package dirinle.com.services

import dirinle.com.models._

class FormatterService {
  def format(textToFormat: TextToFormat): Either[RequestError, FormattingResponse] = {
    val limit = textToFormat.limitPerLine
    val words = textToFormat.text.split("\n").flatMap(_.split(" "))
    val longestWord = words.maxByOption(_.length).getOrElse("")
    if (limit <= 0) Left(NegativeOrZeroLimit)
    else if (longestWord.length > limit) Left(TooLongWords)
    else {
      val wordsWithoutFirst = words.drop(1)
      val firstWord = words.head
      val formatted = wordsWithoutFirst.foldLeft(Seq(firstWord)) {
        case (acc, word) =>
          val alreadyInRow = acc.last
          if ((word.length + alreadyInRow.length + 1) <= limit) acc.dropRight(1) :+ (alreadyInRow + " " + word)
          else acc :+ word
      }
      Right(FormattingResponse(formatted.mkString("\n")))
    }
  }
}
