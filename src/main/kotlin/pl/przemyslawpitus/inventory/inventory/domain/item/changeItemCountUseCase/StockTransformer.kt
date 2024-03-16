package pl.przemyslawpitus.inventory.inventory.domain.item.changeItemCountUseCase

import pl.przemyslawpitus.inventory.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.inventory.domain.item.StockHistoryEntry
import java.time.Instant

class StockTransformer {
    fun updateCurrentStock(stock: Stock, stockChange: Int): Stock {
        val newCurrentStock = stock.currentStock + stockChange
        if (newCurrentStock < 0) throw CurrentStockCannotBeNegativeException()

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

class CurrentStockCannotBeNegativeException : RuntimeException("Current stock cannot be less than 0")