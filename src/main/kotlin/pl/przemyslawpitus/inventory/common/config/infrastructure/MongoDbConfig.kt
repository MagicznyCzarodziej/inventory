package pl.przemyslawpitus.inventory.common.config.infrastructure

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
@EnableConfigurationProperties(MongoDbProperties::class)
class MongoDbConfig {
    @Bean
    fun mongoClient(mongoDbProperties: MongoDbProperties): MongoClient {
        val credential = MongoCredential.createCredential(
            mongoDbProperties.username,
            mongoDbProperties.authenticationDatabase,
            mongoDbProperties.password.toCharArray()
        )
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(mongoDbProperties.connectionString)
            .credential(credential)
            .build()

        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient, mongoDbProperties: MongoDbProperties): MongoTemplate {
        val mongoDbFactory = SimpleMongoClientDatabaseFactory(mongoClient, mongoDbProperties.database)

        return MongoTemplate(mongoDbFactory)
    }
}

@ConfigurationProperties(prefix = "mongodb")
data class MongoDbProperties(
    val username: String,
    val password: String,
    val database: String,
    val authenticationDatabase: String,
    val host: String,
    val port: Int,
) {
    val connectionString: ConnectionString
        get() = ConnectionString("mongodb://$username:$password@$host:$port/$database")
}
