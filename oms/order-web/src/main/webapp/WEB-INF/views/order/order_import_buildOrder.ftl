<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="list" />
<title>敏感词导入</title>
</head>
<body>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="myAccount">
			<form  name="stockImportForm" id="stockImportForm" enctype="multipart/form-data" action="${BasePath !}/order/importBuildOrderExcel.do" method="post">
				<table width="100%" border="0">
					<tbody>
						<tr>
							<td width="100px"><span>*</span>选择文件：</td>
							<td width="200px"><input type="file" type="file" id="filePath" name="filePath" /></td>
						</tr>
						<tr>
							<td>模板下载：</td>
							<td>
								<a href="javascript:void(0);" onclick="downloadFile();"><font color="#39f">点击下载导入模板</font></a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>

	</div>
	<script type="text/javascript">
		function submitImport(){ 
			var isTrue=true;
			var filePath=$("#filePath").val();
			if((filePath==null || filePath=="" || typeof(filePath)=="undefined")){
				$.frontEngineDialog.executeDialogOK("上传失败","请选择上传文件！","200px");
				isTrue=false;
				return;
			}
			$.frontEngineDialog.executeDialogContentTime("数据正在处理，请稍后",50000);
			$("#stockImportForm").submit();
		}

		function downloadFile(type) {
			var path = "/template/order/importOrder.xlsx";
			window.open(rootPath + "/order/downFile.do?path=" + path);
		}
		
	</script>
	<#include "../common/loadingffzx.ftl" encoding="utf-8">
	<@load_content content="small"/>
</body>

</html>