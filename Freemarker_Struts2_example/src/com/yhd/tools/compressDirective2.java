package com.yhd.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.javascript.ErrorReporter;

import sun.org.mozilla.javascript.internal.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class compressDirective2 implements TemplateDirectiveModel {  

	int linebreakpos = -1;
	boolean munge = true;
	boolean verbose = false;
	boolean preserveAllSemiColons = false;
	boolean disableOptimizations = false;
	public void execute(Environment env, Map params, TemplateModel[] loopVars,  
            TemplateDirectiveBody body) throws TemplateException, IOException {  
        // 检查是否传递参数，此指令禁止传参！  
        if (!params.isEmpty()) {  
            throw new TemplateModelException(  
                    "This directive doesn't allow parameters.");  
        }  

        // 指令内容体不为空  
        if (body != null) {  
     
	   Writer  w = env.getOut();
	
	  
	   PageJsCompressWrite pageJs  = new PageJsCompressWrite(w);
      
	
          body.render(pageJs);  
          pageJs.flush();
         
        } else {  
            throw new RuntimeException("missing body");  
        }  
      
    }  
	private  class PageJsCompressWrite extends Writer {
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

		  public void compress(String code, Writer writer) throws IOException {
		        Reader in = null;
		        try {
		            in = new InputStreamReader(IOUtils.toInputStream(StringUtils.trimToEmpty(code),encoding));
		            JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {
		                public void warning(String message, String sourceName,
		                                    int line, String lineSource, int lineOffset) {
		                    if (line < 0) {
		                        System.err.println("/n[WARNING] " + message);
		                    } else {
		                        System.err.println("/n[WARNING] " + line + ':' + lineOffset + ':' + message);
		                    }
		                }
		                public void error(String message, String sourceName,
		                                  int line, String lineSource, int lineOffset) {
		                    if (line < 0) {
		                        System.err.println("/n[ERROR] " + message);
		                    } else {
		                        System.err.println("/n[ERROR] " + line + ':' + lineOffset + ':' + message);
		                    }
		                }
		                public org.mozilla.javascript.EvaluatorException runtimeError(String message, String sourceName,
		                                                       int line, String lineSource, int lineOffset) {
		                    error(message, sourceName, line, lineSource, lineOffset);
		                    new EvaluatorException(message);
			                 return null;
		                }
		            });
		            compressor.compress(writer, -1, true, false, false, false);
		        } catch (Exception e) {
		        	writer.append(code);
		        
		        	e.printStackTrace();
		        } finally {
		         //   IOUtils.closeQuietly(writer);
		        }
		    }
		
		public void js() throws Exception, IOException{
			ByteArrayInputStream strIn =	 new   ByteArrayInputStream(strBuffer.toString().trim().getBytes());  
			Reader in = new InputStreamReader(strIn);		
			
		      JavaScriptCompressor compressor = new JavaScriptCompressor(in, new org.mozilla.javascript.ErrorReporter() {
					
					public void warning(String message, String sourceName,
			                    int line, String lineSource, int lineOffset) {
			                if (line < 0) {
			                    System.err.println("\n[WARNING] " + message);
			                } else {
			                    System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
			                }
			            }

					
					public org.mozilla.javascript.EvaluatorException runtimeError(String message, String sourceName,
		                    int line, String lineSource, int lineOffset){
						   error(message, sourceName, line, lineSource, lineOffset);
			                 new EvaluatorException(message);
			                 return null;
					}
					
			
					 public void error(String message, String sourceName,
			                    int line, String lineSource, int lineOffset) {
			                if (line < 0) {
			                    System.err.println("\n[ERROR] " + message);
			                } else {
			                    System.err.println("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
			                }
			            }
				});
		
				compressor.compress(out, linebreakpos, munge, verbose,preserveAllSemiColons, disableOptimizations);
			
		}
		

		@Override
		public void flush() throws IOException {
			
			   try {
				 this.compress(strBuffer.toString(), out);
				//	this.js();
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
    
  
   
	
	


  

}