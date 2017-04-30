<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>新增供应商</title>
	<style type="text/css">
	.addForm1 label{
		width: 140px;
	}
	</style>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/validation/jquery.validate.js?v=${ver !}"></script>
</head>
<body>
<ul class="nav nav-tabs" style="padding-left: 1%;">
      <li class="active"><a href="#">商品切换</a></li>
</ul>
    <div class="tab-content">
         <div class="tab-pane fade in active" id="addVendor">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/commoditySwitch/switchStatus.do" method="post" name="myform">
	        <input type="hidden" id="id" name="id" value="${(commodity.id) !}">
	        <div class="addForm1">
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	            <div class="form-tr">
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>商品编码：</label>
	                    <div class="div-form">
		                    <input class="form-control input-sm txt_mid"   readonly="readonly"
		                    type="text" id="code" name="code" value="${(commodity.code) !}">                     
	       	 			</div>
	                </div>
	               <div class="form-td">
	                    <label>商品条形码：</label>
	       	 			<div class="div-form">
	                    	<div  id="vendorPartner" >
                            	<input class="form-control input-sm txt_mid" type="text" 
                            		id="commodityBarCode" name="commodityBarCode" value="${(commodity.barCode) !}" readonly="readonly">
                        	</div>
	                    </div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>商品标题：</label>
	       	 			<div class="div-form">
	                    	<div id="commodityPartner" >
                            	<input class="form-control input-sm txt_mid" type="text" 
                            		id="commodityTitle" name="commodityTitle" value="${(commodity.title) !}" readonly="readonly">
                        	</div>
	                    </div>
	                </div>
	                <div class="form-td">
	                
	                </div>
	            </div>
	            
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>商品分类：</label>
	                    <div class="div-form">
		                    <input class="form-control input-sm txt_mid"  readonly="readonly"
		                    type="text" id="commodityCategory" name="commodityCategory" value="${(commodity.categoryAttributeName) !}">                     
	       	 			</div>
	                </div>
	                
	                 <div class="form-td">
	                    <label>供应商：</label>
	                    <div class="div-form">
		                    <input class="form-control input-sm txt_mid" readonly="readonly"
		                    type="text" id="commodityBrandName" name="commodityBrandName" value="${(commodity.vendorName) !}">                     
	       	 			</div>
	                </div>
	            </div>
	            
	            <div class="form-tr">
	               <div class="form-td">
	                   <label></label>
	                       <div class="div-form wMonospaced">
	                           <label style="width:auto;"><input type="checkbox" name="warehouse" id="warehouse" onclick="changeCheck(this)"
	                            <#if (commodity.warehouse)?? && commodity.warehouse == "0">checked="checked" value="0" <#else> value="0" </#if>
	                               >是否在供应商仓库</label>
	                            
	                            <label style="width:auto;"><input type="checkbox"  name="entityStorage" id="entityStorage"
	                            <#if (commodity.warehouse)?? && commodity.warehouse != "0"> disabled="true" </#if> 
	                            <#if (commodity.entityStorage)?? && commodity.entityStorage == "0">checked="checked" value="0" <#else> value="0" </#if>
	                              >实物是否入库</label>   
	                       </div>
	               </div>
	           </div>
	           
	           <div class="form-tr">
	                <div class="form-td">
	                    <label>区域性商品：</label>
                        <div class="div-form">                    
                            <select class="input-select2" id="areaCategory" name="areaCategory" >  
                                <option value="0" <#if (commodity.areaCategory)?? && commodity.areaCategory == "0">selected</#if> >区域性商品</option>
                                <option value="1" <#if (commodity.areaCategory)?? && commodity.areaCategory == "1">selected</#if> >非区域性商品</option>
                            </select>
                        </div>
	                </div>
	                
	                <div class="form-td">
	                </div>
              </div>
              
              <div class="form-tr">
	                <div class="form-td">
	                    <label>可销售数量限制： </label>
                        <div class="div-form"> 
                            <select class="input-select2" id="stockLimit" name="stockLimit" onchange="gradeChange()" >  
                                <option value="0" <#if (commodity.stockLimit)?? && commodity.stockLimit == "0">selected</#if> >WMS决定商品可销售数量</option>
                                <option value="1" <#if (commodity.stockLimit)?? && commodity.stockLimit == "1">selected</#if> >自定义商品可销售数量</option>
                            </select>
                        </div>
	                </div>
	                
	                <div class="form-td">
	                </div>
              </div>
	            
	            <div class="form-tr">
	                <div class="form-group btn-div">
	                    <input type="submit" id="submitBtn" class="btn btn-primary" value="保存">
	                    <input type="button" class="btn btn-default" value="返回" onclick="<#if viewstatus??>history.go(-1);<#else>isReturn()</#if>">
	                </div>
	            </div>
	        </div>
	       </form>
	    </div>
	</div>
	<!--新增1--end-->	
                                      
     </div>
 </div>
<script type="text/javascript">

$(function(){
	executeValidateFrom('myform');
    var stockLimit = $("#stockLimit");
	stockLimit.change();
});

function CheckInputIntFloat(oInput) {
	  if ('' != oInput.value.replace(/[\d]+/, '')) {
	    oInput.value = oInput.value.match(/[\d]+/) == null ? '' : oInput.value.match(/[\d]+/);
	  }
}

function CheckInputIntFloatByDay(oInput) {
	  if ('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,1}/, '')) {
	    oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,1}/) == null ? '' : oInput.value.match(/\d{1,}\.{0,1}\d{0,1}/);
	  }
}


//下拉框选中触发事件
function gradeChange(){
    var objS = document.getElementById("stockLimit");
    var grade = objS.selectedIndex;
    if(grade == "0"){
    	document.getElementById("warehouse").disabled=true; 
    	$('#warehouse').removeAttr('checked');
    	$('#entityStorage').removeAttr('checked');
    	document.getElementById("entityStorage").disabled=true;
    }
	if(grade == "1"){
		document.getElementById("warehouse").disabled=false; 
    }
 }
 
function changeCheck(obj){
	if (obj.checked == true){
		document.getElementById("entityStorage").disabled=false; 
	}else{
		 $('#entityStorage').removeAttr('checked');
		document.getElementById("entityStorage").disabled=true; 
	}
}
	
</script>
</body>
</html>