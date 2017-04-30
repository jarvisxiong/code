<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>扫码确认</title>
</head>
<body>
<div class="btn-div4">
	<a href="javascript:void(0);" onclick="switchInput('scan');" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i> 扫描输入</a>
	<a href="javascript:void(0);" onclick="switchInput('manual');" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i> 手动输入</a>
</div>
<div class="inquire-ul" style="margin-top:0;">
	<div class="form-tr" style="overflow:hidden;">
	    <div class="form-td">
	      <label style="width:auto;"><span id="input_mode">"被扫描"的客户签收单条形码：</span></label>
	      <div class="div-form">
	        <input type="text" class="form-control input-sm txt_big3" id="barCodeSigned" name="barCodeSigned">
	      </div>
	    </div>
	  <input type="hidden" id="flag" value="${flag !}"/>
	  <input type="hidden" id="opFlag" value="S"/>
	  
	</div>
</div>
  <div class="tips-form" style="padding:0 0 30px 0; margin:0;color:red;">提醒：请用扫描枪 "扫描客户签收单条形码" 区域  或者 "手动输入客户签收单条形码并按Enter键"完成确认回收。</div>

<script type="text/javascript" src="${BasePath}/asset/js/orderRecycle/scan_confirm.js?v=${ver !}"></script>
</body>
</html>