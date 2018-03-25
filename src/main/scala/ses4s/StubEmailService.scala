package ses4s

class StubEmailService extends EmailService {
  val sentEmails = scala.collection.mutable.Buffer[Email]()
  def sendEmail(email: Email): Unit = {
    sentEmails += email
  }
}