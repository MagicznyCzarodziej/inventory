package pl.przemyslawpitus.inventory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import pl.przemyslawpitus.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.domain.item.changeItemCountUseCase.StockTransformer
import pl.przemyslawpitus.inventory.domain.item.changeItemCountUseCase.UpdateCurrentStockUseCase
import pl.przemyslawpitus.inventory.domain.category.createCategoryUseCase.CreateCategoryUseCase
import pl.przemyslawpitus.inventory.domain.item.createItemUseCase.CreateItemUseCase
import pl.przemyslawpitus.inventory.domain.parentItem.createParentItemUseCase.CreateParentItemUseCase
import pl.przemyslawpitus.inventory.domain.item.editItemUseCase.EditItemUseCase
import pl.przemyslawpitus.inventory.domain.item.geItemUseCase.GetItemUseCase
import pl.przemyslawpitus.inventory.domain.category.getCategoriesUseCase.GetCategoriesUseCase
import pl.przemyslawpitus.inventory.domain.item.getItemsUseCase.GetItemsUseCase
import pl.przemyslawpitus.inventory.domain.parentItem.getParentItemsUseCase.GetParentItemsUseCase
import pl.przemyslawpitus.inventory.domain.photo.getPhotoUseCase.GetPhotoUseCase
import pl.przemyslawpitus.inventory.domain.item.removeItemUseCase.RemoveItemUseCase
import pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase.PhotoRepository
import pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase.UploadPhotoUseCase
import pl.przemyslawpitus.inventory.infrastructure.mongodb.ItemEntityToDomainMapper
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoCategoryRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoItemRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoParentItemRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoPhotoRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.ParentItemEntityToDomainMapper

@Configuration
@Suppress("TooManyFunctions")
class DomainConfig {
    @Bean
    fun itemRepository(
        mongoTemplate: MongoTemplate,
        itemEntityToDomainMapper: ItemEntityToDomainMapper,
    ) = MongoItemRepository(
        mongoTemplate = mongoTemplate,
        itemEntityToDomainMapper = itemEntityToDomainMapper,
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
    fun photoRepository(
        mongoTemplate: MongoTemplate,
    ) = MongoPhotoRepository(
        mongoTemplate = mongoTemplate,
    )

    @Bean
    fun createItemUseCase(
        itemRepository: ItemRepository,
        categoryRepository: CategoryRepository,
        parentItemRepository: ParentItemRepository,
    ) = CreateItemUseCase(
        itemRepository = itemRepository,
        categoryRepository = categoryRepository,
        parentItemRepository = parentItemRepository,
    )

    @Bean
    fun createParentItemUseCase(
        parentItemRepository: ParentItemRepository,
        categoryRepository: CategoryRepository,
    ) = CreateParentItemUseCase(
        parentItemRepository = parentItemRepository,
        categoryRepository = categoryRepository,
    )

    @Bean
    fun stockTransformer() = StockTransformer()

    @Bean
    fun updateCurrentStockUseCase(
        itemRepository: ItemRepository,
        stockTransformer: StockTransformer,
    ) = UpdateCurrentStockUseCase(
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
        itemRepository: ItemRepository,
    ) = GetItemUseCase(
        itemRepository = itemRepository,
    )

    @Bean
    fun removeItemUseCase(
        itemRepository: ItemRepository,
    ) = RemoveItemUseCase(
        itemRepository = itemRepository,
    )

    @Bean
    fun uploadPhotoUseCase(
        photoRepository: PhotoRepository,
    ) = UploadPhotoUseCase(
        photoRepository = photoRepository,
    )

    @Bean
    fun getPhotoUseCase(
        photoRepository: PhotoRepository,
    ) = GetPhotoUseCase(
        photoRepository = photoRepository,
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
    fun editItemUseCase(
        itemRepository: ItemRepository,
    ) = EditItemUseCase(
        itemRepository = itemRepository,
    )

}

