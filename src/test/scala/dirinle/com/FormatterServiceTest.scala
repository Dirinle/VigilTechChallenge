package dirinle.com

import dirinle.com.models.{FormattingResponse, NegativeOrZeroLimit, TextToFormat, TooLongWords}
import dirinle.com.services.FormatterService
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FormatterServiceTest extends AnyFlatSpec with Matchers {
  val service = new FormatterService()
  it should "proper handle negative value in limit" in {
    service.format(TextToFormat("Hello Vigil", -1)) shouldBe Left(NegativeOrZeroLimit)
  }
  it should "proper handle zero value in limit" in {
    service.format(TextToFormat("Hello Vigil", 0)) shouldBe Left(NegativeOrZeroLimit)
  }
  it should "proper handle when limit is shorter that some word" in {
    service.format(TextToFormat("Hello Vigil", 3)) shouldBe Left(TooLongWords)
  }
  it should "format simple text" in {
    val textToTest = "In 1991, while studying computer science at University of Helsinki, Linus Torvalds began a project that later became the Linux kernel. He wrote the program specifically for the hardware he was using and independent of an operating system because he wanted to use the functions of his new PC with an 80386 processor. Development was done on MINIX using the GNU C Compiler."
    val expectedText =
      """In 1991, while studying computer science
        |at University of Helsinki, Linus
        |Torvalds began a project that later
        |became the Linux kernel. He wrote the
        |program specifically for the hardware he
        |was using and independent of an
        |operating system because he wanted to
        |use the functions of his new PC with an
        |80386 processor. Development was done on
        |MINIX using the GNU C Compiler.""".stripMargin
    service.format(TextToFormat(textToTest, 40)) shouldBe Right(FormattingResponse(expectedText))
  }

}
