/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.scopes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;


/**
 * AutoCloseable copy of {@link org.springframework.context.support.SimpleThreadScope}
 */
public class ThreadScope implements Scope, AutoCloseable {

	private static final Log logger = LogFactory.getLog(ThreadScope.class);

	private final ThreadLocal<Map<String, Object>> threadScope =
			new NamedThreadLocal<>("ThreadScope") {
				@Override
				protected Map<String, Object> initialValue() {
					return new HashMap<>();
				}
			};

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> scope = this.threadScope.get();
		// NOTE: Do NOT modify the following to use Map::computeIfAbsent. For details,
		// see https://github.com/spring-projects/spring-framework/issues/25801.
		Object scopedObject = scope.get(name);
		if (scopedObject == null) {
			scopedObject = objectFactory.getObject();
			scope.put(name, scopedObject);
		}
		return scopedObject;
	}

	@Override
	@Nullable
	public Object remove(String name) {
		Map<String, Object> scope = this.threadScope.get();
		return scope.remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		logger.warn("SimpleThreadScope does not support destruction callbacks. " +
				"Consider using RequestScope in a web environment.");
	}

	@Override
	@Nullable
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return Thread.currentThread().getName();
	}

	public AutoCloseable init() {
		assert this.threadScope.get().isEmpty();
		return this;
	}
	@Override
	public void close() {
		this.threadScope.remove();
	}
}
