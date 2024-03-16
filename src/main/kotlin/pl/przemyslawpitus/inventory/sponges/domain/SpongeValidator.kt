package pl.przemyslawpitus.inventory.sponges.domain

import pl.przemyslawpitus.inventory.sponges.domain.updateSpongeUseCase.SpongeUpdate

object SpongeValidator {
    fun validate(color: String, purpose: String) {
        if (purpose.isBlank()) {
            throw EmptySpongePurposeException()
        }

        if (color.isBlank()) {
            throw InvalidSpongeColorException(color)
            // TODO validate color format
        }
    }
}

class EmptySpongePurposeException :
    RuntimeException("Sponge purpose cannot be empty")

class InvalidSpongeColorException(color: String) :
    RuntimeException("Invalid sponge color (${color})")
