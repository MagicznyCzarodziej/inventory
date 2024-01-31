package pl.przemyslawpitus.inventory.domain.parentItem.getParentItemsUseCase

import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItem

class GetParentItemsUseCase(
    private val parentItemRepository: ParentItemRepository,
) {
    fun getParentItems(): List<ParentItem> {
        return parentItemRepository.getAll()
    }
}