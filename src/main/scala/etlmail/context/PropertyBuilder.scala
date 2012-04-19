package etlmail.context

import java.io.IOException
import java.io.InputStream
import java.util.Properties

import org.springframework.core.io.Resource

import com.google.common.io.Closeables

abstract class PropertySetter {
  def yields(value: String): PropertyBuilder
}

class PropertyBuilder {
  val asProperties = new Properties

  def key(key: String) = new PropertySetter {
    def yields(value: String): PropertyBuilder = {
      asProperties.setProperty(key, value)
      PropertyBuilder.this
    }
  }
}

object PropertyBuilder {
  @throws(classOf[IOException])
  def readProperties(propertyResource: Resource): Properties = {
    val result = new Properties
    var propertyInputStream: InputStream = null
    try {
      propertyInputStream = propertyResource.getInputStream()
      result.load(propertyInputStream)
      propertyInputStream.close()
    } finally {
      Closeables.closeQuietly(propertyInputStream)
    }
    result
  }
}