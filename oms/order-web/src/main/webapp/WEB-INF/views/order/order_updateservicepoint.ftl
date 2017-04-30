<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>订单改价</title>
</head>
<body>
<div class="box-body">
 <form id="myForm" action="${BasePath !}/order/ajax_save.do" method="post">
<input type="hidden" name="id" value="${(data.id)!}" />
<input type="hidden" name="servicePoint" value="${(data.servicePoint)!}" />
  <div class="tab-content">
    <div class="order-page addForm1">
      <div class="order-t1"><span class="order-time">下单时间：<#if (data.createDate) ??>${(data.createDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></span>订单编号：${(data.orderNo) !}</div>
      <ul>
        <li>客户姓名：${(data.memberName)!}</li>
        <li>联系方式：${(data.memberPhone)!}</li>
        <li>收货地址：${(data.addressInfo)!}</li>
        <li>配送地址：${(data.servicePoint)!}</li>
        <li>
          <div class="form-tr">
            <div class="form-td">
              <label>配送地址（合伙人）：</label>
              <div class="div-form">
                 <div class="f7" >
                 <input type="hidden"  id="servicePoint" name="sendPersonId"  value="${(data.sendPersonId)!}" />
                   <input class="form-control input-sm txt_big3"  
                    type="text" id="regionName" readonly="readonly"  name="sendPersonName" value="${(data.sendPersonName)!}" data-rule-required="true" data-msg-required="广告区域不能为空">
                       <span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span> 
                     <span class="selectBtn" onclick='selectAddr();'>选</span> 
                  </div>
              </div>
            </div>
          </div>
        </li>
        <!-- <li>
          <div class="form-tr">
            <div class="form-td">
              <label>配送人/合伙人：</label>
              <div class="div-form">
                <input type="text" class="form-control input-sm txt_big4">
              </div>
            </div>
          </div>
        </li> -->
      </ul>
    </div>
   <!--  <div class="form-tr">
            <div class="btn-div5">
              <input type="submit" class="btn btn-primary btn-sm" value="保存">
              <input type="submit" class="btn btn-default btn-sm" value="返回">
            </div>
          </div> -->
  </div>
  </form>
</div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<script type="text/javascript">
var dlg;
function selectAddr(){
	var url="${BasePath !}/order/partner_dataPicker.do?";
	var width =  window.screen.width*0.6;
	var height = window.screen.height*0.3;
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
			art_tips($(this).attr("data-msg-required"),1500);
			flag = false;
			return false;
		}else if($(this).attr("name")=="consignPhone"){
			var res = /^1[3458]\d{9}$/;
			if(!res.test($(this).val())){
				art_tips("请输入正确的手机号",1500);
				flag = false;
				return false;
			}
		}
	});
	if(!flag)return;
	$("#addressInfo").val($.trim($("#regionName").val())+$.trim($("#detailedAddress").val()));
	 $.post("${BasePath !}/order/ajax_saveAddr.do",$("form").serialize(),function(res){
		 if(res.STATUS=="SUCCESS"){
			 art_tips(res.MSG,1500);
			 setTimeout(function(){
			 dlg.close();
			 parent.location.reload();
			 },1500);
		 }else{
			 art_tips(res.MSG,1500);
		 }
	 },'json');
		} 
</script> 
</body>
</html>
