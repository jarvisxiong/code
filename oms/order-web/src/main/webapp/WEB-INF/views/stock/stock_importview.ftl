<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="list" />
<title></title>
</head>
<body>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="myAccount">
			<form  name="stockImportForm" id="stockImportForm" enctype="multipart/form-data" action="${BasePath !}/stock/importExcel.do" method="post">
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
		<script type="text/javascript" src="${BasePath !}/asset/js/oms/stock/stock_importview.js?v=${ver !}"></script>
	</div>
	<#include "../common/loadingffzx.ftl" encoding="utf-8">
	<@load_content content="small"/>
</body>
</html>