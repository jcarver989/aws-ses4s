
# The Easiest Way To Send Email Through AWS Simple Email Service (SES) In Scala.

```scala
 import ses4s._

val emailService = EmailServiceImpl() // for default region, or pass in a AmazonSimpleEmailService
emailService.sendEmail(Email(
  from = "stewie.griffin@gmail.com",
  to = "peter.griffin@gmail.com",
  subject = "Ha ha, ha hahaha",
  content = HTML("<h1>Victory is mine!</h1>"),
  attachments = Seq(Attachment(
    fileName = "world-domination.pdf", 
    mimeType = "application/pdf", 
    bytes = ...))
))
```

## Getting Started

Just add these two lines to your `build.sbt`: 

```scala
resolvers += Resolver.bintrayRepo("jcarver989", "maven")
libraryDependencies += "com.jcarver989" %% "aws-ses4s" % "latest.integration"
```

## Usage 

### 1. Get an Email Service
```scala
import ses4s._
val emailService = EmailServiceImpl() // for default region, or pass in a AmazonSimpleEmailService
```
OR 

```scala
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import ses4s._

val client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build()
val emailService = new EmailServiceImpl(client)
```

### 2. Send An Email

#### HTML

```scala
 emailService.sendEmail(Email(
   fromName = "Stewie Griffin (Baby Genius)",
   fromEmail = "stewie.griffin@gmail.com",
   toEmail = "peter.griffin@gmail.com",
   subject = "Ha ha, ha hahaha",
   content = HTML("<h1>Victory is mine!</h1>"
   headers = Map("Foo" -> "Bar") // optional custom email headers
 )))
```

#### Text 

```scala
 emailService.sendEmail(Email(
   from = "stewie.griffin@gmail.com",
   to = "peter.griffin@gmail.com",
   subject = "Ha ha, ha hahaha",
   content = Text("Victory is mine!")
 ))
```

#### Attachments

```scala
 emailService.sendEmail(Email(
   from = "stewie.griffin@gmail.com",
   to = "peter.griffin@gmail.com",
   subject = "Ha ha, ha hahaha",
   content = HTML("<h1>Victory is mine!</h1>"),
   attachments = Seq(Attachment(
     fileName = "world-domination.pdf",
     mimeType = "application/pdf",
     bytes = ...))
 ))
```
