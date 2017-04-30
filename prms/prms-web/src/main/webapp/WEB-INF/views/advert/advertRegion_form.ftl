<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>广告区域</title>
</head>
<body>
            <ul class="nav nav-tabs" style="padding-left: 1%;">
                <li class="active"><a href="#">广告区域<#if data ??>编辑<#else>添加</#if></a></li>
            </ul>

           <div class="tab-content">
               <div class="tab-pane fade in active" id="addUser">

                   <!--新增1--start-->
                   <div class="row">
                       <div class="col-lg-10 col-md-12 col-sm-12">
                           <form id="myform" action="${BasePath !}/advert/advertRegion_save.do" method="post">
                           <input type="hidden" id="id" name="id" value="${(data.id) !}">  
                           <input type="hidden" id="supportTypes" name="supportTypes" value="${(data.supportTypes) !}">  
                                             
                           <div class="addForm1">
		                        <div id="error_con" class="tips-form">
		                            <ul></ul>
		                        </div>
                               <div class="tips-form"></div>
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>所属区域编码：</label>
                                       <div class="div-form"><input class="form-control input-sm txt_mid"
                                         type="text" id="number" name="number" value="${(data.number) !}" data-rule-required="true" data-msg-required="所属区域编码不能为空">
                          	 			</div>
                                   </div>                  
                               </div>  
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>所属区域名称：</label>
                                       <div class="div-form"><input class="form-control input-sm txt_mid"
                                         type="text" id="name" name="name" value="${(data.name) !}" value="${(data.number) !}" data-rule-required="true" data-msg-required="所属区域名称不能为空">
                                        </div>
                                   </div>
                               </div>  
                             <div class="form-tr">
                                   <div class="form-td">
                                       <label>上级节点：</label>
                                       <div class="div-form">
                                        <div class="f7" >
                                               <input type="hidden"  id="parentId" name="parent.id" value="${(data.parent.id) !}"/>
                                               <input class="form-control input-sm txt_mid" 
                                               type="text" id="parentName" name="parent.name" readonly="readonly" value="${(data.parent.name) !}">
                                               <span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span>
                                               <span class="selectBtn" onclick='simpleTreeDataPiker("选择区域","/advert/ajax_advertRegionSimpleTree.do","parentName","parentId")'>选</span>
                                           </div>
                                        </div>
                                   </div>                           
                               </div>  
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>序号：</label>
                                       <div class="div-form"><input class="form-control input-sm txt_mid"
                                         type="text" id="sort" name="sort" value="${(data.sort) !}"  onkeyup="onlyInputNum(this);" data-rule-required="true" data-msg-required="序号不能为空">
                                        </div>
                                   </div>
                                   <div class="form-td">
                                       <div class="div-form">
                                       <div id="advertTypes_checkboxs" class="checkbox wMonospaced">
                                                <label class="checkbox-inline" style="width: auto;" onclick="checkSystem();"> 
                                            	<input name="input_isSystem"  id="input_isSystem" type="checkbox"  <#if (data.isSystem) ??><#if data.isSystem == 1>checked="checked"</#if></#if>  />系统数据 
                                            	<input name="isSystem"  id="isSystem" type="hidden" value="${(data.isSystem)! }" />
                                             </label>
                                        </div>
                                        </div>
                               </div>
                               </div>  
                                <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>限制广告数量：</label>
                                       <div class="div-form"><input class="form-control input-sm txt_mid"
                                         type="text" id="limitCount" name="limitCount" value="${(data.limitCount) !}" onkeyup="onlyInputNum(this);"  data-rule-required="true" data-msg-required="数量不能为空">
                                        </div>
                                   </div>
                               </div> 
                                <div class="form-tr">
                                   <div class="form-td">
                                       <label>支持广告类型：</label>
                                       <div class="div-form">
                                       <div id="advertTypes_checkboxs">
                                         <#if advertTypeList ??>
                                            <#list advertTypeList as item > 
                                                <div><label class="checkbox-inline" style="width: auto;">
                                            	<input  name="check_supportTypes" id="${item.value}" type="checkbox" value="${item.value}"/>${item.name}
                                            	</label>  </div>                             
                                            </#list>
                                          </#if>
                                        </div>
                                        </div>
                                   </div>
                               </div>
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label>备注：</label>
                                       <div class="div-form" width="250px">
                                         <textarea  style="height:98px;width:98%" id="remarks" name="remarks">${(data.remarks) !}</textarea>
                                        </div>
                                   </div>
                               </div>
                               <div class="form-tr">
	                            <div class="form-group btn-div">
								<@permission name="prms:advertRegion:edit">
								<input type="button" onclick="submitForm();" class="btn btn-primary" value="保存">
								</@permission>
								<input type="button" class="btn btn-default" value="返回" onclick="<#if viewtype??><#if viewtype != '0'>history.go(-1);<#else>isReturn()</#if><#else>isReturn()</#if>">
								</div>
							</div>
                           </div>
                           </form>
                       </div>
                   </div>
                   <!--新增1--end-->
                                      
               </div>
           </div>
   
