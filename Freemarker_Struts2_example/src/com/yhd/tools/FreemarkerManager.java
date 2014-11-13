package com.yhd.tools;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.freemarker.ScopesHashModel;
import com.opensymphony.xwork2.util.ValueStack;


import freemarker.template.ObjectWrapper;

public class FreemarkerManager extends org.apache.struts2.views.freemarker.FreemarkerManager {
	@Override
	protected ScopesHashModel buildScopesHashModel(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, ObjectWrapper wrapper,
			ValueStack stack) {
		ScopesHashModel sModel = new ScopesHashModel(wrapper, servletContext, request, stack);
		sModel.put("scopes", 3);
		
		sModel.put("compressJs",new CompressDirective());
		sModel.put("truncate",new TruncateTemplateMethodModel());
		return sModel;
			
	}
	
}
