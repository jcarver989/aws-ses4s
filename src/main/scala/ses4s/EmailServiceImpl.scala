package ses4s

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.Properties

import com.amazonaws.regions.Regions
import com.amazonaws.services.simpleemail.{ AmazonSimpleEmailService, AmazonSimpleEmailServiceClientBuilder }
import com.amazonaws.services.simpleemail.model.{ RawMessage, SendRawEmailRequest }

import javax.activation.DataHandler
import javax.mail.{ Message, Session }
import javax.mail.internet.{ InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart, MimeUtility }
import javax.mail.util.ByteArrayDataSource

/**
 * Sends a multi-part text or html email with an arbitrary number of attachments via Amazon's SES service
 *
 *  ex val service = EmailServiceImpl() // default AWS region
 *
 *  service.sendEmail(Email(to = "...", from = "...", HTML("<h1>Hello World</h1>)")
 *
 */
class EmailServiceImpl(client: AmazonSimpleEmailService) extends EmailService {
  override def sendEmail(email: Email, sourceArn: Option[String] = None): Unit = {
    val request = sourceArn match {
      case Some(arn) => new SendRawEmailRequest(email.toRawMessage).withSourceArn(arn)
      case None      => new SendRawEmailRequest(email.toRawMessage)
    }
    client.sendRawEmail(request)
  }
}

object EmailServiceImpl {
  def apply(): EmailServiceImpl = {
    val client = AmazonSimpleEmailServiceClientBuilder.standard()
      .withRegion(Regions.DEFAULT_REGION).build()

    new EmailServiceImpl(client)
  }
}

