/**
 * Copyright (c) 2022 Mohammed Irfanulla S <mohammed.irfanulla.s1@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.phonepe.growth.mustang.predicate;

import static com.phonepe.growth.mustang.json.JsonUtils.getNodeValue;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.phonepe.growth.mustang.common.RequestContext;
import com.phonepe.growth.mustang.debug.PredicateDebugResult;
import com.phonepe.growth.mustang.detail.Detail;
import com.phonepe.growth.mustang.predicate.impl.ExcludedPredicate;
import com.phonepe.growth.mustang.predicate.impl.IncludedPredicate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(name = PredicateType.INCLUDED_TEXT, value = IncludedPredicate.class),
        @JsonSubTypes.Type(name = PredicateType.EXCLUDED_TEXT, value = ExcludedPredicate.class), })
@JsonPropertyOrder({ "type", "lhs", "detail", "weight", "defaultResult" })
public abstract class Predicate {
    @NotNull
    private PredicateType type;
    @NotBlank
    private String lhs;
    private Long weight;
    private boolean defaultResult;

    public boolean evaluate(RequestContext context) {
        final Object lhsValue = getNodeValue(context.getNode(), lhs);
        if (Objects.nonNull(lhsValue)) {
            return evaluate(context, lhsValue);
        }
        return defaultResult;
    }

    public PredicateDebugResult debug(final RequestContext context) {
        return PredicateDebugResult.builder()
                .result(evaluate(context))
                .type(type)
                .lhs(lhs)
                .lhsValue(getNodeValue(context.getNode(), lhs))
                .detail(getDetail())
                .build();
    }

    public abstract boolean evaluate(RequestContext context, Object lhsValue);

    public abstract Detail getDetail();

    public abstract <T> T accept(PredicateVisitor<T> visitor);

}
