package pl.przemyslawpitus.inventory.domain.changeItemCountUseCase

import pl.przemyslawpitus.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.domain.item.StockHistoryEntry
import java.time.Instant

class StockTransformer {
    fun updateCurrentStock(stock: Stock, stockChange: Int): Stock {
        val newCurrentStock = stock.currentStock + stockChange
        val newHistory = stock.stockHistory + StockHistoryEntry(
            change = stockChange,
            stockAfterChange = newCurrentStock,
            createdAt = Instant.now(),
        )

        return stock.copy(
            currentStock = newCurrentStock,
            stockHistory = newHistory,
        )
    }
}