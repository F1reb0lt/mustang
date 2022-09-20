/**
 * Copyright (c) 2022 Original Author(s), PhonePe India Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.phonepe.mustang.index.core;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotBlank;

import com.phonepe.mustang.detail.Caveat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = { "name", "caveat", "value", "order" })
@NoArgsConstructor
@AllArgsConstructor
public class Key {
    @NotBlank
    private String name;
    @NotNull
    private Caveat caveat;
    @NotNull
    private Object value;
    private int order;
    @Builder.Default
    private long upperBoundScore = 10;
}