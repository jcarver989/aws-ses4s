package ses4s

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.Properties

import com.amazonaws.services.simpleemail.model.RawMessage

import javax.activation.DataHandler
import javax.mail.{ Message, Session }
import javax.mail.internet.{ InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart, MimeUtility }
import javax.mail.util.ByteArrayDataSource

case class Email(
  from:        String,
  to:          String,
  subject:     String,
  content:     EmailContent,
  attachments: Seq[Attachment] = Seq.empty) {

  def toRawMessage: RawMessage = {
    val session = Session.getDefaultInstance(new Properties())
    val message = new MimeMessage(session)
    message.setSubject(subject, "UTF-8")
    message.setFrom(new InternetAddress(from))
    message.setRecipients(Message.RecipientType.TO, to)
    message.setContent(createMessageContent)
    val output = new ByteArrayOutputStream()
    message.writeTo(output)
    new RawMessage(ByteBuffer.wrap(output.toByteArray()))
  }

  private def createMessageContent: MimeMultipart = {
    val defaultCharSet = MimeUtility.getDefaultJavaCharset()

    val messageContainer = new MimeMultipart("mixed")
    val textWrapper = new MimeBodyPart()
    val textBody = new MimeMultipart("alternative")
    textWrapper.setContent(textBody)
    messageContainer.addBodyPart(textWrapper)

    val (textContent, contentMime) = content match {
      case Text(text) => (text, "text/plain; charset=UTF-8")
      case HTML(html) => (html, "text/html; charset=UTF-8")
    }

    val textPart = new MimeBodyPart()
    textPart.setContent(textContent, contentMime)
    textPart.setHeader("Content-Transfer-Encoding", "base64")
    textBody.addBodyPart(textPart)

    attachments.foreach {
      case Attachment(fileName, mime, bytes) =>
        val att = new MimeBodyPart();
        val dataSource = new ByteArrayDataSource(bytes, mime)
        att.setDataHandler(new DataHandler(dataSource))
        att.setFileName(fileName)
        messageContainer.addBodyPart(att)
    }

    messageContainer
  }
}

sealed trait EmailContent
case class Text(value: String) extends EmailContent
case class HTML(value: String) extends EmailContent

case class Attachment(fileName: String, mimeType: String, bytes: Array[Byte])