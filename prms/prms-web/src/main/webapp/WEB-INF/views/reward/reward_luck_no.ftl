<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>计算幸运号</title>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.config.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.all.js?v=${ver !}"></script>
	<style type="text/css">
/*计算幸运号*/
.Calculation-title{ border-bottom:1px solid #e5e5e5; color:#337ab7; font-size:16px; height:36px; line-height:36px;}
.Calculation-info{ padding:10px 0 20px 0; line-height:26px;}
.red-txt{ color:#cc0000;}
.Calculation-page{ padding:10px 0 0 0; line-height:26px;}
.Calculation-page dl{border-bottom:1px dotted #ddd; padding:10px 0; margin:0 0 10px 0;}
.Calculation-page dl dt{ font-size:15px; padding:0 0 6px 0;}
.Calculation-page p{ margin:0;}
</style>
</head>
<body><ul class="nav nav-tabs">
    <li class="active"><a href="#">计算幸运号</a></li>
</ul>

<div class="box-body">
  <div class="Calculation-title">一、基础信息</div>
  <div class="Calculation-info">
    <ul>
      <li>活动标题：${(luckNo.reward.title) !}</li>
      <li>活动期号：${(luckNo.reward.dateNo) !}</li>
      <li><span class="red-txt">开奖时间：${(luckNo.reward.rewardDate?string('yyyy-MM-dd HH:mm:ss')) !}</span>　　开始时间：${(luckNo.reward.startDate?string('yyyy-MM-dd HH:mm:ss')) !}　　结束时间：${(luckNo.reward.endDate?string('yyyy-MM-dd HH:mm:ss')) !}</li>
      <li>总参与人次：${(luckNo.takeCount) !}</li>
      <li style="padding:15px 0 0 0;">
      <#if luckNo.oneValue=='0'|| luckNo.oneValue==''>
      	<input class="btn btn-primary" value="导出50个时间数值"  type="button" disabled="disabled" onclick="exportData()" >
      <#else>
      	<input class="btn btn-primary" value="导出50个时间数值"  type="button" onclick="exportData()" >
      </#if>
        
      </li>
    </ul>
  </div>
  <div class="Calculation-title">二、幸运号计算</div>
  <form id="myform" method="post" action="${BasePath !}/reward/drawLuckNo.do">
  <input  id="id" name="id" type="hidden" class="form-control input-sm txt_big3"  value="${(luckNo.id) !}">
  <input  name="rewardId"  id="rewardId" type="hidden" class="form-control input-sm txt_big3"  value="${(luckNo.rewardId) !}">
    <div class="Calculation-page">
    <dl>
      <dd><span class="red-txt">★幸运号码=</span>（数值A+数值B）除以该商品总参与人次得到<span class="red-txt">的余数</span>+原始数10000001</dd>
    </dl>
    <dl>
      <dt>数值A</dt>
      <dd>=本期抽奖活动结束时间时，最后50个参与时间之和
        <p class="red-txt"><input style="background:transparent;border:0;color:red;font-size:18px;" id="oneValue" name="oneValue" class="form-control input-sm txt_big3"  value="${(luckNo.oneValue) !}"></p>
      </dd>
    </dl>
    <dl>
      <dt>数值B</dt>
      <dd>=本期抽奖活动结束当天，最后一期（即第120期）中国福利彩票"老时时彩"的开奖结果（一个五位的数值B），<a id="find-page-orderby-button" class="btn btn-primary btn-sm" type="submit"  target="_blank" href="http://baidu.lecai.com/lottery/draw/view/200?agentId=5555"><i class="fa fa-search"></i>&nbsp;&nbsp;开奖查询</a>
      <p class="red-txt">请输入查询出的${(luckNo.reward.rewardDate?string("yyyy-MM-dd")) !}第120期"老时时彩"的开奖结果</p>
      <p style="padding:6px 0 0 0;"><span class="red-txt"><input id="twoValue"  style="color:red;font-size:18px;" name="twoValue" value="${(luckNo.twoValue) !}" class="form-control input-sm txt_big3" type="text"  data-rule-required="true"  data-msg-required="数字B不能为空"  data-rule-isIntGtZero="true"  data-msg-isIntGtZero="数字B必须是整数" ></span></p></dd>
    </dl>
        <dl>
      <dt>总参与人次</dt>
      <dd>=本期抽奖活动结束时间时，总参与人次<p class="red-txt"><input style="background:transparent;border:0;color:red;font-size:18px;" id="takeCount" name="takeCount" class="form-control input-sm txt_big3"  value="${(luckNo.takeCount) !}"></p></dd>
    </dl>
  </div>
    <div class="Calculation-info">
    <ul>
    <#if luckNo?? && luckNo.luckNo?? >
	     <li style="padding:0 0 15px 0;">
	        <input id="drawLuck" class="btn btn-primary" value="再次计算幸运号" type="button" onclick="drawLuckNo()">
	    </li>
    	<div id="draw_result" >
    		<li>计算结果：</li>
		    <li>幸运号码<span class="red-txt" style="font-size:18px;">=${(luckNo.luckNo) !}</span></li>
		    <li>中奖人（会员账号）：${(luckNo.record.phone) !}</li>
    	</div>
    <#else>
	     <li style="padding:0 0 15px 0;">
	        <input id="drawLuck" class="btn btn-primary" value="计算幸运号" type="button" onclick="drawLuckNo()">
	    </li>
    </#if>

    <li style="padding:0 0 15px 0;">
        <input type="button" class="btn btn-default " onclick="back();" value="返回">
    </li> 
    </ul>
  </div>
  </form>

</div>
<script type="text/javascript">
var number='${(luckNo.luckNo) !}';
var luckName='${(luckNo.record.phone) !}';
	$(function(){
		// SSUI: 注意：iframeFullPage 的回调函数是“第四个”参数
		executeValidateFrom('myform','', '', function(){
			// SSUI: demo_list 为 DataTable ID
			parent.reloadData('reward_list');
			// SSUI: 所有 callback 要在关闭 closeIframeFullPage 前执行
			parent.closeIframeFullPage('reward_list');
			if(number!=null && luckName!=null){
				$("#drawLuck").css("display","");
			}
		});
	});
	  	function drawLuckNo(){
	  		var B=$("#twoValue").val();
	  		if(parseInt(B)<0){
	  			$.frontEngineDialog.executeDialogOK('提示信息','数值B必须为正整数','300px');
	  			return false;
	  		}
	  		else if(B.length<5 || B.length>5){
	  			$.frontEngineDialog.executeDialogOK('提示信息','数值B必须是5位数字','300px');	
	  			return false;
	  		}else{
	  			$.frontEngineDialog.executeDialog('saveLuckNo','提示信息','请再次核对开奖日期,第120期“老时时彩”的开奖结果是否为正确？点确定，将执行计算幸运号。','300px','',
	  					function(){
	  				console.log("aa");
	  		  		$("#myform").submit();
							});
	  		}

	  	}
	  	
	  	function exportData(){	  		
	  		var rewardId=$("#rewardId").val();
	  		var url = rootPath + '/reward/joinRecordExport.do?rewardId='+rewardId;		
	  		window.location.href=url;
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