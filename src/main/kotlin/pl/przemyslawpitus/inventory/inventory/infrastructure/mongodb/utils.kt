package pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import pl.przemyslawpitus.inventory.common.domain.user.UserId

fun queryByUserId(userId: UserId) = Query()
    .addCriteria(
        Criteria.where("userId").isEqualTo(userId.value)
    )