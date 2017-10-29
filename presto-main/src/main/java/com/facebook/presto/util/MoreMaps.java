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
package com.facebook.presto.util;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public final class MoreMaps
{
    private MoreMaps() {}

    public static <K, V> Map<K, V> mergeMaps(Map<K, V> map1, Map<K, V> map2, BinaryOperator<V> merger)
    {
        return mergeMaps(Stream.of(map1, map2), merger);
    }

    public static <K, V> Map<K, V> mergeMaps(Stream<Map<K, V>> mapStream, BinaryOperator<V> merger)
    {
        return mapStream
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, merger));
    }

    public static class EmptyMap<K, V>
            implements Map<K, V>
    {
        private final V defaultValue;

        public EmptyMap(V defaultValue)
        {
            this.defaultValue = defaultValue;
        }

        @Override
        public int size()
        {
            return 0;
        }

        @Override
        public boolean isEmpty()
        {
            return true;
        }

        @Override
        public boolean containsKey(Object key)
        {
            return false;
        }

        @Override
        public boolean containsValue(Object value)
        {
            return false;
        }

        @Override
        public V get(Object key)
        {
            return null;
        }

        @Override
        public V put(K key, V value)
        {
            return null;
        }

        @Override
        public V remove(Object key)
        {
            return defaultValue;
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m)
        {
        }

        @Override
        public void clear()
        {
        }

        @Override
        public Set<K> keySet()
        {
            return ImmutableSet.of();
        }

        @Override
        public Collection<V> values()
        {
            return ImmutableSet.of();
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet()
        {
            return ImmutableSet.of();
        }
    }
}
