/*******************************************************************************
 * Copyright (c) 2016 Dominique Lopes.
 * All rights reserved. 
 *
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Dominique Lopes - initial API and implementation
 *******************************************************************************/
package de.dlopes.stocks.facilitator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.faces.config.AbstractFacesFlowConfiguration;
import org.springframework.faces.webflow.FlowFacesContextLifecycleListener;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

/**
 * This class provides the annotation based Spring WebFlow config
 * 
 * @author Dominique Lopes
 *
 */
@Configuration
public class WebFlowConfig extends AbstractFacesFlowConfiguration {

	/**
	 * Executes flows:	<b>the central entry point</b> into the Spring Web Flow system.
	 * Currently includes the following listeners:
	 * <ul>
	 * 	<li>a listener to create and release a FacesContext</li>
	 *  <li>a listener to apply Spring Security authorities</li>
	 * </ul>
	 * 
	 * @return a FlowExecutor instance
	 */
	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry())
				.addFlowExecutionListener(new FlowFacesContextLifecycleListener())
				.addFlowExecutionListener(new SecurityFlowExecutionListener())
				.build();
	}

	/**
	 * define and build the registry of executable flow definitions 
	 * (this is served from the /WEB-INF/flows directory)
	 * 
	 * @return a FlowDefinitionRegistry instance
	 */
	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder(flowBuilderServices())
				.setBasePath("/WEB-INF/flows")
				.addFlowLocationPattern("/**/*-flow.xml")
				.build();
	}

	/**
	 * Configures the Spring Web Flow JSF integration
	 * 
	 * @return a FlowBuilderServices instance
	 */
	@Bean
	public FlowBuilderServices flowBuilderServices() {
		return getFlowBuilderServicesBuilder().setDevelopmentMode(true).build();
	}

}