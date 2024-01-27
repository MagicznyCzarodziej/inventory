package pl.przemyslawpitus.inventory.logging

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent

class RemoveMessagePrefixConverter : ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        var message = event.message
        PREFIXES_WITH_PIPE.forEach { message = message.removePrefix(it) }
        return message
    }
}
