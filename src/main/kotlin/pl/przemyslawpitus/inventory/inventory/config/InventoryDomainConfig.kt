package pl.przemyslawpitus.inventory.inventory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.item.changeItemCountUseCase.StockTransformer
import pl.przemyslawpitus.inventory.inventory.domain.item.changeItemCountUseCase.UpdateCurrentStockUseCase
import pl.przemyslawpitus.inventory.inventory.domain.category.createCategoryUseCase.CreateCategoryUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.createItemUseCase.CreateItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.createParentItemUseCase.CreateParentItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase.EditItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.geItemUseCase.GetItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.category.getCategoriesUseCase.GetCategoriesUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemProvider
import pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase.EditItemTransformer
import pl.przemyslawpitus.inventory.inventory.domain.item.getItemsUseCase.GetItemsUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemsUseCase.GetParentItemsUseCase
import pl.przemyslawpitus.inventory.inventory.domain.photo.getPhotoUseCase.GetPhotoUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.removeItemUseCase.RemoveItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemProvider
import pl.przemyslawpitus.inventory.inventory.domain.photo.PhotoProvider
import pl.przemyslawpitus.inventory.inventory.domain.photo.PhotoRepository
import pl.przemyslawpitus.inventory.inventory.domain.photo.uploadPhotoUseCase.UploadPhotoUseCase
import pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb.ItemEntityToDomainMapper
import pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb.MongoCategoryRepository
import pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb.MongoItemRepository
import pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb.MongoParentItemRepository
import pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb.MongoPhotoRepository
import pl.przemyslawpitus.inventory.common.infrastructure.mongodb.MongoUserRepository
import pl.przemyslawpitus.inventory.inventory.domain.category.editCategoryUseCase.EditCategoryTransformer
import pl.przemyslawpitus.inventory.inventory.domain.category.editCategoryUseCase.EditCategoryUseCase
import pl.przemyslawpitus.inventory.inventory.domain.category.getCategoryWithItemsCountUseCase.GetCategoryWithItemsCountUseCase
import pl.przemyslawpitus.inventory.inventory.domain.category.removeCategoryUseCase.RemoveCategoryUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.editParentItemUseCase.EditParentItemTransformer
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.editParentItemUseCase.EditParentItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemWithSubItemsCountUseCase.GetParentItemWithSubItemsCountUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.removeParentItemUseCase.RemoveParentItemUseCase
import pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb.ParentItemEntityToDomainMapper

@Configuration
@Suppress("TooManyFunctions")
class InventoryDomainConfig {
    @Bean
    fun userRepository(
        mongoTemplate: MongoTemplate,
    ) = MongoUserRepository(
        mongoTemplate = mongoTemplate,
    )

    @Bean
    fun itemRepository(
        mongoTemplate: MongoTemplate,
        itemEntityToDomainMapper: ItemEntityToDomainMapper,
    ) = MongoItemRepository(
        mongoTemplate = mongoTemplate,
        itemEntityToDomainMapper = itemEntityToDomainMapper,
    )

    @Bean
    fun itemProvider(
        itemRepository: ItemRepository,
    ) = ItemProvider(
        itemRepository = itemRepository,
    )

    @Bean
    fun itemEntityToDomainMapper(
        categoryRepository: CategoryRepository,
        parentItemRepository: ParentItemRepository,
    ) = ItemEntityToDomainMapper(
        categoryRepository = categoryRepository,
        parentItemRepository = parentItemRepository,
    )

    @Bean
    fun parentItemRepository(
        mongoTemplate: MongoTemplate,
        parentItemEntityToDomainMapper: ParentItemEntityToDomainMapper,
    ) = MongoParentItemRepository(
        mongoTemplate = mongoTemplate,
        parentItemEntityToDomainMapper = parentItemEntityToDomainMapper,
    )

    @Bean
    fun parentItemProvider(
        parentItemRepository: ParentItemRepository,
    ) = ParentItemProvider(
        parentItemRepository = parentItemRepository,
    )

    @Bean
    fun parentItemEntityToDomainMapper(
        categoryRepository: CategoryRepository,
    ) = ParentItemEntityToDomainMapper(
        categoryRepository = categoryRepository,
    )

    @Bean
    fun categoryRepository(
        mongoTemplate: MongoTemplate,
    ) = MongoCategoryRepository(
        mongoTemplate = mongoTemplate,
    )

    @Bean
    fun categoryProvider(
        categoryRepository: CategoryRepository,
    ) = CategoryProvider(
        categoryRepository = categoryRepository,
    )

    @Bean
    fun photoRepository(
        mongoTemplate: MongoTemplate,
    ) = MongoPhotoRepository(
        mongoTemplate = mongoTemplate,
    )

    @Bean
    fun photoProvider(
        photoRepository: PhotoRepository,
    ) = PhotoProvider(
        photoRepository = photoRepository,
    )

    @Bean
    fun createItemUseCase(
        itemRepository: ItemRepository,
        categoryProvider: CategoryProvider,
        parentItemProvider: ParentItemProvider,
    ) = CreateItemUseCase(
        itemRepository = itemRepository,
        categoryProvider = categoryProvider,
        parentItemProvider = parentItemProvider,
    )

