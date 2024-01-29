package pl.przemyslawpitus.inventory.domain.getParentItemsUseCase

import pl.przemyslawpitus.inventory.domain.ParentItemRepository
import pl.przemyslawpitus.inventory.domain.item.ParentItem

class GetParentItemsUseCase(
    private val parentItemRepository: ParentItemRepository,
) {
    fun getParentItems(): List<ParentItem> {
        return parentItemRepository.getAll()
    }
}