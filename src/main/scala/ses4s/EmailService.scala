package ses4s

trait EmailService {
  def sendEmail(email: Email): Unit
}
