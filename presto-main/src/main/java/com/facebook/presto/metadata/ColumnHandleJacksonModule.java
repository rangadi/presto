/*
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
package com.facebook.presto.metadata;

import com.facebook.presto.spi.ColumnHandle;

import javax.inject.Inject;

import static java.util.Objects.requireNonNull;

public class ColumnHandleJacksonModule
        extends AbstractTypedJacksonModule<ColumnHandle>
{
    @Inject
    public ColumnHandleJacksonModule(HandleResolver handleResolver)
    {
        super(ColumnHandle.class, "type", new ColumnHandleJsonTypeIdResolver(handleResolver));
    }

    private static class ColumnHandleJsonTypeIdResolver
            implements JsonTypeIdResolver<ColumnHandle>
    {
        private final HandleResolver handleResolver;

        private ColumnHandleJsonTypeIdResolver(HandleResolver handleResolver)
        {
            this.handleResolver = requireNonNull(handleResolver, "handleResolver is null");
        }

        @Override
        public String getId(ColumnHandle columnHandle)
        {
            return handleResolver.getId(columnHandle);
        }

        @Override
        public Class<? extends ColumnHandle> getType(String id)
        {
            return handleResolver.getColumnHandleClass(id);
        }
    }
}
