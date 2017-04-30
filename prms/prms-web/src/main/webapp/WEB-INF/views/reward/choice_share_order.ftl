<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit" />
<title>精选晒单管理</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">精选晒单管理</a></li>
</ul>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="addUser">

			<!--新增1--start-->
			<div class="row">
				<div class="col-lg-10 col-md-12 col-sm-12">

					<form id="myform" method="post"
						action="${BasePath !}/reward/saveChoiceShareOrder.do">
						<input type="hidden" name="id" value="${(choice.id) !}" />
						<div class="addForm1">
							<div id="error_con" class="tips-form">
								<ul></ul>
							</div>
							<div class="form-tr">
								<div class="form-td">
									<label><i>*</i>精选晒单图片： </label>
									<div class="div-form">图片尺寸610*442，图片大小100KB以内，图片格式为jpg、png</div>
								</div>
							</div>
							<div class="form-tr">
								<div class="form-td">
									<label></label>
									<div class="div-form">
										<div class="pic-show">
										  <ul id="show_detailImage">			                   
									<li class="alert">
										<img src="${BasePath !}/asset/img/noPic.jpg" id="photoPath_img1"><a id="a_image1"  href="#" class="close" onclick="deleteImage(this.title,1)" >×</a> 
	                          			<input type="hidden"  id="detailImageBtn1">
									</li>
									<li class="alert">
										<img src="${BasePath !}/asset/img/noPic.jpg" id="photoPath_img2"><a id="a_image2"  href="#" class="close" onclick="deleteImage(this.title,2)">×</a>
										<input type="hidden"  id="detailImageBtn2">
									</li>
									<li class="alert">
										<img src="${BasePath !}/asset/img/noPic.jpg" id="photoPath_img3"><a id="a_image3"  href="#" class="close" onclick="deleteImage(this.title,3)" >×</a>
										<input type="hidden"  id="detailImageBtn3"> 
									</li>
									<li class="alert">
										<img src="${BasePath !}/asset/img/noPic.jpg" id="photoPath_img4"><a id="a_image4"  href="#" class="close" onclick="deleteImage(this.title,4)" >×</a> 
										<input type="hidden"  id="detailImageBtn4">
									</li>
									<li class="alert">
										<img src="${BasePath !}/asset/img/noPic.jpg" id="photoPath_img5"><a id="a_image5"  href="#" class="close" onclick="deleteImage(this.title,5)" >×</a>
										<input type="hidden"  id="detailImageBtn5">
									 </li>
									<li class="alert">
										<img src="${BasePath !}/asset/img/noPic.jpg" id="photoPath_img6"><a id="a_image6"  href="#" class="close" onclick="deleteImage(this.title,6)" >×</a>
										<input type="hidden"  id="detailImageBtn6">
									</li>	                    			                       	                        
		                    </ul>
		                    <input type="hidden" id="choiceImage" name="choiceImage" value="${(choice.choiceImage) !}"  data-rule-required="true"  data-msg-required="详情图片不能为空">											
										</div>
									</div>
								</div>
							</div>
							<div class="form-tr">
								<div class="form-group btn-div">
									<input type="submit" class="btn btn-primary" value="保存">
									<!-- SSUI: 注意添加 class: btn-close-iframeFullPage -->
									<input type="button"
										class="btn btn-default " onclick="back();" value="返回">
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${BasePath}/asset/js/control/SWFUpload/swfupload.js"></script>
	<script type="text/javascript"
		src="${BasePath}/asset/js/common/uploadImage.js"></script>
	<script type="text/javascript"
		src="${BasePath}/asset/js/control/SWFUpload/plugins/swfupload.queue.js"></script>
		<script type="text/javascript" src="${BasePath !}/asset/js/reward/choice_share_order.js?v=${ver !}"></script>
	<script type="text/javascript">
	var detailImageValue=[];
	var imagePath='${(image_path)!}';
	var imageList='${(choice.choiceImage) !}';
	var vstatus='${(viewStatus) !}';
		$(function() {
			// SSUI: 注意：iframeFullPage 的回调函数是“第四个”参数
			executeValidateFrom('myform', '', '', function() {
				// SSUI: demo_list 为 DataTable ID
				parent.reloadData('reward_list');
				// SSUI: 所有 callback 要在关闭 closeIframeFullPage 前执行
				parent.closeIframeFullPage('myform');
			});
			for(var i=1;i<7;i++){
				uploadImage("rewardImage","detailImageBtn"+i+"",100,i);	
			}
				if(imageList !=null && imageList!=""){
						var images=imageList.split(",");
						for(var i=0;i<images.length;i++){
							detailImageValue.push(images[i]);
							$("#photoPath_img"+(i+1)+"").attr("src",imagePath+images[i].replace("_size","_origin"));
							$("#a_image"+(i+1)+"").attr("title",images[i]);
						}			
				}		
		});		
		function back(){
			$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
						function(){
							window.location.href= rootPath + "/reward/rewardList.do";
						}
					);
			}
	</script>

</body>
</html>