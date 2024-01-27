package pl.przemyslawpitus.inventory.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

open class WithLogger {
    val logger = LayerLogger(LoggerFactory.getLogger(unwrapCompanionClass(this.javaClass).name))
}

fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return if (ofClass.enclosingClass?.kotlin?.companionObject?.java == ofClass) {
        ofClass.enclosingClass
    } else {
        ofClass
    }
}

class LayerLogger(l: Logger) : Logger by l {
    fun api(message: String) = this.info("API    | $message")
    fun apiError(message: String) = this.error("API    | $message")

    fun domain(message: String) = this.info("DOMAIN | $message")
    fun domainError(message: String) = this.error("DOMAIN | $message")

    fun infra(message: String) = this.debug("INFRA  | $message")
    fun infraError(message: String) = this.error("INFRA  | $message")
    fun infraError(message: String, throwable: Throwable) = this.error("INFRA  | $message", throwable)

    fun config(message: String) = this.debug("CONFIG | $message")
}

enum class LogLayer {
    DOMAIN, INFRA, API, CONFIG,
}