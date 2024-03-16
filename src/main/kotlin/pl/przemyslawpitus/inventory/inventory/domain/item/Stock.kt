package pl.przemyslawpitus.inventory.inventory.domain.item

import java.time.Instant

data class Stock(
    val currentStock: Int,
    val desiredStock: Int,
    val stockHistory: List<StockHistoryEntry>,
)

data class StockHistoryEntry(
    val change: Int,
    val stockAfterChange: Int,
    val createdAt: Instant,
)