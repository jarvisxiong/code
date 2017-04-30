<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>商品导入</title>
</head>
<body style="overflow:auto;overflow-x: hidden;">
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<form id="find-page-orderby-form" name="impotpic"  enctype="multipart/form-data" action="${BasePath !}/couponGrant/importExcel.do" onsubmit="return  chongfusubmit();" method="post">
					
		<!--表格修改2--start-->
		<table width="100%" border="0">
			<tbody>
				
			 <tr>	
			 <td  width="100px"><span>*</span>选择文件：</td>
           	 <td  width="200px"><input type="file" type="file" id="filePath" name="filePath" /></td>
       
			 </tr>
			 <tr>	
        	 <td >模板下载：</td>
             <td><a href="javascript:void(0);" onclick="downloadFile();"><font color="#39f">点击下载导入模板</font></a></td>
      	     </tr>
      	     <tr>
      	     <td><input type="submit" class="btn btn-primary" value="提交导入模板" />
      	     </td>
      	     </tr>
			</tbody>
		</table>
		</form>
	</div>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">

<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_imprtview.js"></script>
<script type="text/javascript">
var  rootPath="${BasePath !}";
var id="${id !}";
var i=0;
function chongfusubmit(){
console.log("t");
var filePath=$("#filePath").val();
	if(filePath == null || filePath == ''){
		$.frontEngineDialog.executeDialogOK("导入失败","请选择导入文件！","200px");
		return false;
	}


	if(i==0){
	i++;
	return true;
	}
	else{
	return false;
	}
}

</script>
</div>
</body>
</html>