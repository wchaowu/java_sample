<#include "./macroTools.ftl">
<html>
<head><title>ViralPatel.net - FreeMarker Spring MVC Hello World</title>

<style>
body, input {
	font-family: Calibri, Arial;
	margin: 0px;
	padding: 0px;
}
#header h2 {
	color: white;
	background-color: #3275A8;
	height: 50px;
	padding: 5px 0 0 5px;
	font-size: 22px;
}
	
.datatable {margin-bottom:5px;border:1px solid #eee;border-collapse:collapse;width:400px;max-width:100%;font-family:Calibri}
.datatable th {padding:3px;border:1px solid #888;height:30px;background-color:#B2D487;text-align:center;vertical-align:middle;color:#444444}
.datatable tr {border:1px solid #888}
.datatable tr.odd {background-color:#eee}
.datatable td {padding:2px;border:1px solid #888}
#content { padding 5px; margin: 5px; text-align: center}

fieldset { width: 300px; padding: 5px; margin-bottom: 0px; }
legend { font-weight: bold; }
</style>

<body>
<div id="header">

</div>

<div id="content">
  
  
  <fieldset>
  	<legend>http://www.yhd.com</legend>
	</fieldset>
  <br/>
  <table class="datatable">
  	<tr>
  		<th>Firstname</th>  <th>Lastname</th>
  	</tr>
    <#list userList as user>
  	<tr>
  		<td>${user.firstname}</td> <td>${user.lastname}</td>
  	</tr>
    </#list>
  </table>
  <script type="text/javascript">
<@compressJs>
function add(){
return 1+2+${scopes};
}
var testInfo = add();
alert(add()+"测试");
var c= testInfo+2;
</@compressJs>
</script>

  <script type="text/javascript">
<@compressJs>
(function(){
  var getCookie = function (name) {
    var strCookie=document.cookie;
    var arrCookie=strCookie.split("; ");
    for(var i=0;i<arrCookie.length;i++){
        var arr=arrCookie[i].split("=");
        if(arr[0]==name)return arr[1];
    }
    return null;
 };
 
 var getQueryStringRegExp = function(name){
    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
    if (reg.test(location.href)){
		return unescape(RegExp.$2.replace(/\+/g, " "));
	} else {
		return "";
	}
 }
 
 var url=location.href;
 
 if(url.indexOf("forceId=")>0){
      var reg = new RegExp("(\\?|&)"+ "forceId" +"=([^&]*)(\\s|&|$)", "i");
 	   if (reg.test(url)){
 	   		var p1=RegExp.$1;
 	   		var p2=RegExp.$2;
 	   		var p3=RegExp.$3 || "";	
 	   		
 	   		if(p1=="?"){
 	   			url=url.replace(p1+"forceId="+p2+p3,"?");
 	   		}else{
 	   		    url=url.replace(p1+"forceId="+p2+p3,p3);
 	   		}
 	   		var len=url.length;
 	   		if(url.charAt(len-1)=="?" || url.charAt(len-1)=="&"){
 	   		   url=url.substring(0,len-1);
 	   		}
 	   		
 	   		
 	   		 if(history.replaceState){
		          //改变当前url
		          history.replaceState({},"",url);
		      }else{
		      	  var provinceId=getCookie("provinceId");
		      	  if(provinceId){
		      	  	location.replace(url);
		      	  }
		      }
 	   }	
     
 }

})();


</@compressJs>
</script>
<script>
<@compressJs>
(function(){
window["ajaxFindPromAdvData"] = {};
function lunboDomReady(func) {
	var detectLunboDomInterval = setInterval(function(){
		var lunbo = document.getElementById("index_menu_carousel");
		if (lunbo) {
			func();
			clearInterval(detectLunboDomInterval);
		}
	}, 100);
}
function replaceCommonAdv() {
	var lunbo = document.getElementById("index_menu_carousel");
	var attr = screen.width>=1280 ? 'wi':'si';
	if (lunbo) {
		var lis = lunbo.getElementsByTagName("li");
		var links = lis.length>0 ? lis[0].getElementsByTagName("a") : [];
		
		for (var i=0;i<links.length;i++) {
			var link = links[i];
			var pic = link.getElementsByTagName("img")[0];
			
			if (pic.getAttribute("data-done")=='1' || !pic.getAttribute(attr)) {
				continue;
			}
			pic.setAttribute("src", pic.getAttribute(attr));
			pic.setAttribute("data-done", "1");
			pic.removeAttribute("si");
			pic.removeAttribute("wi");
			
			if (pic.getAttribute("id") == "lunbo_1") {
				var imgFunc = function() {
					var global = window["global"] || (window["global"] = {});
					var vars = global.vars = (global.vars || {});
					global.vars.customInteractTime = global.vars.customInteractTime || new Date().getTime();
					pic.setAttribute("data-loaded", "1");
				};
				if (document.addEventListener) {
					pic.addEventListener("load", imgFunc);
				} else if (document.attachEvent) {
					pic.attachEvent("onload", imgFunc);
				}
			
			}
		}
	}
	var promoShow = document.getElementById("promo_show");
	if (promoShow) {
		promoShow.setAttribute("data-ajax-done", "1");
	}
}
lunboDomReady(replaceCommonAdv);
})();


</@compressJs>


<@compressJs>
var openUnionSiteFlag = 1;
(function(){
	if(typeof openUnionSiteFlag != "undefined" && openUnionSiteFlag != "0"){
		var getCookie = function (name) {
		    var strCookie=document.cookie;
		    var arrCookie=strCookie.split("; ");
		    for(var i=0;i<arrCookie.length;i++){
		        var arr=arrCookie[i].split("=");
		        if(arr[0]==name)return arr[1];
		    }
		    return null;
		};		
		var provinceId=getCookie("provinceId"), gla=getCookie("gla");
		var check2ProvinceIsSame = function(){
			var isSame = false;
	
			var glaObj = analysisGla();
	
			if(provinceId && glaObj && glaObj.provinceId && glaObj.provinceId == provinceId){
				isSame = true;
			}
	
			return isSame;
		};
		var analysisGla = function(){
			if (!gla)  return null;
			
			var glaObj = {};
			var glas = gla.split("_");
			var as = glas[0].split(".");
			if (as.length < 2)  return null;
			glaObj.provinceId = as[0];
			glaObj.cityId = as[1];
			glaObj.hasUnionSite = false;
			if (glas.length>1 && glas[1] != "0") {
				glaObj.hasUnionSite = true;
				glaObj.unionSiteDomain = glas[1];
			};
			glaObj.willingToUnionSite = 1;
			if (glas.length>2 && glas[2] == "0") {
				glaObj.willingToUnionSite = 0; 
			};
			return glaObj;
		};

		if(check2ProvinceIsSame()){
			var glaObj = analysisGla();
			if(glaObj && glaObj.unionSiteDomain && glaObj.willingToUnionSite){
				location.href = "http://" + glaObj.unionSiteDomain + ".yhd.com";
			}
		}
	}
})();


</@compressJs>
</script>
</div>  
</body>
</html>  