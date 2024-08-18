package pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase

import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.inventory.domain.photo.PhotoProvider
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemValidations
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class EditItemTransformer(
    private val categoryProvider: CategoryProvider,
    private val photoProvider: PhotoProvider,
) {
    fun editItem(item: Item, editItemParameters: EditItemParameters, userId: UserId): Item {
        if (!hasAnyFieldChanged(item, editItemParameters)) {
            logger.domain("Nothing changed | ${item.id}")
            return item
        }

        ItemValidations.validateName(editItemParameters.name)

        return item.copy(
            name = editItemParameters.name.trim(),
            root = updateRoot(currentItem = item, editItemParameters = editItemParameters, userId = userId),
            description = editItemParameters.description?.trim(),
            brand = editItemParameters.brand?.trim(),
            stock = updateStock(currentItem = item, editItemParameters = editItemParameters),
            photo = updatePhoto(editItemParameters, userId),
            barcode = editItemParameters.barcode?.trim(),
            updatedAt = Instant.now(),
        )
    }

    private fun hasAnyFieldChanged(
        currentItem: Item,
        editItemParameters: EditItemParameters,
    ): Boolean =
        (currentItem.name != editItemParameters.name
                || currentItem.description != editItemParameters.description
                || currentItem.brand != editItemParameters.brand
                || hasCategoryChanged(currentItem, editItemParameters)
                || currentItem.stock.currentStock != editItemParameters.currentStock
                || currentItem.stock.desiredStock != editItemParameters.desiredStock
                || currentItem.photo?.id != editItemParameters.photoId
                || currentItem.barcode != editItemParameters.barcode)

    private fun hasCategoryChanged(
        currentItem: Item,
        editItemParameters: EditItemParameters,
    ): Boolean =
        when (currentItem.root) {
            is Root.CategoryRoot -> currentItem.root.category.id != editItemParameters.categoryId
            is Root.ParentRoot -> {
                if (editItemParameters.categoryId != null) {
                    throw CannotEditCategoryInSubItem()
                }
                false
            }
        }

    private fun updateRoot(
        currentItem: Item,
        editItemParameters: EditItemParameters,
        userId: UserId,
    ): Root {
        return when (currentItem.root) {
            is Root.CategoryRoot -> {
                if (editItemParameters.categoryId == null) {
                    throw MissingCategoryId()
                }
                val category = categoryProvider.getByIdForUser(
                    categoryId = editItemParameters.categoryId,
                    userId = userId
                )
                return Root.CategoryRoot(category)
            }

            is Root.ParentRoot -> currentItem.root
        }
    }

    private fun updateStock(
        currentItem: Item,
        editItemParameters: EditItemParameters,
    ) = Stock(
        currentStock = editItemParameters.currentStock,
        desiredStock = editItemParameters.desiredStock,
        stockHistory = currentItem.stock.stockHistory,
    )

    private fun updatePhoto(
        editItemParameters: EditItemParameters,
        userId: UserId,
    ): pl.przemyslawpitus.inventory.inventory.domain.item.ItemPhoto? {
        if (editItemParameters.photoId == null) {
            return null
        }

        val photo = photoProvider.getPhotoByIdForUser(
            photoId = editItemParameters.photoId,
            userId = userId
        )

        return pl.przemyslawpitus.inventory.inventory.domain.item.ItemPhoto(
            id = photo.id,
            url = "/photos/${photo.id.value}",
        )
    }

    private companion object : WithLogger()
}

class MissingCategoryId : RuntimeException("CategoryId is required when editing item that has Category Root")

class CannotEditCategoryInSubItem : RuntimeException("Category id must be null when editing item with ParentItem Root")