package etlmail.engine;

import java.util.Collections;
import java.util.Map;

case class NewsletterNotification(subject: String, template: String, resourcesPath: String, from: String, to: String, cc: String, variables: Map[String, Any]) {
}
