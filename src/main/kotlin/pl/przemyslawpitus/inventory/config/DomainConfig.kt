package pl.przemyslawpitus.inventory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.przemyslawpitus.inventory.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.domain.auth.AuthTokenVerifier
import pl.przemyslawpitus.inventory.domain.auth.AuthenticationDetailsCreator
import pl.przemyslawpitus.inventory.domain.user.UserRepository
import pl.przemyslawpitus.inventory.domain.auth.loginUseCase.LoginUseCase
import pl.przemyslawpitus.inventory.domain.auth.refreshTokenUseCase.RefreshTokenUseCase
import pl.przemyslawpitus.inventory.domain.auth.registerUseCase.RegisterUseCase
import pl.przemyslawpitus.inventory.domain.category.CategoryProvider
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
import pl.przemyslawpitus.inventory.domain.item.ItemProvider
import pl.przemyslawpitus.inventory.domain.item.getItemsUseCase.GetItemsUseCase
import pl.przemyslawpitus.inventory.domain.parentItem.getParentItemsUseCase.GetParentItemsUseCase
import pl.przemyslawpitus.inventory.domain.photo.getPhotoUseCase.GetPhotoUseCase
import pl.przemyslawpitus.inventory.domain.item.removeItemUseCase.RemoveItemUseCase
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemProvider
import pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase.PhotoRepository
import pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase.UploadPhotoUseCase
import pl.przemyslawpitus.inventory.infrastructure.mongodb.ItemEntityToDomainMapper
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoCategoryRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoItemRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoParentItemRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoPhotoRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.MongoUserRepository
import pl.przemyslawpitus.inventory.infrastructure.mongodb.ParentItemEntityToDomainMapper

@Configuration
@Suppress("TooManyFunctions")
class DomainConfig {
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
    fun loginUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
        authenticationDetailsCreator: AuthenticationDetailsCreator,
    ) = LoginUseCase(
        userRepository = userRepository,
        passwordEncoder = passwordEncoder,
        authenticationDetailsCreator = authenticationDetailsCreator,
    )

    @Bean
    fun refreshTokenUseCase(
        authTokenVerifier: AuthTokenVerifier,
        userRepository: UserRepository,
        authenticationDetailsCreator: AuthenticationDetailsCreator,
    ) = RefreshTokenUseCase(
        authTokenVerifier = authTokenVerifier,
        userRepository = userRepository,
        authenticationDetailsCreator = authenticationDetailsCreator,
    )

    @Bean
    fun authTokenVerifier(
        authenticationProperties: AuthenticationProperties,
    ) = AuthTokenVerifier(
        authenticationProperties = authenticationProperties,
    )

    @Bean
    fun registerUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
    ) = RegisterUseCase(
        userRepository = userRepository,
        passwordEncoder = passwordEncoder,
    )

    @Bean
    fun authenticationDetailsCreator(
        authenticationProperties: AuthenticationProperties,
    ) = AuthenticationDetailsCreator(
        authenticationProperties = authenticationProperties,
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
        itemProvider: ItemProvider,
    ) = EditItemUseCase(
        itemRepository = itemRepository,
        itemProvider = itemProvider,
    )

}

