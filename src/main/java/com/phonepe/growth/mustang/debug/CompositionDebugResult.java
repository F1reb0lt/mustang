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
package com.phonepe.growth.mustang.debug;

import com.phonepe.growth.mustang.composition.CompositionType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompositionDebugResult {
    private boolean result;
    private CompositionType type;
    private List<PredicateDebugResult> predicateDebugResults;
}
