<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>添加页面</title>	
	<style type="text/css">
		.question{ border:1px solid  #C17103; background:#FFE668; color:#754F0E;border-radius:3px;}
		.viewlogistics{position:relative;}
		.viewlogistics .wrapper-aftersales-status{ display:none; position:absolute;top:22px;top:10px; z-index:100; width:500px;left:13px;background-color: #fff8d2;text-align: left;line-height: 25px;padding:10px 10px 10px 10px ;}
		 .viewlogistics:hover .wrapper-aftersales-status {display:block;} 
	</style>
</head>
<body><ul class="nav nav-tabs">
    <li class="active"><a href="#">
    <#if viewStatus??>
    	<#if viewStatus=='view'>
    		免费抽奖详情
    	<#elseif viewStatus=='edit'>编辑
    	<#else>新增</#if>
    </#if>
    </a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">

				<form id="myform" method="post" action="${BasePath !}/reward/saveReward.do">
					<input  type="hidden" name="id" value="${(reward.id) !}" /> 
					<input  type="hidden" name="rewardNo" value="${(reward.rewardNo) !}" /> 
					<div class="addForm1">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label title="0"><i>*</i>活动标题：
								<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:100;">
									<div class="wrapper-aftersales-status">								
										<div class="logistics status0" ></div>
									</div>
								</i>
								</label>
								<div class="div-form">
									<input id="title" name="title" value="${(reward.title) !}" class="form-control input-sm txt_mid" type="text" data-rule-required="true"  data-msg-required="标题不能为空" data-rule-rangelength="[1,20]"  data-msg-rangelength="标题不可以超过20个字">
								</div>
							</div>
							</div>
						<div class="form-tr">
							<div class="form-td">
								<label title="1"><i>*</i>抽奖期号：<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:99;">
								<div class="wrapper-aftersales-status">								
										<div class="logistics status1" ></div>
									</div>
								</i>
								</label>
								<div class="div-form">
									<input id="dateNo" name="dateNo" value="${(reward.dateNo) !}" class="form-control input-sm txt_mid" type="text" data-rule-required="true"   data-msg-required="抽奖期号不能为空" 
									 data-rule-isIntGtZero="true"  data-msg-isIntGtZero="抽奖期必须是大于0的整数" data-rule-rangelength="[1,4]"  data-msg-rangelength="期号不可以超过4位">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label title="2"><i>*</i>开始时间：<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:98;">
								<div class="wrapper-aftersales-status" >								
									<div class="logistics status2" ></div>
								</div></i></label>
								<div class="div-form">
									 <input name="startDateStartStr" id="startDateStartStr" class="form-control txt_mid input-sm"  data-rule-required="true" data-msg-required="开始时间不能为空"
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(reward.startDate?string('yyyy-MM-dd HH:mm:ss')) !}">
								</div>
							</div>
							<div class="form-td">
								<label title="3"><i>*</i>结束时间：<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:97;">
								<div class="wrapper-aftersales-status" >								
									<div class="logistics status3" ></div>
								</div></i></label>
								<div class="div-form">
									 <input name="endDateStartStr" id="endDateStartStr" class="form-control txt_mid input-sm"   data-rule-required="true" data-msg-required="结束时间不能为空"
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',onpicking:toSetRewardDate})"  value="${(reward.endDate?string('yyyy-MM-dd HH:mm:ss')) !}">
								</div>
							</div>
							<div class="form-td">
								<label title="4"><i>*</i>开奖时间：<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:96;">
								<div class="wrapper-aftersales-status" >								
									<div class="logistics status4" ></div>
								</div></i></label>
								<div class="div-form">
									 <input name="rewardDateStartStr" id="rewardDateStartStr"  data-rule-required="true" data-msg-required="开奖时间不能为空" readonly="readonly" class="form-control txt_mid input-sm"  value="${(reward.rewardDate?string('yyyy-MM-dd HH:mm:ss')) !}">
								</div>
						  </div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label title="5"><i>*</i>市场价：<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:95;">
								<div class="wrapper-aftersales-status" >								
									<div class="logistics status5" ></div>
								</div></i></label>
								<div class="div-form">
										<input id="price" name="price" value="${(reward.price) !}" class="form-control input-sm txt_mid" type="text" 
										data-rule-required="true" data-msg-required="市场价不能为空"  data-rule-isFloatGteZero="true" data-msg-isFloatGteZero="市场价必须大于或等于0"  data-rule-decimal="true" data-msg-decimal="市场价小数位数不能超过两位" >
								 </div>
							 </div>
						 </div>
				<div class="form-tr">
	        		<div class="form-td">
	        		<label title="6"><i>*</i>活动列表图：<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:94;">
								<div class="wrapper-aftersales-status" >								
									<div class="logistics status6" ></div>
								</div></i></label>
      	 				<div class="div-form">	
	                    	图片尺寸250*250，图片大小100KB以内，图片格式为jpg、png
                        </div>
	                </div>	                
	        	</div>
	        	<div class="form-tr">
	        		<div class="form-td">
	        		<label></label>
      	 				<div class="div-form">	
	                    	<div style="border:1px solid #ccc; width:100px; height:100px;">
	                            <img id="photoPath_img" style="width:100px; height:100px;" <#if (reward.showImage)?? >src="${(image_path)!}${reward.showImage?replace('size','origin')}"</#if>   onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
	                        </div>
	                          	<input type="hidden" id="showImage" name="showImage" value="${(reward.showImage) !}"  data-rule-required="true"  data-msg-required="列表图片不能为空" >
	                          	<input type="hidden"  id="photoPathBtn">
	                          	<input type="hidden" value="${(jsessionId) !}" id="jsessionid">
                        </div>
	                </div>	                
	        	</div>
	        	<div class="form-tr">
	        		<div class="form-td">
	        		<label title="7"><i>*</i>活动详情图：<i class="viewlogistics logistics-hover fa fa-question question" style="z-index:93;">
								<div class="wrapper-aftersales-status" >								
									<div class="logistics status7" ></div>
								</div></i></label>
      	 				<div class="div-form">	
	                    	图片尺寸800*800，图片大小100KB以内，图片格式为jpg、png  
                        </div>
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
		                    <input type="hidden" id="detailImage" name="detailImage" value="${(reward.detailImage) !}"  data-rule-required="true"  data-msg-required="详情图片不能为空">
	               		 </div>
					</div>
	                </div>             
	        	</div>
	        	<#if viewStatus?? && viewStatus=='view'>
	        		      <div class="form-tr">
				        	<div class="tab-content">
			                <div class="tab-pane fade in active" id="sale-state">
			                    <table class="table table-hover table-striped table-common">
			                        <thead>
			                        <tr>
			                            <th>处理时间</th>
			                            <th>处理信息</th>
			                            <th>操作人</th>
			                        </tr>
			                        </thead>
			                        <tbody>
			             			 <#if operateRecordList?? > 
											<#list operateRecordList as item >								
													 <tr>
														<td>${(item.recordDate?string("yyyy-MM-dd HH:mm:ss")) !} </td>
														<td>${(item.content) !} </td>
														<td>${(item.recordUser) !} </td>	
													 </tr>
											</#list>
										</#if>    
			                        </tbody>
			                    </table>
			                </div>
			            </div>
				        </div>
	        			</#if>         
						<div class="form-tr">
	                    	<div class="form-group btn-div">
	                    	<#if viewStatus?? && viewStatus!='view'>
	                    		<input type="submit" class="btn btn-primary" value="保存">
	                    	</#if>   								
								<!-- SSUI: 注意添加 class: btn-close-iframeFullPage -->
								<input type="button" class="btn btn-default " onclick="back();" value="返回">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/asset/js/common/uploadImage.js"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/plugins/swfupload.queue.js"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/reward/reward_form.js?v=${ver !}"></script>
<script type="text/javascript">
var detailImageValue=[];
var imagePath='${(image_path)!}';
var vstatus='${(viewStatus) !}';
var imageList='${(reward.detailImage) !}';
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
		
		for(var i=1;i<7;i++){
			uploadImage("rewardImage","detailImageBtn"+i+"",100,i);	
		}
 		if(vstatus=="view" || vstatus=="edit"){
			if(imageList !=null){
					var images=imageList.split(",");
					for(var i=0;i<images.length;i++){
						detailImageValue.push(images[i]);
						$("#photoPath_img"+(i+1)+"").attr("src",imagePath+images[i].replace("_size","_origin"));
						$("#a_image"+(i+1)+"").attr("title",images[i]);
					}			
					console.log(detailImageValue);
			}		
		}
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