package com.yhd.tools;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class TruncateTemplateMethodModel implements TemplateMethodModel {

	public Object exec(List arguments) throws TemplateModelException {
		// TODO Auto-generated method stub
		 return arguments.get(0).toString();
	}

}
