package etlmail.context

trait ServerConfiguration {
  def getHost(): String

  def getPort(): Int

  def getUsername(): String

  def getPassword(): String
}