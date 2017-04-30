<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>演示添加页面</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform2" method="post" action="${BasePath !}/ordinaryActivity/recommendSave.do">
				   <input id="acrivityId" name="acrivityId" type="hidden" value="${(acrivityId) !}" />
					<input id="commoditys" name="commoditys" type="hidden" value="" />
					<div class="addForm1">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<div class="btn-div3">
									<a href="javascript:addItem();"  class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
								</div>
							</div>
						</div>
						<!--表格修改2--start-->
						<table class="table table-hover table-striped bor2 table-common">
							<thead>
								<tr>
									<th width="1px"></th>
									<th>商品图片</th>
									<th>商品选择</th>
									<th>排序号</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="recommends">
								<tr style="display: none;">
									<td></td>
									<td><input type="hidden" name="isRe" value="" />
										<div class="div-form">	
					                    	<div style="border:1px solid #ccc; width:100px; height:100px;">
				                                <img id="photoPath_img" style="width:100px; height:100px;" <#if (activity.picPath)?? >src="${(image_path)!}/${activity.picPath?replace('size','origin')}"</#if>   onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
				                            </div>
	                                       	<input  type="hidden" id="picPath" name="picPath" value="${(activity.picPath) !}"  />
	                                       	<input type="hidden"  name="photoPathBtn">
	                                    	<br><span style="color: red;  ">注：图片内存大小，100KB以内</span>
				      	 				</div>
									</td>
									<td><input type="hidden" name="id" value="" />
										<!-- 放活动商品 -->
					                    <div class="f7" onclick="toSelectOrdinary('${(acrivityId) !}',this)">
			                               	<input class="form-control input-sm txt_mid"  type="text" name="activityTitle" value="">
			                               	<span class="selectBtn">选</span>
			                           	</div>   
									</td>
									<td><input name="recommendSort" type="text" value="" onkeyup="onlyInputNum(this);" class="form-control txt_mid input-sm"/></td>
									<td><a href="javascript:void(0)" onclick="deleteItem(this);">删除</a></td>
								</tr>
								<#if dataList?? > 
								<#list dataList as item >
								<tr>
									<td>${item_index + 1}</td>
									<td><input type="hidden" name="isRe" value="${(item.isRecommend) !}" />
					                    <div class="div-form" id="photoPath${item_index + 1}">	
					                    	<div style="border:1px solid #ccc; width:100px; height:100px;">
				                                <img id="photoPath_img" style="width:100px; height:100px;" <#if (item.picPath)?? >src="${(image_path)!}/${item.picPath?replace('size','origin')}"</#if>   onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
				                            </div>
	                                       	<input  type="hidden" name="picPath" value="${(item.picPath) !}" />
	                                       	<input type="hidden"  id="photoPathBtn${item_index + 1}" name="photoPathBtn" >
	                                    	<br><span style="color: red;  ">注：图片内存大小，100KB以内</span>
	                                    </div>
									</td>
									<td>
										<input type="hidden" name="id" value="${(item.id) !}" />
										<!-- 放活动商品 -->
					                    <div class="f7" onclick="toSelectOrdinary('${(acrivityId) !}',this)">
			                               	<input class="form-control input-sm txt_mid"  type="text" name="activityTitle" value="${(item.activityTitle) !}">
			                               	<span class="selectBtn">选</span>
			                           	</div>    
									</td>
									<td><input name="recommendSort" type="text" value="${(item.recommendSort) !}" onkeyup="onlyInputNum(this);" class="form-control txt_mid input-sm"/></td>
									<td><a href="javascript:void(0)" onclick="deleteItem(this);">删除</a></td>
								</tr>
								</#list> 
								</#if>
							</tbody>
						</table>
						<!-- 表格修改2--end -->
					</div>
					
				</form>
			</div>
		</div>
		<!--新增1--end-->

	</div>
</div>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/common/uploadImage.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/ordinary/ordinary_recommend_list.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/onlyInputNumber.js?v=${ver !}"></script>
<script type="text/javascript">
var image_path = "${image_path}";
	$(function(){
		executeValidateFrom('myform2','','resultData');
		// 上传图片
		//initUploadImage(showImg,"activityImage","photoPathBtn",100);
	});
	// 保存
	function butsubmit(){
	 	// 验证
 		if (validate()) {
			$("#myform2").submit();
		 }
	}
</script>
</body>
</html>