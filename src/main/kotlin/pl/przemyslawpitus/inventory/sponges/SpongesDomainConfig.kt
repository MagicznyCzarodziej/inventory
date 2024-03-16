package pl.przemyslawpitus.inventory.sponges

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import pl.przemyslawpitus.inventory.sponges.domain.SpongeRepository
import pl.przemyslawpitus.inventory.sponges.domain.createSpongeUseCase.CreateSpongeUseCase
import pl.przemyslawpitus.inventory.sponges.domain.getSpongesUseCase.GetSpongesUseCase
import pl.przemyslawpitus.inventory.sponges.domain.updateSpongeUseCase.UpdateSpongeUseCase
import pl.przemyslawpitus.inventory.sponges.infrastructure.mongodb.MongoSpongeRepository

@Configuration
class SpongesDomainConfig {
    @Bean
    fun spongeRepository(
        mongoTemplate: MongoTemplate,
    ) = MongoSpongeRepository(
        mongoTemplate = mongoTemplate,
    )

    @Bean
    fun getSpongesUseCase(
      spongeRepository: SpongeRepository,
    ) = GetSpongesUseCase(
      spongeRepository = spongeRepository,
    )

    @Bean
    fun updateSpongeUseCase(
      spongeRepository: SpongeRepository,
    ) = UpdateSpongeUseCase(
      spongeRepository = spongeRepository,
    )

    @Bean
    fun createSpongeUseCase(
      spongeRepository: SpongeRepository,
    ) = CreateSpongeUseCase(
      spongeRepository = spongeRepository,
    )
}