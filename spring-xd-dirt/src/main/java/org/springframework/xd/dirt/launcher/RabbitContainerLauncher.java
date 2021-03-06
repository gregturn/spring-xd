/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xd.dirt.launcher;

import java.util.UUID;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.util.Assert;
import org.springframework.xd.dirt.container.XDContainer;

/**
 * @author Mark Fisher
 * @author David Turanski
 */
public class RabbitContainerLauncher extends AbstractContainerLauncher {

	private final ConnectionFactory connectionFactory;


	public RabbitContainerLauncher(ConnectionFactory connectionFactory) {
		Assert.notNull(connectionFactory, "connectionFactory must not be null");
		this.connectionFactory = connectionFactory;
	}

	@Override
	protected String generateId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String getRuntimeInfo(XDContainer container) {
		final StringBuilder runtimeInfo = new StringBuilder();
		runtimeInfo.append(String.format("Using RabbitMQ at %s (virtual host: %s) on port: %d ",
				this.connectionFactory.getHost(),
				this.connectionFactory.getVirtualHost(),
				this.connectionFactory.getPort()));
		return runtimeInfo.toString();
	}

	@Override
	protected void logErrorInfo(Exception exception) {
		if (exception instanceof AmqpConnectException) {
			System.err.println("RabbitMQ does not seem to be running. " +
					"Did you install and start RabbitMQ? " +
					"Please see the Getting Started section of the guide for instructions.");
		}
	}

}
