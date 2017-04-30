<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>设置晒单</title>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.config.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.all.js?v=${ver !}"></script>
	
</head>
<body><ul class="nav nav-tabs">
    <li class="active"><a href="#">${(shareOrder.id)???string('编辑','新增')}</a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">

				<form id="myform" method="post" action="${BasePath !}/reward/addShareOrder.do">
					<input  type="hidden" name="id" value="${(shareOrder.id) !}" /> 
					<input  type="hidden" name="rewardId" value="${(shareOrder.rewardId) !}" /> 
					<div class="addForm1">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>中奖期号：</label>
								<div class="div-form">
									<input id="rewardDateNo" name="rewardDateNo" value="${(shareOrder.rewardDateNo) !}" class="form-control input-sm txt_mid" type="text"  readonly="readonly">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>中奖人：</label>
								<div class="div-form">
									<input id="luckName" name="luckName" value="${(shareOrder.luckName) !}" class="form-control input-sm txt_mid" type="text"  readonly="readonly" data-rule-required="true" data-msg-required="没有中奖人">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>领奖地址：</label>
								<div class="div-form">
									<input id="receiveAddress" name="receiveAddress" value="${(shareOrder.receiveAddress) !}" class="form-control input-sm txt_mid" type="text" data-rule-required="true" data-msg-required="领奖地址不可以为空">
								</div>
							</div>
						</div>

				<div class="form-tr">
	        		<div class="form-td">
	        		<label><i>*</i>晒单图片：
	        		</label>
      	 				<div class="div-form">	
	                    	图片尺寸320*214，图片大小100KB以内，图片格式为jpg、png
                        </div>
	                </div>	                
	        	</div>
	        	<div class="form-tr">
	        		<div class="form-td">
	        		<label></label>
      	 				<div class="div-form">	
	                    	<div style="border:1px solid #ccc; width:100px; height:100px;">
	                            <img id="photoPath_img" style="width:100px; height:100px;" <#if (shareOrder.showImage)?? >src="${(image_path)!}/${shareOrder.showImage?replace('size','origin')}"</#if>   onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
	                        </div>
	                          	<input type="hidden" id="showImage" name="showImage" value="${(shareOrder.showImage) !}"  >
	                          	<input type="hidden"  id="photoPathBtn">
	                          	<input type="hidden" value="${(jsessionId) !}" id="jsessionid">
                        </div>
	                </div>	                
	        	</div>
						<div class="form-tr">
	                    	<div class="form-group btn-div">
								<input type="submit" class="btn btn-primary" value="保存">
								<!-- SSUI: 注意添加 class: btn-close-iframeFullPage -->
								<input type="button" class="btn btn-default" onclick="back()" value="返回">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--新增1--end-->

	</div>
</div>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/asset/js/common/uploadImage.js"></script>
<script type="text/javascript">
	$(function(){
		// SSUI: 注意：iframeFullPage 的回调函数是“第四个”参数
		executeValidateFrom('myform','', '', function(){
			// SSUI: demo_list 为 DataTable ID
			parent.reloadData('reward_list');
			// SSUI: 所有 callback 要在关闭 closeIframeFullPage 前执行
			parent.closeIframeFullPage('reward_list');
		});
		  // 上传图片
		initUploadImage(showImg,"rewardImage","photoPathBtn",100);
	});
	  	//图片上传成功   业务处理事件
		function showImg(data){	
			var res = eval('(' + data + ')');	
			if(res.status == "0"){
				$("#showImage").val(res.path);		//path  图片地址用于数据库存储
				$("#photoPath_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
			}
			
			//上传成功
			$.frontEngineDialog.executeDialogContentTime(res.infoStr);
		}
	  	
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