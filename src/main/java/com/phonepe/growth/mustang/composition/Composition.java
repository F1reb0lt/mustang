/**
 * Copyright (c) 2021 Mohammed Irfanulla S <mohammed.irfanulla.s1@gmail.com>
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
package com.phonepe.growth.mustang.composition;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.phonepe.growth.mustang.debug.CompositionDebugResult;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.phonepe.growth.mustang.common.RequestContext;
import com.phonepe.growth.mustang.composition.impl.Conjunction;
import com.phonepe.growth.mustang.composition.impl.Disjunction;
import com.phonepe.growth.mustang.predicate.Predicate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(name = CompositionType.AND_TEXT, value = Conjunction.class),
        @JsonSubTypes.Type(name = CompositionType.OR_TEXT, value = Disjunction.class), })
@JsonPropertyOrder({ "type", "predicates" })
public abstract class Composition {
    @NotNull
    private final CompositionType type;
    @NotEmpty
    private List<Predicate> predicates;

    public abstract boolean evaluate(RequestContext context);

    public abstract CompositionDebugResult debug(RequestContext context);

    public abstract double getScore(RequestContext context);

    public abstract <T> T accept(CompositionVisitor<T> visitor);

    public int getWeigthFromContext(RequestContext context, Predicate predicate) {
        try {
            JsonPath.read(context.getNode()
                    .toString(), predicate.getLhs());
            return 1;
        } catch (PathNotFoundException e) {
            return 0;
        }
    }
}
