package pl.przemyslawpitus.inventory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.przemyslawpitus.inventory.domain.CategoryRepository
import pl.przemyslawpitus.inventory.domain.ItemRepository
import pl.przemyslawpitus.inventory.domain.ParentItemRepository
import pl.przemyslawpitus.inventory.domain.changeItemCountUseCase.StockTransformer
import pl.przemyslawpitus.inventory.domain.changeItemCountUseCase.UpdateCurrentStockUseCase
import pl.przemyslawpitus.inventory.domain.createItemUseCase.CreateItemRepository
import pl.przemyslawpitus.inventory.domain.createItemUseCase.CreateItemUseCase
import pl.przemyslawpitus.inventory.domain.createParentItemUseCase.CreateParentItemUseCase
import pl.przemyslawpitus.inventory.domain.geItemUseCase.GetItemUseCase
import pl.przemyslawpitus.inventory.domain.getCategoriesUseCase.GetCategoriesUseCase
import pl.przemyslawpitus.inventory.domain.getItemsUseCase.GetItemsUseCase
import pl.przemyslawpitus.inventory.domain.getParentItemsUseCase.GetParentItemsUseCase
import pl.przemyslawpitus.inventory.domain.getPhotoUseCase.GetPhotoUseCase
import pl.przemyslawpitus.inventory.domain.removeItemUseCase.RemoveItemUseCase
import pl.przemyslawpitus.inventory.domain.uploadPhotoUseCase.PhotoRepository
import pl.przemyslawpitus.inventory.domain.uploadPhotoUseCase.UploadPhotoUseCase
import pl.przemyslawpitus.inventory.infrastructure.InMemoryCategoryRepository
import pl.przemyslawpitus.inventory.infrastructure.InMemoryItemRepository
import pl.przemyslawpitus.inventory.infrastructure.InMemoryParentItemRepository
import pl.przemyslawpitus.inventory.infrastructure.InMemoryPhotoRepository

@Configuration
@Suppress("TooManyFunctions")
class DomainConfig {
    @Bean
    fun ItemRepository() = InMemoryItemRepository()

    @Bean
    fun parentItemRepository() = InMemoryParentItemRepository()

    @Bean
    fun categoryRepository() = InMemoryCategoryRepository()

    @Bean
    fun photoRepository() = InMemoryPhotoRepository()

    @Bean
    fun createItemUseCase(
        createItemRepository: CreateItemRepository,
        categoryRepository: CategoryRepository,
        parentItemRepository: ParentItemRepository,
        photoRepository: PhotoRepository,
    ) = CreateItemUseCase(
        createItemRepository = createItemRepository,
        categoryRepository = categoryRepository,
        parentItemRepository = parentItemRepository,
        photoRepository = photoRepository,
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
    ) = GetItemsUseCase(
      itemRepository = itemRepository,
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

}

