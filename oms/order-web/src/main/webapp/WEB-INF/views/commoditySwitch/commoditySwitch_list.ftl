<html>
<head>
<meta name="decorator" content="list" />
<title>商品管理</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="brandList">
			<div class="col-md-12">
				<div class="search">
					<form  action="${BasePath !}/commoditySwitch/list.do" id="myform" name="myform" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr"><br/>
								<div class="form-td">
									<label>商品条形码：</label> 
									<div class="div-form"><input id="barCode" name="barCode" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.barCode) !}"></div>
								</div>
								<div class="form-td">
									<label>商品标题：</label> 
									<div class="div-form"><input id="title"  name="title" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.name) !}"></div>
								</div>
								<div class="form-td">
									<label>商品编码：</label> 
									<div class="div-form"><input id="code" name="code" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.code) !}"></div>
								</div>
								
							    <div class="form-td"> 
                                 <label>商品状态：</label> 
                                 <div class="div-form">                                      
                                         <select class="input-select2" id="status" name="status">
                                           <option value="">--全部--</option> 
                                     		<option value="COMMODITY_STATUS_ENTRYING" <#if commodity.status??><#if commodity.status == 'COMMODITY_STATUS_ENTRYING'>selected="selected"</#if></#if>>录入中</option> 
                                     		<option value="COMMODITY_STATUS_ENTRYED" <#if commodity.status??><#if commodity.status == 'COMMODITY_STATUS_ENTRYED'>selected="selected"</#if></#if>>已录入</option> 
                                     		<option value="COMMODITY_STATUS_SHELVES" <#if commodity.status??><#if commodity.status == 'COMMODITY_STATUS_SHELVES'>selected="selected"</#if></#if>>下架</option> 
                                      	  </select>                   
                                    </div> 
                                </div> 
								
								<div class="form-td">
									<label>区域性商品：</label> 
									<div class="div-form">
										<select class="input-select2" id="areaCategory" name="areaCategory">  
											<option value="" >--全部--</option>
                                            <option value="0" <#if (commodity.areaCategory)?? && commodity.areaCategory == "0">selected</#if> >区域性商品</option>
                                            <option value="1" <#if (commodity.areaCategory)?? && commodity.areaCategory == "1">selected</#if> >非区域性商品</option>
                                        </select>
									</div>
								</div>
								
								<div class="form-td">
									<label>供应商：</label> 
									<div class="div-form">
										<div class="f7">
				                    		<input type="hidden" id="vendorId" name="vendorId" value="${(commodity.vendorId) !}"/>
			                            	<input class="form-control input-sm txt_mid" type="text" 
			                            		id="vendorName" name="vendorName" value="${(commodity.vendorName) !}" readonly="readonly"/>
			                            	<span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span>
			                            	<span class="selectBtn" onclick="vendorPopupFrom()">选</span>
			                        	</div>
									</div>
								</div>
								
								<div class="form-td">
                                    <label>商品分类：</label>
                                       <div class="div-form">
                                        <div class="f7" >
                                        <input type="hidden" id="parentId" name="parent.id" value="${(category.parent.id) !""}">
                                       	<input class="form-control input-sm txt_mid" 
                                       	type="text" id="parentName" name="parent.name" value="${(category.parent.name) !''}" 
                                       	data-rule-required="true"  data-msg-required="请选择商品分类" readonly="readonly"/>
                                       	<span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span>
                                       	<span class="selectBtn" onclick='showTreeAndisParent("请选择商品类别","/purchaseArea/getCategoryAJax.do","parentName","parentId","","parentId")'>选</span>
                                       	</div>
                                       </div>
                                </div>
								<div class="form-td">
									<label></label>
									<div class="div-form wMonospaced">  
										<label style="width:auto;">
										<input type="checkbox" name="warehouse" id="warehouse" value="0" 
										<#if (commodity.warehouse)?? && commodity.warehouse == "0">checked="checked"</#if> />在供应商仓库</label>
									</div>
								</div>
								<div class="form-td">
									<label>可销售数量限制： </label>
									<div class="div-form">                    
                                          <select class="input-select2" id="stockLimit" name="stockLimit" onchange="gradeChange()">  
                                          <option value="" >--全部--</option>
                                           <option value="0" <#if (commodity.stockLimit)?? && commodity.stockLimit == "0">selected</#if> >WMS决定商品可销售数量</option>
                                           <option value="1" <#if (commodity.stockLimit)?? && commodity.stockLimit == "1">selected</#if> >自定义商品可销售数量</option>
                                       </select>
                                       </div>
								</div>
								
							</div>
						</div>
						<div class="btn-div3">
							<button id="find-page-orderby-button"  class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<a href="${BasePath !}/commoditySwitch/list.do" class="btn btn-primary btn-sm"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
							<!--<a href="${BasePath !}/commodity/list.do" class="btn btn-primary btn-sm"><i class="fa fa-arrow-circle-down"></i>&nbsp;&nbsp;导入</a>-->
							<!--<a href="${BasePath !}/commodity/list.do" class="btn btn-primary btn-sm"><i class="fa fa-arrow-circle-up"></i>&nbsp;&nbsp;导出</a>-->
						</div>
						<div class="col-md-12">
							<div class="search"></div>
						</div>	
						<!-- <div class="navtabs-title">
							<input  type="hidden" name="tblshow" id="tblshow" value="<#if tblshow?? >${(tblshow) !}<#else>1</#if>" /> 
							<input  type="hidden" name="status" id="status" value = "<#if commodity.status?? >${(commodity.status) !}<#else></#if>" />
							<ul class="nav nav-tabs" style="padding-left: 1%;">
								<li id="tblshow_1" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "1">class="active" </#if>><a href="javascript:void(0);">全部</a></li>
								<li id="tblshow_2" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "2">class="active" </#if>><a href="javascript:void(0);" >已上架</a></li>
								<li id="tblshow_3" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "3">class="active" </#if>><a href="javascript:void(0);" >已录入</a></li>
								<li id="tblshow_4" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "4">class="active" </#if>><a href="javascript:void(0);" >已下架</a></li>
							</ul>
						</div> -->
					</form>
				</div>
			</div>
			<div class="tab-content">
				<table class="table table-hover table-striped bor2 table-common">
					<thead>
						<tr>
							<th width="1%"  class="tab-td-center"><input name="name1" type="checkbox" value=""/></th>
							<th class="tab-td-center">序号</th>
							<th class="tab-td-center">商品条形码</th>
							<th class="tab-td-center" width="300px" >商品标题</th>
							<th class="tab-td-center">商品编码</th>
							<th class="tab-td-center">状态</th>
							<th class="tab-td-center">可销售数量限制</th>
							<th class="tab-td-center">是否区域性商品</th>
							<th class="tab-td-center">最近操作时间</th>
							<th class="tab-td-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<#if commodityList?? > 
						<#list commodityList as item >
						<tr ondblclick="view('${(item.id) !}','${(item.code) !}')">
							<td class="tab-td-center"><input name="name1" type="checkbox" value=""/></td>
							<td class="tab-td-center">${item_index + 1}</td>
							<td class="tab-td-center">${(item.barCode) !}</td>
							<td class="tab-td-center">${(item.title) !}</td>
							<td class="tab-td-center">${(item.code) !}</td>
							<td class="tab-td-center">
								<#if commodityStatusList?? > 
									<#list commodityStatusList as items >
										<#if items.getValue()==item.status>${(items.getName())}</#if>
									</#list>  
								</#if>
							</td>
							<td class="tab-td-center">
								<#if (item.stockLimit)?? && item.stockLimit == "0">WMS决定商品可销售数量<#else>自定义商品可销售数量</#if>
							</td>  
							<td class="tab-td-center">
								<#if (item.areaCategory)?? && item.areaCategory == "0">区域性商品<#else>非区域性商品</#if>
							</td>  
							<td class="tab-td-center"><#if item.commoditySwitchLog.lastUpdateDate?? >${item.commoditySwitchLog.lastUpdateDate?string("yyyy-MM-dd HH:mm:ss") !}<#else></#if></td>  
							<td class="tab-td-center">
							<@permission name="order:commoditySwitch:switchView">
								<a href="#" onclick="checkCommodityStatus('${(item.id) !}')" >商品状态切换</a> |
							</@permission>	
							<@permission name="order:commoditySwitch:commoditySwitchLogDeail">
								<a href="${BasePath !}/commoditySwitch/commoditySwitchLogDeail.do?commodityCode=${(item.code) !}" >商品状态切换日志</a>
							</@permission>	
							</td>  
						</tr>
						</#list>  
						</#if>
					</tbody>
				</table>
			</div>
			
			
			<#include "../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>



