/**
 * 雷---2016-08-10批量导出售后申请单
 */
function importApplyList() {
	var data = $("#find-page-orderby-form").serialize();
	var flag = beforeImportApplyList(data);
	if (flag) {
		$("#orderImport_iframe").attr("src",
				rootPath + "/aftersaleapply/importApplyList.do?" + data);
	}

}
/**
 * 雷---2016-08-10批量导出售后申请单
 */
function importRefundList() {
	var data = $("#find-page-orderby-form").serialize();
	var flag = beforeImportApplyList(data);
	if (flag) {
		$("#orderImport_iframe").attr("src",
				rootPath + "/aftersaleapply/importRefundList.do?" + data);
	}

}
/**
 * 雷---2016-08-10导出之前要执行的方法
 */
function beforeImportApplyList(data) {
	var flag = false;
	$.ajax({
		url : rootPath + "/aftersaleapply/beforeImportApplyList.do",
		data : data,
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (!result.flag) {
				$.frontEngineDialog.executeDialogContentTime(result.msg, 2000);
			}
			flag = result.flag;
		}
	});
	return flag;
}

/**
 * 雷---2016-07-28---取消审核
 */
function cancelAudit(pickupNo, orderNo, applyID) {
	/**
	 * 
	 * 
	 */
	$("#cancelAudit").hide();
	$.ajax({
		url : rootPath + "/aftersaleapply/cancelAudit.do",
		data : {
			pickupNo : pickupNo,
			orderNo : orderNo,
			applyID : applyID
		},
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		beforeSend : function() {
			FFZX.mask('show');
		},
		success : function(result) {
			FFZX.mask('hide');
			$.frontEngineDialog.executeDialogContentTime(result.msg, 2000);
			setTimeout(function() {
				window.location.reload(true);
			}, 2000);
		},
		complete : function() {
			FFZX.mask('hide');
		}
	});

}
