package pl.przemyslawpitus.inventory.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.color.ANSIConstants

private const val SET_DEFAULT_COLOR = ANSIConstants.ESC_START + "0;" + ANSIConstants.DEFAULT_FG + ANSIConstants.ESC_END

private const val ERROR_PREFIX = ANSIConstants.ESC_START +
        ANSIConstants.BOLD +
        ANSIConstants.RED_FG +
        ANSIConstants.ESC_END +
        "ERROR" +
        SET_DEFAULT_COLOR +
        " "

private const val LIGHT_YELLOW = "93"

class ColorPrefixConverter : ClassicConverter() {
    override fun convert(event: ILoggingEvent): String =
        PrefixBuilder(event)
            .withConditionalErrorPrefix(ERROR_PREFIX)
            .withConditionalLevelPrefix(event.level.levelStr.padEnd(PREFIX_LENGTH))
            .withLayerPrefix(
                LogLayer.entries
                    .find { event.message.startsWith(it.name) }
                    ?.let { getColoredPrefix(it) }
            )
            .build()

    private fun getColoredPrefix(prefix: LogLayer): String {
        return when (prefix) {
            LogLayer.DOMAIN -> colorAndPadPrefix(ANSIConstants.GREEN_FG, prefix)
            LogLayer.INFRA -> colorAndPadPrefix(ANSIConstants.MAGENTA_FG, prefix)
            LogLayer.API -> colorAndPadPrefix(LIGHT_YELLOW, prefix)
            LogLayer.CONFIG -> colorAndPadPrefix(ANSIConstants.YELLOW_FG, prefix)
        }
    }

    private fun colorAndPadPrefix(color: String, prefix: LogLayer): String {
        return ANSIConstants.ESC_START +
                color +
                ANSIConstants.ESC_END +
                prefix.name.padEnd(PREFIX_LENGTH) +
                SET_DEFAULT_COLOR
    }
}

private class PrefixBuilder(
    val event: ILoggingEvent,
    private var errorPrefix: String? = null,
    private var levelPrefix: String? = null,
    private var layerPrefix: String? = null,
) {
    fun withConditionalErrorPrefix(errorPrefix: String): PrefixBuilder {
        if (event.level === Level.ERROR) {
            this.errorPrefix = errorPrefix
        }
        return this
    }

    fun withConditionalLevelPrefix(levelPrefix: String): PrefixBuilder {
        val message = event.message
        val messageHasPrefix = PREFIXES_WITH_PIPE.any { message.startsWith(it) }

        if (!messageHasPrefix) {
            this.levelPrefix = levelPrefix
        }
        return this
    }

    fun withLayerPrefix(layerPrefix: String?): PrefixBuilder {
        this.layerPrefix = layerPrefix
        return this
    }

    fun build(): String {
        return listOfNotNull(errorPrefix, layerPrefix, levelPrefix).joinToString(" ")
    }
}
