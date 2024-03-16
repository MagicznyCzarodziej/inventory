package pl.przemyslawpitus.inventory.inventory.domain.parentItem.createParentItemUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.common.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class CreateParentItemUseCase(
    private val categoryProvider: CategoryProvider,
    private val parentItemRepository: ParentItemRepository,
) {
    fun createParentItem(itemDraft: ParentItemDraft, userId: UserId): ParentItem {
        logger.domain("Create parent item | ${itemDraft.name}")

        val now = Instant.now()
        val category = categoryProvider.getByIdForUser(
            categoryId = itemDraft.categoryId,
            userId = userId
        )

        val item = ParentItem(
            id = ParentItemId(value = randomUuid()),
            userId = userId,
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
