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
import pl.przemyslawpitus.inventory.infrastructure.InMemoryCategoryRepository
import pl.przemyslawpitus.inventory.infrastructure.InMemoryItemRepository
import pl.przemyslawpitus.inventory.infrastructure.InMemoryParentItemRepository

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
    fun createItemUseCase(
        createItemRepository: CreateItemRepository,
        categoryRepository: CategoryRepository,
        parentItemRepository: ParentItemRepository,
    ) = CreateItemUseCase(
        createItemRepository = createItemRepository,
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
}

