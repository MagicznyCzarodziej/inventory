package pl.przemyslawpitus.inventory.infrastructure.mongodb

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import pl.przemyslawpitus.inventory.domain.user.UserId

fun queryByUserId(userId: UserId) = Query()
    .addCriteria(
        Criteria.where("userId").isEqualTo(userId.value)
    )