    @Bean
    fun createParentItemUseCase(
        parentItemRepository: ParentItemRepository,
        categoryProvider: CategoryProvider,
    ) = CreateParentItemUseCase(
        parentItemRepository = parentItemRepository,
        categoryProvider = categoryProvider,
    )

    @Bean
    fun stockTransformer() = StockTransformer()

    @Bean
    fun updateCurrentStockUseCase(
        itemProvider: ItemProvider,
        itemRepository: ItemRepository,
        stockTransformer: StockTransformer,
    ) = UpdateCurrentStockUseCase(
        itemProvider = itemProvider,
        itemRepository = itemRepository,
        stockTransformer = stockTransformer,
    )

    @Bean
    fun getItemsUseCase(
        itemRepository: ItemRepository,
        parentItemRepository: ParentItemRepository,
    ) = GetItemsUseCase(
        itemRepository = itemRepository,
        parentItemRepository = parentItemRepository,
    )

    @Bean
    fun getItemUseCase(
        itemProvider: ItemProvider,
    ) = GetItemUseCase(
        itemProvider = itemProvider,
    )

    @Bean
    fun removeItemUseCase(
        itemRepository: ItemRepository,
        itemProvider: ItemProvider,
    ) = RemoveItemUseCase(
        itemRepository = itemRepository,
        itemProvider = itemProvider,
    )

    @Bean
    fun uploadPhotoUseCase(
        photoRepository: PhotoRepository,
    ) = UploadPhotoUseCase(
        photoRepository = photoRepository,
    )

    @Bean
    fun getPhotoUseCase(
        photoProvider: PhotoProvider,
    ) = GetPhotoUseCase(
        photoProvider = photoProvider,
    )

    @Bean
    fun getParentItemsUseCase(
        parentItemRepository: ParentItemRepository,
    ) = GetParentItemsUseCase(
        parentItemRepository = parentItemRepository,
    )

    @Bean
    fun getCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ) = GetCategoriesUseCase(
        categoryRepository = categoryRepository,
    )

    @Bean
    fun createCategoryUseCase(
        categoryRepository: CategoryRepository,
    ) = CreateCategoryUseCase(
        categoryRepository = categoryRepository,
    )

    @Bean
    fun getCategoryWithItemsCountUseCase(
        categoryProvider: CategoryProvider,
        itemRepository: ItemRepository,
    ) = GetCategoryWithItemsCountUseCase(
        categoryProvider = categoryProvider,
        itemRepository = itemRepository,
    )

    @Bean
    fun editCategoryTransformer() = EditCategoryTransformer()

    @Bean
    fun removeCategoryUseCase(
        categoryRepository: CategoryRepository,
        categoryProvider: CategoryProvider,
        itemRepository: ItemRepository,
    ) = RemoveCategoryUseCase(
        categoryRepository = categoryRepository,
        categoryProvider = categoryProvider,
        itemRepository = itemRepository,
    )

    @Bean
    fun editCategoryUseCase(
        categoryProvider: CategoryProvider,
        categoryRepository: CategoryRepository,
        editCategoryTransformer: EditCategoryTransformer,
    ) = EditCategoryUseCase(
        categoryProvider = categoryProvider,
        categoryRepository = categoryRepository,
        editCategoryTransformer = editCategoryTransformer,
    )

    @Bean
    fun editItemTransformer(
        categoryProvider: CategoryProvider,
        photoProvider: PhotoProvider,
    ) = EditItemTransformer(
        categoryProvider = categoryProvider,
        photoProvider = photoProvider,
    )

    @Bean
    fun editItemUseCase(
        itemRepository: ItemRepository,
        itemProvider: ItemProvider,
        editItemTransformer: EditItemTransformer,
    ) = EditItemUseCase(
        itemRepository = itemRepository,
        itemProvider = itemProvider,
        editItemTransformer = editItemTransformer,
    )

    @Bean
    fun getParentItemWithSubItemsCountUseCase(
        parentItemProvider: ParentItemProvider,
        itemRepository: ItemRepository,
    ) = GetParentItemWithSubItemsCountUseCase(
        parentItemProvider = parentItemProvider,
        itemRepository = itemRepository,
    )

    @Bean
    fun editParentItemTransformer(
        categoryProvider: CategoryProvider,
    ) = EditParentItemTransformer(
        categoryProvider = categoryProvider,
    )

    @Bean
    fun editParentItemUseCase(
        parentItemProvider: ParentItemProvider,
        editParentItemTransformer: EditParentItemTransformer,
        parentItemRepository: ParentItemRepository,
    ) = EditParentItemUseCase(
        parentItemProvider = parentItemProvider,
        editParentItemTransformer = editParentItemTransformer,
        parentItemRepository = parentItemRepository,
    )

    @Bean
    fun removeParentItemUseCase(
        parentItemRepository: ParentItemRepository,
        parentItemProvider: ParentItemProvider,
        itemRepository: ItemRepository,
    ) = RemoveParentItemUseCase(
        parentItemRepository = parentItemRepository,
        parentItemProvider = parentItemProvider,
        itemRepository = itemRepository,
    )
}

