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
					<form id="myform" name="myform" action="${BasePath !}/stock/list.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr"><br/>
								<div class="form-td">
									<label>商品名称：</label> 
									<div class="div-form"><input id="name"  name="name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.name) !}"></div>
								</div>
								<div class="form-td">
									<label>SKU条形码：</label> 
									<div class="div-form"><input id="barCode"  name="barCode" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.barCode) !}"></div>
								</div>
								<div class="form-td">
									<label>SKU编码：</label> 
									<div class="div-form"><input id="skuCode" name="skuCode" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.skuCode) !}"></div>
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
				                    	<div class="f7" id="vendorPartner">
				                    		<input type="hidden" id="vendorId" name="vendorId" value="${(commodity.vendorId) !}"/>
				                    		<input type="hidden" id="vendorCode" name="vendorCode" value="${(commodity.vendorCode) !}"/>
			                            	<input class="form-control input-sm txt_mid" type="text" 
			                            		id="vendorName" name="vendorName" value="${(commodity.vendorName) !}" readonly="readonly">
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
                                       	data-rule-required="true"  data-msg-required="请选择商品分类" readonly="readonly" />
                                       	<span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span>
                                       	<span class="selectBtn" 
                                       	onclick='showTreeAndisParent("请选择商品类别","/purchaseArea/getCategoryAJax.do","parentName","parentId","","parentId")'>选</span>
                                       	</div>
                                       </div>
                                </div>
								<div class="form-td">
									<label></label>
									<div class="div-form wMonospaced">  
										<label style="width:auto;"><input type="checkbox" name="warehouse" id="warehouse" <#if (commodity.warehouse)?? && commodity.warehouse == "0">checked="checked" value="0"<#else> value="0"</#if>  >在供应商仓库</label>
									</div>
								</div>
								<div class="form-td">
									<label>可销售数量限制： </label>
									<div class="div-form">                    
                                          <select class="input-select2" id="stockLimit" name="stockLimit" onchange="gradeChange()">  
                                           <option value="1" <#if (commodity.stockLimit)?? && commodity.stockLimit == "1">selected</#if> >自定义商品可销售数量</option>
                                           <option value="0" <#if (commodity.stockLimit)?? && commodity.stockLimit == "0">selected</#if> >WMS决定商品可销售数量</option>
                                       	  </select>
                                       </div>
								</div>
								<!-- <div class="form-td">  
                                        <label>库存状态：</label>
                                        <div class="div-form">                    
                                           <select class="input-select2" id="actFlag" name="actFlag">  
                                           <option value="2">--全部--</option>
                                            <option value="0" <#if (actFlag)?? && actFlag == "0">selected</#if> >启用</option>
                                            <option value="1" <#if (actFlag)?? && actFlag == "1">selected</#if> >禁用</option>
                                        </select>
                                        </div>
                                    </div> -->
                                    
								 <div class="form-td">
									<label>是否共享： </label>
									<div class="div-form">                    
                                          <select class="input-select2" id="isMany" name="isMany" onchange="gradeChange()">  
                                          <option value="" >--全部--</option>
                                           <option value="0" <#if (isMany)?? && isMany == "0">selected</#if> >非共享</option>
                                           <option value="1" <#if (isMany)?? && isMany == "1">selected</#if> >共享</option>
                                       </select>
                                       </div>
								</div> 
							</div>
						</div>
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<a href="${BasePath !}/stock/list.do?tblshow=1" class="btn btn-primary btn-sm"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
						 	<@permission name="order:stock:loadCommodity">
						 		<a id="loadCommodity" href="javascript:void(0);" class="btn btn-primary btn-sm" >&nbsp;&nbsp;加载自定义非区域性商品</a>
							</@permission>
						 	<@permission name="order:stock:import">
						 		<a id="import_button" class="btn btn-primary btn-sm" ><i class="fa fa-plus" ></i>&nbsp;&nbsp;导入</a>
						 	</@permission>
						 	<@permission name="order:stock:export">
				           		<a onclick="exportExcel()" class="btn btn-primary btn-sm" ><i class="fa fa-arrow-circle-up"></i>&nbsp;&nbsp;批量导出</a>
							</@permission>
						</div>
						<div class="col-md-12">
							<div class="search"></div>
						</div>	
						<div class="navtabs-title">
							<input  type="hidden" name="tblshow" id="tblshow" value="<#if tblshow?? >${(tblshow) !}<#else>1</#if>" /> 
							<input  type="hidden" name="status" id="status" value = "<#if commodity.status?? >${(commodity.status) !}<#else></#if>" /> 
							<ul class="nav nav-tabs" style="padding-left: 1%;">
								<li id="tblshow_1" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "1">class="active" </#if>><a href="javascript:void(0);">全部</a></li>
								<li id="tblshow_2" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "2">class="active" </#if>><a href="javascript:void(0);" >已上架</a></li>
								<li id="tblshow_3" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "3">class="active" </#if>><a href="javascript:void(0);" >已录入</a></li>
								<li id="tblshow_4" onclick="onSubmitTn(this)" <#if tblshow?? && tblshow == "4">class="active" </#if>><a href="javascript:void(0);" >已下架</a></li>
							</ul>
						</div>
					</form>
				</div>
			</div>
			<div class="tab-content">
				<table class="table table-hover table-striped bor2 table-common">
					<thead>
						<tr>
							<th width="1%"  class="tab-td-center"><input name="name1" type="checkbox" value=""/></th>
							<th class="tab-td-center">序号</th>
							<!-- <th class="tab-td-center">商品编码</th> -->
							<th class="tab-td-center">SKU条形码</th>
							<!-- <th class="tab-td-center">商品名称</th> -->
							<th class="tab-td-center">商品标题</th>
							<!-- <th class="tab-td-center">sku编码</th> -->
							<th class="tab-td-center">销售属性</th>
							<!-- <th class="tab-td-center">商品分类</th>  -->
							<th class="tab-td-center">状态</th>
							<#if commodity.stockLimit ?? > 
							<#else>
								<th class="tab-td-center">库存是否共享</th> 
							</#if>
							<#if commodity.stockLimit?? && commodity.stockLimit == '1' > 
								<th class="tab-td-center">库存是否共享</th> 
							</#if>
							<th class="tab-td-center">库存状态</th> 
							 <!-- <th class="tab-td-center">可销售数量限制</th> 
							 <th class="tab-td-center">是否区域性商品</th>  -->
							<!-- <th class="tab-td-center">是否在供应商仓库</th> -->
							<th class="tab-td-center">购买区域</th>
							<th class="tab-td-center">对应仓库当前库存</th>
							<th class="tab-td-center">库存总数</th>
							<th class="tab-td-center">已占用数</th>
							<th class="tab-td-center">用户可购买量</th>
							<th class="tab-td-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<#if stockCustomList?? > 
						<#list stockCustomList as item >
						<tr >
							<td class="tab-td-center"><input name="name1" type="checkbox" value=""/></td>
							<td class="tab-td-center">${item_index + 1}</td>
							<td class="tab-td-center">${(item.commoditySku.barcode) !}</td>
							<!-- <td class="tab-td-center">${(item.commodity.code) !}</td> -->
							<!-- <td class="tab-td-center">${(item.commodity.barCode) !}</td> -->
							<!-- <td class="tab-td-center">${(item.commodity.name) !}</td> -->
							<td class="tab-td-center">${(item.commodity.title) !}</td>
							<!--  <td class="tab-td-center">${(item.commoditySku.skuCode) !}</td> -->
							<td class="tab-td-center">${(item.commoditySku.commodityAttributeValues) !}</td>
							 <!-- <td class="tab-td-center">${(item.commodity.categoryAttributeName) !}</td>  -->
							<td class="tab-td-center">
								<#if commodityStatusList?? > 
									<#list commodityStatusList as items >
										<#if items.getValue()==item.commodity.status>${(items.getName())}</#if>
									</#list>  
								</#if>
							</td>
							<!-- <td class="tab-td-center">
								<#if (item.commodity.warehouse)?? && item.commodity.warehouse == "0">是<#else>否</#if>
							</td>   -->
							 <td class="tab-td-center">
								<#if (item.isMany)?? && item.isMany == "0">非共享<#else>共享</#if>
							</td> 
							<td class="tab-td-center">
								<#if (item.actFlag)?? && item.actFlag == "0">启用<#else>禁用</#if>
							</td> 
							<!--  <td class="tab-td-center">
								<#if (item.commodity.stockLimit)?? && item.commodity.stockLimit == "0">WMS决定商品可销售数量<#else>自定义商品可销售数量</#if>
							</td>  
							  <td class="tab-td-center">
								<#if (item.commodity.areaCategory)?? && item.commodity.areaCategory == "0">区域性商品<#else>非区域性商品</#if>
							</td>  --> 
							<td class="tab-td-center">
								<#if item.commodity.areaCategory == "0">
									${(item.addressNameOrWarehouseName) !}
								<#else>
									所有区域
								</#if>
							</td>
							<td class="tab-td-center">
							<#if (item.currStockNumWms)?? >${(item.currStockNumWms) !}<#else>0</#if>
							</td>
							<input id="numValue_${item_index }" type="hidden"  value="${(item.currStockNum) !}" /> 
							<td class="tab-td-center" id="numTd_${item_index }" 							
							 <@permission name="order:stock:editNum">ondblclick=editCurrStockNum('${(item.id) !}','${item_index }')</@permission>
						    >
								${(item.currStockNum) !}
							</td>
							<input id="stockUsedCount_${item_index }" type="hidden"  value="<#if (item.stockCustomUsed.stockUsedCount)?? >${(item.stockCustomUsed.stockUsedCount) !}<#else>0</#if>" /> 
							<td class="tab-td-center">
							<#if (item.stockCustomUsed.stockUsedCount)?? >${(item.stockCustomUsed.stockUsedCount) !}<#else>0</#if>
							</td> 
							<input id="userCanBuyValue_${item_index }" type="hidden"  value="<#if (item.stockCustomUsed.userCanBuy)?? >${(item.stockCustomUsed.userCanBuy) !}<#else>${(item.currStockNum) !}</#if>" /> 
							<td class="tab-td-center" id="userCanBuyTd_${item_index }">
							<#if (item.stockCustomUsed.userCanBuy)?? >
								${(item.stockCustomUsed.userCanBuy) !}
							<#else>
								${(item.currStockNum) !}
							</#if>
							</td>
							<td class="tab-td-center">
							 <@permission name="order:stock:viewLog">
								<a href="${BasePath !}/stock/stockNumLogDeail.do?stockCustomId=${(item.id) !}" >查看日志</a>
							</@permission> 
							</td>  
						</tr>
						</#list>  
						</#if>
						
						<#if stockWmsList?? > 
						<#list stockWmsList as item >
						<tr ondblclick="view('${(item.id) !}','${(item.code) !}')">
							<td class="tab-td-center"><input name="name1" type="checkbox" value=""/></td>
							<td class="tab-td-center">${item_index + 1}</td>
							<td class="tab-td-center">${(item.commoditySku.barcode) !}</td>
							<!-- <td class="tab-td-center">${(item.commodity.code) !}</td> -->
							<!-- <td class="tab-td-center">${(item.commodity.barcode) !}</td> -->
							<td class="tab-td-center">${(item.commodity.title) !}</td>
							<!-- <td class="tab-td-center">${(item.commodity.name) !}</td> -->
						<!-- 	<td class="tab-td-center">${(item.commoditySku.skuCode) !}</td> -->
							<td class="tab-td-center">${(item.commoditySku.commodityAttributeValues) !}</td>
						<!-- 	<td class="tab-td-center">${(item.commodity.categoryAttributeName) !}</td> -->
							<td class="tab-td-center">
								<#if commodityStatusList?? > 
									<#list commodityStatusList as items >
										<#if item.commodity.status?? > 
											<#if items.getValue()==item.commodity.status>${(items.getName())}</#if>
										</#if>
									</#list>  
								</#if>
							</td>
							 <td class="tab-td-center">
								<#if (item.actFlag)?? && item.actFlag == "0">启用<#else>禁用</#if>
							</td> 
						    <!-- <td class="tab-td-center">
								<#if (item.commodity.stockLimit)?? && item.commodity.stockLimit == "0">WMS决定商品可销售数量<#else>自定义商品可销售数量</#if>
							</td> 
							<td class="tab-td-center">
								<#if (item.commodity.areaCategory)?? && item.commodity.areaCategory == "0">区域性商品<#else>非区域性商品</#if>
							</td> -->  
							<!-- <td class="tab-td-center">
								<#if (item.commodity.warehouse)?? && item.commodity.warehouse == "0">是<#else>否</#if>
							</td>   -->
							<td class="tab-td-center">
								<!-- <#if (item.commodity.areaCategory)?? && item.commodity.areaCategory == "0">
									${(item.addressNameOrWarehouseName) !}
								<#else>
									所有区域
								</#if> -->
								${(item.addressNameOrWarehouseName) !}
							</td>
							<td class="tab-td-center">
							<#if (item.currStockNum)?? >${(item.currStockNum) !}<#else>0</#if>
							</td>
							<td class="tab-td-center">
							<#if (item.currStockNum)?? >${(item.currStockNum) !}<#else>0</#if>
							</td>
							<td class="tab-td-center">
							<#if (item.stockWmsUsed.stockUsedCount)?? >${(item.stockWmsUsed.stockUsedCount) !}<#else>0</#if>
							</td>
							<td class="tab-td-center">
							<#if (item.stockWmsUsed.userCanBuy)?? >
								${(item.stockWmsUsed.userCanBuy) !}
							<#else>
								${(item.currStockNum) !}
							</#if>
							</td>
							<td class="tab-td-center">
								
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

	/* $(function(){
	    executeValidateFrom('myform');
	}); */
    
   function view(id,code) {
      // window.location.href = '${BasePath !}/commodity/form.do?id='+ id + "&commodityCode="+code + "&viewType=1";
   } 
   
   //选择供应商
   function vendorPopupFrom() {
       $.frontEngineDialog.executeIframeDialog('vendor_select_stock', '选择供应商', rootPath
               + '/stock/listSelectVendor.do', '1000', '600');
   }
   
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
   
   function recovery1(num,index,useCanBuyNum){
	   $("#numTd_"+index).html("");
	   $("#userCanBuyTd_"+index).html("");
	   var str=num;
	   var useCanBuyNumStr=useCanBuyNum;
	   $("#numTd_"+index).append(str); 
	   $("#userCanBuyTd_"+index).append(useCanBuyNumStr);
   }
   
   function enabledInsertLog(num,stockCustomId,index,useCanBuyNum){
	  /*  $.frontEngineDialog.executeDialog('delete_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否' + '更改自定义库存数量' + '!','210','50',
				function(){
		   			insertLog(num,stockCustomId,index);
				},
				function(){
					recovery1(num,index);
				}
			); */
			
	   dialog({
           id: "delete_table_info",
           icon:'succeed',
           title: "信息",
           width:200,
           height:50,
           content: '<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否更改自定义库存数量!',
           button: [
                    {
                        value: '确定',
                        callback: function(){
                        	insertLog(num,stockCustomId,index,useCanBuyNum);
                        }
                    },
                    {
                        value: '取消',
                        callback: function(){
                        	recovery1(num,index,useCanBuyNum);
                        }
                    }
                    
                ]
           /*cancelValue: '取消',
           cancel: function () {},
           okValue: '确定',
           ok:callback*/
          /* ok:callback,
           cancel: true*/
       }).showModal();
   }
   
   function insertLog(num,stockCustomId,index,useCanBuyNum){
	   var newNum=$("#currStockNum_"+index).val();
	   var stockUsedCount=$("#stockUsedCount_"+index).val();
	   if((newNum==null || newNum=="" || typeof(newNum)=="undefined") || isNaN(newNum)){
		   $("#numTd_"+index).html("");
		   var str=num;
		   $("#numTd_"+index).append(str); 
 			 $.frontEngineDialog.executeDialogContentTime('请填写好数量');
 			 return;
 		}
	   
	   newNum=parseInt(newNum);
	   stockUsedCount=parseInt(stockUsedCount);
	   if(newNum<stockUsedCount){
		   $("#numTd_"+index).html("");
		   var str=num;
		   $("#numTd_"+index).append(str); 
		   $.frontEngineDialog.executeDialogContentTime('自定义库存数量不能少于已占用量');
			 return;
	   }
	   
	   $("#numTd_"+index).html("");
	   $("#userCanBuyTd_"+index).html("");
	   $.ajax({
			url: rootPath+"/stock/insertLog.do",
			type:'POST',
			data:{"newNum":newNum,"stockCustomId":stockCustomId},
			async:false,
			dataType: 'json',
			success: function(data){
				if(data == null || data == "fail"){
					 $.frontEngineDialog.executeDialogContentTime('添加失败');
					return;
				}
				if(data == "fail_control"){
					 $.frontEngineDialog.executeDialogContentTime('正在下单中，请稍后再改');
					 recovery1(num,index,useCanBuyNum);
					return;
				}
				var str=newNum;
				 $("#numTd_"+index).append(str); 
				$("#numValue_"+index).val(newNum);
				var newUserCanBuy=newNum-stockUsedCount;
				$("#userCanBuyTd_"+index).append(newUserCanBuy);
				 $.frontEngineDialog.executeDialogContentTime('修改成功');
				 setTimeout(function(){
					 window.location.reload();
				 }, 1000);
			}//end success 
		});//end ajax	
   }
   
   function loadCommodity(){
	   $.ajax({
			url: rootPath+"/stock/loadCommodity.do",
			type:'POST',
			data:{},
			async:false,
			dataType: 'json',
			success: function(data){
				if(data == null || data == "fail"){
					 $.frontEngineDialog.executeDialogContentTime('初始化失败');
					return;
				}
				if(!isNaN(data)){
					 $.frontEngineDialog.executeDialogContentTime('成功初始化'+data+'条');
					 setTimeout(function(){
						 window.location.reload();
					 }, 1000);
				}else{
					$.frontEngineDialog.executeDialogContentTime(data);
					 setTimeout(function(){
						 window.location.reload();
					 }, 2000);
				}
				
			}//end success 
		});//end ajax	
   }
   
   $("#loadCommodity").click(function(){
	   //${BasePath !}/stock/stockNumLogDeail.do?stockCustomId=${(item.id) !}
	   $.ajax({
			url: rootPath+"/stock/checkLoadCommodityNum.do",
			type:'POST',
			data:"",
			async:false,
			dataType: 'json',
			success: function(data){
				if(data == null || data == "fail"){
					 $.frontEngineDialog.executeDialogContentTime('添加失败');
					return;
				}
				
				if(data<=0){
					$.frontEngineDialog.executeDialogContentTime('没有需要初始化的自定义非区域性商品');
					return;
				}
				
				$.frontEngineDialog.executeDialog('delete_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　检测到有    '+data+'  条自定义非区域行商品没有添加,是否继续？','100%','100%',
						function(){
							//window.location.href=url;
							loadCommodity()
						}
				);
				
			}//end success 
		});//end ajax	
		
   });
   
   function CheckInputIntFloat(oInput) {
		  if ('' != oInput.value.replace(/[\d]+/, '')) {
		    oInput.value = oInput.value.match(/[\d]+/) == null ? '' : oInput.value.match(/[\d]+/);
		  }
	}
   
   function editCurrStockNum(stockCustomId,index){
	   $("#numTd_"+index).html(""); 
	   var num = $("#numValue_"+index).val();
	   var useCanBuyNum = $("#userCanBuyValue_"+index).val();
	   var str="<input id='currStockNum_"+index+"' name='currStockNum' maxlength='9' onkeyup='javascript:CheckInputIntFloat(this);'   onblur=enabledInsertLog('"+num+"','"+stockCustomId+"','"+index+"','"+useCanBuyNum+"')  style='width:50px' class='form-control txt_mid input-sm' type='text'  value="+num+">"
	   $("#numTd_"+index).append(str); 
   }
   
  function searchStatus(url){
	  var stockLimit=$("#stockLimitChange").val();
	  window.location.href = url+"&stockLimitChange="+stockLimit;
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
    											$.frontEngineDialog.executeDialogOK('信息','只能选择最小的商品分类', "");
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
    
    
    $("#import_button").click(function() {
   		var url = rootPath + '/stock/importView.do';
       var dlg = dialog({
           id: "printerForm",
           title: '库存导入',
           lock: false,
           content : "<iframe  id='printerForm' name='printerForm,"+window.location.href+"' src="+url+" width='500px' height='210px' frameborder='0' scrolling='no' id='printerForm'></iframe>",
   	    button: [
    	        {
    	            value: '导入',
    	            callback: function () {
    	            	document.getElementById("printerForm").contentWindow.submitImport();
    	            	return false;
    	            },
    	        },
    	        {
    	            value: '取消',
    	            callback: function () {
    	            }
    	        }
    	    ]
   	}).show();
   }); 
    
    function exportExcel() {
    	document.forms[0].action = rootPath + "/stock/exportExcel.do";
    	
    	document.forms[0].submit();
    	
    	document.forms[0].action = rootPath + "/stock/list.do";
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
