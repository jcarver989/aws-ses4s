package ses4s

trait EmailService {
  def sendEmail(email: Email, sourceArn: Option[String] = None): Unit
}