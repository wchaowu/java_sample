package com.yhd.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import sun.org.mozilla.javascript.internal.EvaluatorException;



import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CompressDirective implements TemplateDirectiveModel {  

	int linebreakpos = -1;
	boolean munge = true;
	boolean verbose = false;
	boolean preserveAllSemiColons = false;
	boolean disableOptimizations = false;
	private Map<String,List<File>> jsGenMap=new HashMap();
	private Map<String,List<File>> cssGenMap=new HashMap();
	
    public void execute(Environment env, Map params, TemplateModel[] loopVars,  
            TemplateDirectiveBody body) throws TemplateException, IOException {  
        // 检查是否传递参数，此指令禁止传参！  
        if (!params.isEmpty()) {  
            throw new TemplateModelException(  
                    "This directive doesn't allow parameters.");  
        }  
    //    env.getCurrentEnvironment().__getitem__("page_js")
       System.out.println(params.size());
        // 禁用循环变量  
        /* 
         * 循环变量 
                 用户定义指令可以有循环变量，通常用于重复嵌套内容，基本用法是：作为nested指令的参数传递循环变量的实际值，而在调用用户定义指令时，在${"<@…>"}开始标记的参数后面指定循环变量的名字 
                 例子： 
            <#macro repeat count> 
              <#list 1..count as x> 
                <#nested x, x/2, x==count> 
              </#list> 
            </#macro> 
            <@repeat count=4 ; c, halfc, last> 
              ${c}. ${halfc}<#if last> Last!</#if> 
            </@repeat>  
        */  
  
      
        // 指令内容体不为空  
        if (body != null) {  
            // Executes the nested body. Same as <#nested> in FTL, except  
            // that we use our own writer instead of the current output writer.  
	   Writer  w = env.getOut();
	
	  
	   PageJsCompressWrite pageJs  = new PageJsCompressWrite(w);
      
	
          body.render(pageJs);  
          pageJs.flush();
         
        } else {  
            throw new RuntimeException("missing body");  
        }  
      
    }  
	private static  class PageJsCompressWrite extends Writer {
	    private Writer out = new  StringWriter();  
	    private  StringBuilder strBuffer = new StringBuilder();
	  String encoding = "UTF-8";  
	    PageJsCompressWrite(Writer out) {  
            this.out = out;        
        }  
		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {	
			if(len>0){
				   char[] transformedCbuf = new char[len];  	     	   
		            for (int i = 0; i < len; i++) {  
		                transformedCbuf[i] = cbuf[i + off];  
		            }  
				strBuffer.append(transformedCbuf);
			}
		}

		public static void main(String[] args) {

String code ="var openUnionSiteFlag = 1;\n" +
"(function(){\n" + 
"  if(typeof openUnionSiteFlag != \"undefined\" && openUnionSiteFlag != \"0\"){\n" + 
"    var getCookie = function (name) {\n" + 
"        var strCookie=document.cookie;\n" + 
"        var arrCookie=strCookie.split(\"; \");\n" + 
"        for(var i=0;i<arrCookie.length;i++){\n" + 
"            var arr=arrCookie[i].split(\"=\");\n" + 
"            if(arr[0]==name)return arr[1];\n" + 
"        }\n" + 
"        return null;\n" + 
"    };\n" + 
"    var provinceId=getCookie(\"provinceId\"), gla=getCookie(\"gla\");\n" + 
"    var check2ProvinceIsSame = function(){\n" + 
"      var isSame = false;\n" + 
"\n" + 
"      var glaObj = analysisGla();\n" + 
"\n" + 
"      if(provinceId && glaObj && glaObj.provinceId && glaObj.provinceId == provinceId){\n" + 
"        isSame = true;\n" + 
"      }\n" + 
"\n" + 
"      return isSame;\n" + 
"    };\n" + 
"    var analysisGla = function(){\n" + 
"      if (!gla)  return null;\n" + 
"\n" + 
"      var glaObj = {};\n" + 
"      var glas = gla.split(\"_\");\n" + 
"      var as = glas[0].split(\".\");\n" + 
"      if (as.length < 2)  return null;\n" + 
"      glaObj.provinceId = as[0];\n" + 
"      glaObj.cityId = as[1];\n" + 
"      glaObj.hasUnionSite = false;\n" + 
"      if (glas.length>1 && glas[1] != \"0\") {\n" + 
"        glaObj.hasUnionSite = true;\n" + 
"        glaObj.unionSiteDomain = glas[1];\n" + 
"      };\n" + 
"      glaObj.willingToUnionSite = 1;\n" + 
"      if (glas.length>2 && glas[2] == \"0\") {\n" + 
"        glaObj.willingToUnionSite = 0;\n" + 
"      };\n" + 
"      return glaObj;\n" + 
"    };\n" + 
"\n" + 
"    if(check2ProvinceIsSame()){\n" + 
"      var glaObj = analysisGla();\n" + 
"      if(glaObj && glaObj.unionSiteDomain && glaObj.willingToUnionSite){\n" + 
"        location.href = \"http://\" + glaObj.unionSiteDomain + \".yhd.com\";\n" + 
"      }\n" + 
"    }\n" + 
"  }\n" + 
"})();";

			  boolean escapeUnicode = false;
			  String strIn =	new String();
			String stripConsole = null;
			System.out.println(com.yhd.tools.min.Compressor.compressScript(code, 0, 1, escapeUnicode, stripConsole));
		}
		
		public void js() throws Exception, IOException{
			  boolean escapeUnicode = false;
			  String strIn =	new String(strBuffer.toString().getBytes(encoding));
			String stripConsole = null;
			out.append(com.yhd.tools.min.Compressor.compressScript(strIn, 0, 1, escapeUnicode, stripConsole));

//			InputStream strIn =	 new   ByteArrayInputStream(strBuffer.toString().getBytes("UTF-8"));  
//			Reader in = new InputStreamReader(strIn);		
//		     
//		//	System.out.println(strIn);
//		      JavaScriptCompressor compressor = new JavaScriptCompressor(in, new org.mozilla.javascript.ErrorReporter() {
//					
//					public void warning(String message, String sourceName,
//			                    int line, String lineSource, int lineOffset) {
//			                if (line < 0) {
//			                    System.err.println("\n[WARNING] " + message);
//			                } else {
//			                    System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
//			                }
//			            }
//
//					
//					@SuppressWarnings("restriction")
//					public org.mozilla.javascript.EvaluatorException runtimeError(String message, String sourceName,
//		                    int line, String lineSource, int lineOffset){
//						   error(message, sourceName, line, lineSource, lineOffset);
//			                 new EvaluatorException(message);
//			                 return null;
//					}
//					
//			
//					 public void error(String message, String sourceName,
//			                    int line, String lineSource, int lineOffset) {
//			                if (line < 0) {
//			                    System.err.println("\n[ERROR] " + message);
//			                } else {
//			                    System.err.println("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
//			                }
//			            }
//				});
//		
//				compressor.compress(out, linebreakpos, munge, verbose,preserveAllSemiColons, disableOptimizations);
//			
		}

		@Override
		public void flush() throws IOException {
			
			   try {
					this.js();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  out.flush();  
			
		}

		@Override
		public void close() throws IOException {
			 
		     out.close();  
			
		}
	
	  
    

		
    }
    
  
    /** 
     * 输出流的包装器(转换大写字母) 
     */  
    private static class PageJsFilterWriter extends Writer {  
  
        private final Writer out;  
  
        PageJsFilterWriter(Writer out) {  
            this.out = out;  
        }  
        

        public void write(char[] cbuf, int off, int len) throws IOException {  
            char[] transformedCbuf = new char[len];  
     	   System.out.println("bb"+String.valueOf(cbuf));
            for (int i = 0; i < len; i++) {  
                transformedCbuf[i] = Character.toUpperCase(cbuf[i + off]);  
            }  
            out.write(transformedCbuf);  
        }  
  
        public void flush() throws IOException {  
            out.flush();  
        }  
  
        public void close() throws IOException {  
            out.close();  
        }  
    }
	public Map<String,List<File>> putList1(Map<String,List<File>> map,String key,File value){
		if(map.containsKey(key)){
			List list=map.get(key);
			list.add(value);
			map.put(key, list);
		}else{
			List list=new ArrayList();
			list.add(value);
			map.put(key, list);
		}
		return map;
	}
	
	public void compressFile(String key,String path) throws Exception {
		File file=new File(path);
		String fileName = file.getName();
		Reader in = new FileReader(file);
		
		String genPath = null;
		if (fileName.endsWith(".js")) {
			File compressed = new File(genPath + "\\js\\" + file.getName());
			if (!compressed.exists()) {
				compressed.createNewFile();
			}
			Writer out = new FileWriter(compressed);

			JavaScriptCompressor compressor = new JavaScriptCompressor(in, new org.mozilla.javascript.ErrorReporter() {
				
				public void warning(String arg0, String arg1, int arg2, String arg3,
						int arg4) {
					// TODO Auto-generated method stub
					
				}
				
				public org.mozilla.javascript.EvaluatorException runtimeError(String arg0,
						String arg1, int arg2, String arg3, int arg4) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public void error(String arg0, String arg1, int arg2, String arg3, int arg4) {
					// TODO Auto-generated method stub
					
				}
			});
			compressor.compress(out, linebreakpos, munge, verbose,
					preserveAllSemiColons, disableOptimizations);
			//jsGenFiles.add(compressed);
			putList1(jsGenMap, key, compressed);
			out.close();
		} else if (fileName.endsWith(".css")) {
			File compressed = new File(genPath + "\\css\\" + file.getName());
			if (!compressed.exists()) {
				compressed.createNewFile();
			}
			Writer out = new FileWriter(compressed);
			CssCompressor compressor = new CssCompressor(in);
			compressor.compress(out, linebreakpos);
			//cssGenFiles.add(compressed);
			putList1(cssGenMap, key, compressed);
			out.close();
		}
		in.close();
	}


  

}

