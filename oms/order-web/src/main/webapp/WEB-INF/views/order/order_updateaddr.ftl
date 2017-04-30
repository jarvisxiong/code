<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>修改地址</title>
</head>
<body>

<div class="box-body">
  <div class="tab-content">
    <div class="wrapper-html">
      <form id="myForm" action="${BasePath !}/order/ajax_save.do" method="post">
      <input type="hidden" name="id" value="${(data.id)!}" />
      <input type="hidden" name="orderNo" value="${(data.orderNo)!}" />
      <input type="hidden" name="addressInfo" id="addressInfo"  value="${(data.addressInfo)!}" />
      
        <div class="addForm1">
          <div class="form-tr">
            <div class="form-td">
              <label><i>*</i>收货人：</label>
              <div class="div-form">
                <input class="form-control input-sm txt_big3" type="text"  value="${(data.consignName)!}"  id="consignName"  name="consignName" data-rule-required="true"  data-msg-required="收货人不能为空"/>
              </div>
            </div>
          </div>
          <div class="form-tr">
            <div class="form-td">
              <label><i>*</i>收货地址：</label>
            <div class="div-form">
                 <div class="f7" >
                 <input type="hidden"  id="regionId" name="regionId"  value="${(data.regionId)!}" />
                   <input class="form-control input-sm txt_big3"  
                    type="text" id="regionName" readonly="readonly"  name="regionName" value="${(data.regionName)!}" data-rule-required="true" data-msg-required="广告区域不能为空">
                       <span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span> 
                     <span class="selectBtn" onclick='selectAddr();'>选</span> 
                      </div>
                 </div>
            </div> 
          </div>
          <div class="form-tr">
            <div class="form-td">
              <label><i></i>详细地址：</label>
              <div class="div-form">
                <input class="form-control input-sm txt_big3" type="text"   name="detailedAddress" id="detailedAddress"  value="${(data.detailedAddress)!}" />
              </div>
            </div>
          </div>
          <div class="form-tr">
            <div class="form-td">
              <label><i>*</i>手机号码：</label>
              <div class="div-form">
                <input class="form-control input-sm txt_big3" type="text" value="${(data.consignPhone)!}" name="consignPhone" data-rule-required="true" data-msg-required="收货人电话不能为空"/>
              </div>
            </div>
          </div>
          <!-- <div class="form-tr">
            <div class="btn-div">
              <input type="button" onclick="saveEdit();" class="btn btn-primary btn-sm" value="保存">
              <input type="submit" class="btn btn-default btn-sm" value="返回">
            </div>
          </div> -->
        </div>
      </form>
    </div>
  </div>
</div>

<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<script type="text/javascript">
var dlg;
function selectAddr(){
	var url="${BasePath !}/order/addressListSelect.do?";
	var width =  window.screen.width*0.52;
	var height = window.screen.height*0.35;
	dlg = dialog({
		title: '选择地址',
        resize: false,
        drag: false,
        lock: true,
        content:"<iframe id='addrframe' name='addrframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>"
	    
	}).showModal();
}
function getSelectAddr(obj){
	dlg.close();	
	var regionId = $(obj).attr("id");
	var regionName = $(obj).attr("name");
	var preaddress =  $(obj).attr("preaddress");
	var addr = $.trim(preaddress)+$.trim(regionName);
	$("#regionId").val(regionId);
	$("#regionName").val(addr);
}
function saveEdit(dlg){
	var flag =  true;
	$("input[data-rule-required='true']").each(function(){
		if(!isNotNull($(this).val())){
			$.frontEngineDialog.executeDialogContentTime($(this).attr("data-msg-required"),1500);
			flag = false;
			return false;
		}else if($(this).attr("name")=="consignPhone"){
			var res = /^1[3458]\d{9}$/;
			if(!res.test($(this).val())){
				$.frontEngineDialog.executeDialogContentTime("请输入正确的手机号",1500);
				flag = false;
				return false;
			}
		}
	});
	if(!flag)return;
	$("#addressInfo").val($.trim($("#regionName").val())+$.trim($("#detailedAddress").val()));
	 $.post("${BasePath !}/order/ajax_save.do",$("form").serialize(),function(res){
		 if(res.STATUS=="SUCCESS"){
			 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
			 setTimeout(function(){
			 dlg.close();
			 parent.location.reload();
			 },1500);
		 }else{
			 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
		 }
	 },'json');
		} 
var isNotNull = function(value){
	if(!value) return false;
	if("undefined" == typeof(value)) return false;
	if("string" != typeof(value)) return true;
	//下面的方法只能字符类型使用,不然报错
	value = $.trim(value);
	var flag = false;
	if(value==null){
		flag = false;
	}else if(value==""){
		flag = false;
	}else if(value=="null"){
		flag = false;
	}else{
		flag = true;
	}
	return flag;
}
</script> 
</body>
</html>
