package pl.przemyslawpitus.inventory.logging

const val PREFIX_LENGTH = 6
val PREFIXES_WITH_PIPE = LogLayer.entries.map { it.name.padEnd(PREFIX_LENGTH) + " | " }
