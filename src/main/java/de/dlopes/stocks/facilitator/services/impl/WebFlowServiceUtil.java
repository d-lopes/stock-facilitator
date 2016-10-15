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
