<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>预售标签设置</title>
</head>
<body>
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/presaletag/saveAndUpdate.do?view=${(viewStatus) !}" method="post">
	         <input type="hidden" id="id" name="id" value="${(presaleTag.id) !}"/>  
	         <input type="hidden" id="oldName" name="oldName" value="${(presaleTag.name) !}"/> 
	         <input type="hidden" id="oldNumber" name="oldNumber" value="${(presaleTag.number) !}"/>       
	        <div class="addForm1">
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	            <div class="tips-form"></div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>标签名称：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" 
	                        data-msg-required="标签名称不能为空"type="text" id="name" name="name"   value="${(presaleTag.name) !}">          
	       	 			</div>
	                </div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>排序号：</label>
	                    <div class="div-form">
                                <input class="form-control input-sm txt_mid"  type="text" id="number" name="number"   data-rule-isIntGteZero="true"  data-msg-isIntGteZero="排序号必须大于或者等于0的整数"   data-rule-required="true"  data-msg-required="排序号不能为空"  value="${(presaleTag.number) !}">   
	       	 			</div>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!--新增1--end-->	
                                      
     </div>
 </div>
         </div>
       </div>
   </div>
<script type="text/javascript">
    $(function(){
        executeValidateFrom('myform','','resultData');
    });    
    
    function butsubmit(){
    	var name=$("#name").val();
    	if(name && name.length>6){
    		$.frontEngineDialog.executeDialogOK('提示信息','最多只支持输入6个汉字！','300px');		
			$("#name").val("");
			return false;
    	}
    	$("#myform").submit();
    }
    
    function resultData(result){
   	 if(result && result.status=="success"){
   		 parent.location.reload(false);
        }else{
       	 $.frontEngineDialog.executeDialogContentTime(result.infoStr,2000);
        }    
   }
</script>
</body>
</html>