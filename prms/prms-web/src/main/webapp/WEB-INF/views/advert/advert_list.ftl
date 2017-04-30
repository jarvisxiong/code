<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<body style="overflow: hidden;"> 
<div class="row">
    <div class="col-md-12">
        <div class="box-body" >
          
            <div class="row" style="float: left; margin-left: 0px;width: 100%;">
	            <div class="search">
		            <form id="find-page-orderby-form" action="${BasePath !}/advert/advert_list.do" method="post">	
		            	<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />	            
		                <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
		                <input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
		                <input name="parent_regionIds" type="hidden" value="${(params.parent_regionIds) !}" />
		                <input name="regionId" type="hidden" value="${(params.regionId) !}" />
		                
		                <div class="inquire-ul" style="margin-top: -10px;">
		                    <div class="form-tr">
		                        <div class="form-td">
		                            <label>标题：</label>
		                            <div class="div-form">
		                            <input class="form-control input-sm txt_mid" type="text" id="keyWords" name="keyWords" value="${(params.keyWords) !}">
		                            </div>
		                        </div>
		                        <div class="form-td">
								<label>广告类型 ：</label>
								<div class="div-form">
								<select class="input-select2" id="advertType" name="advertType">
								<option value="">-广告类型-</option>
								 <#if advertTypeList ??>
                                            <#list advertTypeList as item > 
                                            <option value="${item.value}"  <#if (params.advertType) ??><#if params.advertType == item.value>selected="selected"</#if></#if> >${item.name}</option>
                                            </#list>
                                  </#if>
								</select>
								</div>
								</div>
								<div class="form-td">
								<label>广告状态：</label>
								<div class="div-form">
								<select class="input-select2" id="status" name="status">
								<option value="">-广告状态-</option>
								 <#if advertStatusList ??>
                                            <#list advertStatusList as item > 
                                            <option value="${item.value}"  <#if (params.status) ??><#if params.status == item.value>selected="selected"</#if></#if> >${item.name}</option>
                                            </#list>
                                  </#if>
								</select>
								</div>
								</div>
 								<div class="form-td">
		                            <label>开始时间：</label>
		                            <div class="div-form"> 
                                        <input name="startDate" id="startDate" class="form-control input-sm txt_mid"   readonly="readonly"
                                         onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                          value="${(params.startDate)!}" /> 
                                    </div>
		                        </div>
	    						<div class="form-td">
		                            <label>结束时间：</label>
		                            <div class="div-form"> 
                                        <input name="endDate" id="startDate" class="form-control input-sm txt_mid"   readonly="readonly"
                                         onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                          value="${(params.endDate)!}" /> 
                                    </div>
		                        </div>
		                        <br>		                        
		                    </div>                              
		                </div>
		                
		                <div class="btn-div3">
		                    <button id="find-page-orderby-button" class="btn btn-primary btn-sm" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button> 
                            <button id="clean" onclick="onEmpty()" class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button>  
                             <@permission name="prms:advert:edit">                             
		                    <a href="javascript:void(0);" onclick="toAdd();" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>      
		                    </@permission>                        
		                </div>
		            </form>
		        </div>
                                    
                <div class="box-header" style="float: left;padding-left: 0px;width: 99%; ">
                         <!--表格修改2--start-->
                    <table class="table table-hover table-striped bor2 table-common">
                        <thead>
                        <tr>
                            <th width="50px"></th>
                            <th class="tab-td-center">编码/序号</th>
                            <th class="tab-td-center">广告标题</th>
                            <th class="tab-td-center">所属区域</th>
                            <th class="tab-td-center">广告类型</th>
                            <th class="tab-td-center">开始时间</th>
                            <th class="tab-td-center">结束时间</th>
                            <th class="tab-td-center">广告状态</th>
                            <th class="tab-td-center">发布人</th>
                            <th class="tab-td-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if list?? >
                        <#list list as item >
                        <tr ondblclick="location.href='${BasePath !}/advert/advert_form.do?dataId=${(item.id) !}&viewtype=1'">
                            <td>${(item_index+1)}</td> 
                            <td align="center">${item.locationIndex !}</td>                           
                            <td align="center">${item.title !}</td>
                            <td align="center">${item.region.name !}</td>  
                            <td align="center">${item.advertTypeName !}</td>    
                            <td align="center"><#if (item.startDate)??>${item.startDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                            <td align="center"><#if (item.endDate)??>${item.endDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                            <td align="center">${item.statusName !}</td>
                            <td align="center">${item.createBy.name !}</td>
                            <td align="center">
                            <@permission name="prms:advert:edit">
							<a href="${BasePath !}/advert/advert_form.do?dataId=${(item.id) !}&viewtype=0">编辑</a>
							</@permission>
							<@permission name="prms:advert:delete">
							| <a name="delete_item" href="javascript:void(0);" onclick="delObjById('${(item.id) !}');">删除</a>
							</@permission>
                            </td>
                        </tr>
                        </#list>
                        </#if>
                        </tbody>
                    </table>
                    
                    <#include "../common/page_macro.ftl" encoding="utf-8"> 
                        <@my_page pageObj/>  
                    </div>  
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<script src="${BasePath !}/asset/js/common/util.js?v=${ver !}"></script>
<script type="text/javascript">
        $(".input-select1").select2();
        $(".input-select").select2();
        function onEmpty() {
            location.href="${BasePath !}/advert/advert_list.do?clearParams=YES";            
        }
     function toAdd(){
    	 var regionId = $("input[name='regionId']").val();
    	 if(isNotNull(regionId)){
    	 location.href="${BasePath !}/advert/advert_form.do?regionId="+regionId;
    	 }else{
    		 $.frontEngineDialog.executeDialogContentTime("请选择左边具体区域(即叶子节点)",1500);
    	 }
     }
     function delObjById(id){
    	 $.frontEngineDialog.executeDialog('delete_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　警告：删除状态为“进行中”的广告，请务必立即在该广告位新增广告或修改该广告位的另一个广告，否则app会出现空白。 删除后，不可恢复，请谨慎操作！','100%','100%',
 				function(){
    	 	 $.post(rootPath+"/advert/ajax_advertDelete.do",{id:id},function(res){
        		 if(res.STATUS=="SUCCESS"){
        			 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
        			 location.reload(); 
        		 }else{
        			 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
        		 }
        	 },'json');
 				}
 			);
   
     }
</script>

<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/> 
</body>
</html>