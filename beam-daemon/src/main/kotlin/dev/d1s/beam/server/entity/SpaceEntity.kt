/*
 * Copyright 2023 Mikhail Titov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.d1s.beam.server.entity

import dev.d1s.beam.commons.Role
import dev.d1s.beam.commons.SpaceSlug
import dev.d1s.beam.server.database.blocks
import dev.d1s.exkt.ktorm.UuidIdentifiedAndModificationTimestampAware
import dev.d1s.exkt.ktorm.lazyFetch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.Entity
import org.ktorm.entity.find

internal interface SpaceEntity : UuidIdentifiedAndModificationTimestampAware<SpaceEntity>, KoinComponent {

    var slug: SpaceSlug

    var role: Role

    val blocks
        get() = lazyFetch("blocks") {
            get<Database>().blocks.find {
                it.spaceId eq id.toString()
            }
        }

    companion object : Entity.Factory<SpaceEntity>() {

        const val ROOT_SPACE_SLUG = "root"
    }
}

internal val SpaceEntity.asString
    get() = "SpaceEntity{slug = $slug, role = $role}"

internal val SpaceEntity.isRoot get() = slug == SpaceEntity.ROOT_SPACE_SLUG