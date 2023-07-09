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

package dev.d1s.beam.commons.validation

import dev.d1s.beam.commons.AbstractBlock
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxItems
import io.konform.validation.jsonschema.minItems
import io.konform.validation.jsonschema.minimum

public val validateBlock: Validation<AbstractBlock> = Validation {
    AbstractBlock::index {
        minimum(0) hint "block index must be greater or equal to 0"
    }

    AbstractBlock::entities {
        minItems(1) hint "block must have at least 1 entity"
        maxItems(100) hint "block must have less than 100 entities"
    }

    AbstractBlock::entities onEach {
        run(validateContentEntity)
    }

    AbstractBlock::metadata {
        run(validateMetadata)
    }
}