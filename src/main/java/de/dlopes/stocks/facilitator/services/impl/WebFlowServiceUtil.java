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
package de.dlopes.stocks.facilitator.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service("serviceUtil")
public class WebFlowServiceUtil {

	public boolean isNull(Object object) {
		return object == null;
	};
	
	public boolean notNull(Object object) {
		return !isNull(object);
	};
	
	public boolean isEmpty(String string) {
		return StringUtils.isEmpty(string);
	};

	public boolean notEmpty(String string) {
		return !isEmpty(string);
	};
	
	public boolean isEmpty(List<?> list) {
		return CollectionUtils.isEmpty(list);
	};

	public boolean notEmpty(List<?> list) {
		return !isEmpty(list);
	};
	
}