<#include "../common/select.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<script src="${BasePath !}/asset/js/common/util.js?v=${ver !}"></script>
<script type="text/javascript">
    $(function(){
        executeValidateFrom('myform');
        $(".input-select2").select2();
        innitSupportTypesCheced();
    });
    function checkSystem(){
    	if($('#input_isSystem').is(':checked')) {
    		$('#isSystem').val(1);
    	}else{
    		$('#isSystem').val(0);
    	}
    }
    function submitForm(){
    	var supportTypes="";
    	$("input[name='check_supportTypes']:checkbox:checked").each(function(){
    		var val= $(this).val();
    		supportTypes+=val+",";
    	});
    	if (supportTypes.indexOf(",") != -1) {
    		supportTypes = supportTypes.substring(0, supportTypes.length - 1);
    	}else{
    		supportTypes="";
    	}
    	$("#supportTypes").val(supportTypes);
    	$("#myform").submit();
    }
    
    function innitSupportTypesCheced(){
    	supportTypes = $("#supportTypes").val();
    	if(supportTypes&&supportTypes!=""){
    		var supportTypesArr = supportTypes.split(",");
    		$.each(supportTypesArr, function() { 
    		$("[value = "+this+"]:checkbox").attr("checked", true);
    		});
    	}
    }
    //设置为查看
    var viewtype = "${viewtype!'0'}";
    if(viewtype == '1'){
        $('form').find('input').attr("readonly","readonly");
        $('form').find('radio').attr("readonly","readonly");
        $('form').find('select').attr("readonly","readonly");
    }
    
    function simpleTreeDataPiker(title,url,id,id1,call,pId) {
    	pId = (pId==null || pId=="" || typeof(pId)=="undefined")? "pId" : pId;
    	var tree_setting = {
    		data : {// 数据
    			simpleData :{
    				enable: true,
    				idKey: "id",
    				pIdKey: "pId",
    				rootPId: null
    			},
    			key : {
    				url : ""
    			}
    		},
    		view:{
    			selectedMulti: false
    		}
    	};
    	$("#"+id).blur();
    	
    	$.post(rootPath+ url,{isParent:"YES"},function(res){
    		if(res.status=="SUCCESS"){
				var content = '<div class="zTreeDemoBackground left"><ul id="show_tree" class="ztree"></ul></div>';
				$.frontEngineDialog.executeDialog(
						'showTree',
						title,
						content,
						"400px",
						"350px",
						function(){
							var treeObj = $.fn.zTree.getZTreeObj("show_tree");
							var nodes = treeObj.getSelectedNodes();
							if(nodes != null && nodes != ""){
								$("#"+id).val(nodes[0].name);
								$("#"+id1).val(nodes[0].id);
							}
						}
				);

				$.fn.zTree.init($("#show_tree"), tree_setting,res.data);
    		}
    	},'json');
    }
</script>
</body>
</html>