import static ch.qos.logback.classic.Level.*
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.status.OnConsoleStatusListener

statusListener(OnConsoleStatusListener)

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
	pattern = "%d{yyyy-MM-dd HH:mm:ss} [%-5p] %c{15} - %m%n"
    }
}

logger "org.springframework", WARN
root INFO, ["CONSOLE"]
