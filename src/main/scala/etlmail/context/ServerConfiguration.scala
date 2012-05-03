package etlmail.context

trait ServerConfiguration {
  def host(): String

  def port(): Int

  def username(): String

  def password(): String
}