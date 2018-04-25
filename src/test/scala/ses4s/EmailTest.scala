package ses4s

import java.nio.charset.StandardCharsets
import org.scalatest._
import com.amazonaws.services.simpleemail.model.RawMessage
import java.util.Base64

class EmailTest extends FlatSpec with Matchers {
  private val to = "foo@gmail.com"
  private val from = "boo@gmail.com"
  private val fromName = "Boo McBoo"

  it should "send a text email" in {
    val rawEmail = Email(from, fromName, to, "You've Won!!!", Text("Hello World")).toRawMessage
    val text = rawMessageToString(rawEmail)
    text should include("From: Boo McBoo <boo@gmail.com>")
    text should include("Subject: You've Won!!!")
    text should include("Content-Type: multipart/mixed;")
    text should include("Content-Type: text/plain; charset=UTF-8")
    text should include(base64Encode("Hello World"))
  }

  it should "send an HTML email" in {
    val rawEmail = Email(from, fromName, to, "You've Won!!!", HTML("<h1>Hello World</h1>")).toRawMessage
    val text = rawMessageToString(rawEmail)
    text should include("From: Boo McBoo <boo@gmail.com>")
    text should include("Subject: You've Won!!!")
    text should include("Content-Type: multipart/mixed;")
    text should include("Content-Type: text/html; charset=UTF-8")
    text should include(base64Encode("<h1>Hello World</h1>"))
  }

  it should "set custom headers" in {
    val rawEmail = Email(from, fromName, to, "You've Won!!!", HTML("<h1>Hello World</h1>"), headers = Map("FooBar" -> "Baz")).toRawMessage
    val text = rawMessageToString(rawEmail)
    text should include("FooBar: Baz")
  }

  private def base64Encode(string: String): String = {
    new String(Base64.getEncoder.encode(string.getBytes), StandardCharsets.UTF_8)
  }

  private def rawMessageToString(msg: RawMessage): String = {
    val data = msg.getData
    val bytes = new Array[Byte](data.remaining)
    data.get(bytes)
    new String(bytes, StandardCharsets.UTF_8)
  }
}