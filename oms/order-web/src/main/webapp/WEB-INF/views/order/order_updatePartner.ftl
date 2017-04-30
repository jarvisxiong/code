<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>重新分配合伙人</title>
</head>
<body>

<div class="box-body">
  <div class="tab-content">
    <div class="wrapper-html">
      <input type="hidden" id="dataId" value="${(data.id)!}" />
      <input type="hidden" name="allocationError" value="${(data.allocationError)!}" />
      <input type="hidden" name="logisticsStatus" value="${(logisticsStatus)!}" />
      <input type="hidden" name="oldPartnerId" value="${(data.partnerId)!}" />
      <!-- 合伙人id  -->
        <div class="addForm1">
        
          <div class="form-tr">
            <div class="form-td">
              <label>订单编号：</label>
              <div class="div-form">
              ${(data.orderNo)!}
              </div>
            </div>
            <div class="form-td">
              <label>客户姓名：</label>
              <div class="div-form">
              ${(data.memberName)!}
              </div>
            </div>
          </div>
          <div class="form-tr">
            <div class="form-td">
              <label>下单时间：</label>
              <div class="div-form">
             <#if (data.createDate) ??>${(data.createDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if>
              </div>
            </div>
            <div class="form-td">
              <label>联系方式：</label>
              <div class="div-form">
              ${(data.memberPhone)!}
              </div>
            </div>
          </div>
          <div class="form-tr">
            <div class="form-td">
              <label>收货地址：</label>
              <div class="div-form">
              ${(data.addressInfo)!}
              </div>
            </div>
            
          </div>
            <div class="form-tr">
            <div class="form-td">
              <label>订单对应配送地址（合伙人）：</label>
              <div class="div-form">
                <input class="form-control input-sm txt_big3" type="text" data-rule-required="true"  readonly="readonly" name="servicePoint" id="servicePoint"  value="${(data.servicePoint)!}" data-msg-required="详细地址不能为空"/>
              </div>
            </div>
          </div>
          <div class="form-tr">
            <div class="form-td">
              <label>订单对应配送人/合伙人：</label>
              <input class="form-control input-sm txt_big3" type="text" data-rule-required="true"  readonly="readonly" name="sendPersonName" id="sendPersonName"  value="${(data.sendPersonName)!}" data-msg-required="详细地址不能为空"/>
            </div> 
          </div>
          
          <div class="form-tr">
            <div class="form-td">
              <label>物流状态：</label>
              <div class="div-form">
              <#if !(logisticsStatus??) || logisticsStatus =='0'>
     			   未分配
       		 <#else>
       			 已分配
       		 </#if>
              </div>
            </div>
          </div>
          
       <div class="form-tr" style="color:#FF0000; padding:5px 0;">    当前卖家收货地址对应的合伙人如下，请电话核实，确认是否正确？正确的话，请点击下方的“确认分配合伙人”，错误的话，请先到地址管理修改正确后，再进行重新分配配送人。</div>
          <div class="form-tr" style=" border: 1px dotted #FF0000;    overflow: hidden;">
          <div class="form-tr">
            <div class="form-td">
              <label style="width: 250px;">收货地址对应配送地址（合伙人）：</label>
              <div class="div-form">
              <input type="hidden" id="newPartnerId" value="${(partner.id)!}"/>
              ${(partner.addressName)!} ${(partner.addressDeal)!}
              </div>
            </div>
          </div>
           
          
          <div class="form-tr">
            <div class="form-td">
              <label style=" width: 250px;">收货地址对应 配送/合伙人：</label>
              <div class="div-form">
              ${(partner.name)!}
              </div>
            </div>
          </div>
          </div>
           <!-- <div class="form-tr">
                    <div class="btn-div">
                        <input type="buttton" value="更新合伙人" class="btn btn-primary">
                    </div> -->
            </div>
        </div>
    </div>
  </div>
</div>


<script type="text/javascript">
var dlg;
function selectPartner(){
	var url="${BasePath !}/order/partnerlistSelect.do";
	var width =  window.screen.width*0.5;
	var height = window.screen.height*0.5;
	dlg = dialog({
		title: '选择地址',
        resize: false,
        drag: false,
        lock: true,
        content:"<iframe  src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' scrolling='auto' ></iframe>"
	    
	}).showModal();
}
function getSelectPartner(obj){
	dlg.close();	
	var partnerId = $(obj).attr("id");
	var partnerCode = $(obj).attr("partnerCode");
	var sendPerson = partnerId;
	var sendPersonName = $(obj).attr("name");
	var servicePoint =  $(obj).attr("addrssInfo");
	var regionId =  $(obj).attr("addressId");              
	$("#partnerId").val(partnerId);
	$("#partnerCode").val(partnerCode);
	$("#servicePoint").val(servicePoint);
	$("#sendPerson").val(sendPerson);
	$("#sendPersonName").val(sendPersonName);
	$("#regionId").val(regionId);
}
function saveEdit(){
	
	var oldPartnerId = $("#oldPartnerId").val();
	var newPartnerId = $("#oldPartnerId").val();
	var wmsStatus = $("#wmsStatus").val();
	var msgTips = '点击后，将更新该订单的配送人及配送地址请再次确认，警告：当“订单是否未分配”取值是“已分配”时，请务必先记录上面的“配送地址及配送人”再点击确定';
	$.frontEngineDialog.executeDialog('delete_table_info','合伙人新更新提示','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>'+msgTips,'100%','100%',
				function(){
				toSubmit(); 
				}
			);
	
	var flag =  true;
	if(!flag)return;
	 
		} 
		
 function toSubmit(){
	 $.post("${BasePath !}/order/ajax_savePartner.do",{id:$("#dataId").val()},function(res){
		 if(res.STATUS=="SUCCESS"){
			 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
			 setTimeout(function(){
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