<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<link rel="stylesheet"	href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">

<script type="text/javascript">

$(function(){
	gradeChange();
});
    
   function view(id,code) {
      // window.location.href = '${BasePath !}/commodity/form.do?id='+ id + "&commodityCode="+code + "&viewType=1";
   } 
   
   function checkCommodityStatus(id) {
	   $.ajax({
			url: rootPath+"/commoditySwitch/checkCommodityStatus.do", 
			type:'POST',
			data:{"commodityId":id},
			async:false,
			dataType: 'json',
			success: function(data){
				if(data == null || data == "")return;
				if(data=="success"){
					location.href=rootPath+"/commoditySwitch/switchView.do?commodityId="+id;
				}else{
					$.frontEngineDialog.executeDialogContentTime('商品状态切换失败，原因：当前商品状态已经不是“录入中、已录入、已下架”请返回后重试');
				}
			}//end success 
		});//end ajax	         
    }
   
   $(".input-select2").select2();
   function onSubmitTn(tblshow){
	   var ids=tblshow.id;
	   ids=ids.split("_");
	   var tblshowName=ids[0];
	   var tblshowValue=ids[1];
	   var status="";
	   if(tblshowValue=="2"){
		   status="COMMODITY_STATUS_ADDED";
	   }else if(tblshowValue=="3"){
		   status="COMMODITY_STATUS_ENTRYED";
	   }else if(tblshowValue=="4"){
		   status="COMMODITY_STATUS_SHELVES";
	   }
	   $("#status").val(status);
	   $("#tblshow").val(tblshowValue);
	   $("#myform").submit();
   }
    
	//选择供应商
    function vendorPopupFrom() {
		$.frontEngineDialog.executeIframeDialog('vendor_select_commodity', '选择供应商', rootPath
			+ '/purchaseArea/listSelectVendor.do', '1000', '600');
    }
    
    /**
     * 弹出树形选择并且验证是否有子节点
     * title:弹出的标题
     * url：树形地址
     * id：操作元素弹出树形的id
     * id1：选择树形id保存在id1元素上
     * call：单击确定调用的函数
     * pId:父节点id，默认pId
     */
    function showTreeAndisParent(title,url,id,id1,call,pId,errorData) {
    	pId = (pId==null || pId=="" || typeof(pId)=="undefined")? "pId" : pId;
    	var tree_setting = {
    		data : {// 数据
    			simpleData : {
    				enable : true,// true / false 分别表示 使用 / 不使用 简单数据模式
    								// 默认false，一般使用简单数据方式
    				idKey : "id",// 节点数据中保存唯一标识的属性名称 默认值："id"
    				pIdKey : pId// 点数据中保存其父节点唯一标识的属性名称 默认值："pId"
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
    	$.frontEngineAjax.executeAjaxPost(
    					rootPath + url,
    					"",
    					function(ret) {
    						var content = '<div class="left"><div><input type="text" class="form-control input-sm txt_mid" style="height:31px;" id="shwo_tree_search_text"><button id="show_tree_search_btn" class="btn btn-primary btn-sm" style="margin-left:5px;" onclick="showTreeSearchClick()"><i class="fa fa-search"></i></button><div><ul id="show_tree" class="ztree"></ul></div>';
    						$.frontEngineDialog.executeDialog(
    								'showTree',
    								title,
    								content,
    								"400px",
    								"450px",
    								function(){
    									var treeObj = $.fn.zTree.getZTreeObj("show_tree");
    									var nodes = treeObj.getSelectedNodes();
    									
    									if (nodes.length > 0) {
    										var isParent = nodes[0].isParent;
    										if(isParent)
    										{
    											$("#"+errorData).val(0);
    											$.frontEngineDialog.executeDialogOK('信息','只能选择第三级商品分类', "");
    											return false;
    										}
    										
    									}
    									
    									if(nodes != null && nodes != ""){
    										$("#"+id).val(nodes[0].name);
    										$("#"+id1).val(nodes[0].id);
    									}
    								}
    						);
    						$.fn.zTree.init($("#show_tree"), tree_setting, ret);
    					}, function(err) {
    						$.frontEngineDialog.executeDialogOK('错误', err);
    					});
    }
    
    
  //下拉框选中触发事件
    function gradeChange(){
        var objS = document.getElementById("stockLimit");
        var grade = objS.selectedIndex;
        if(grade == "1"){
        	document.getElementById("warehouse").disabled=true; 
        	$('#warehouse').removeAttr('checked');
        } else{
    		document.getElementById("warehouse").disabled=false; 
        }
   }

</script>
</body>
</html>
