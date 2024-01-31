package pl.przemyslawpitus.inventory.domain.parentItem.createParentItemUseCase

import pl.przemyslawpitus.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class CreateParentItemUseCase(
    private val categoryRepository: CategoryRepository,
    private val parentItemRepository: ParentItemRepository,
) {
    fun createParentItem(itemDraft: ParentItemDraft): ParentItem {
        logger.domain("Create parent item | ${itemDraft.name}")

        val now = Instant.now()
        val category = categoryRepository.getById(itemDraft.categoryId) ?: throw RuntimeException("Category not found")

        val item = ParentItem(
            id = ParentItemId(value = randomUuid()),
            name = itemDraft.name,
            category = category,
            createdAt = now,
            updatedAt = now,
            removedAt = null,
            version = 0,
        )

        val savedItem = parentItemRepository.save(item)
        logger.domain("Saved parent item | ${item.id}")
        return savedItem
    }

    private companion object : WithLogger()
}

data class ParentItemDraft(
    val name: String,
    val categoryId: CategoryId,
)