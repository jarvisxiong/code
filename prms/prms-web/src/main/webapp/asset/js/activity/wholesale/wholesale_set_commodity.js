function toSelectCommdity(){
		var url = rootPath + '/wholesaleActivity/toCommdity.do';		
		var dlg=dialog({
            id: "toSelectCommdityForm",
            title: '商品设置-新增',
            lock: false,
            content:"<iframe  name='toSelectCommdityForm,"+window.location.href+"' src='"+url+"' width='950px' height='350px' frameborder='0' scrolling='auto' id='toSelectCommdityForm' style='overflow-x:hidden; '></iframe>",       
		}).showModal();
}


function resultData(result){
	 if(result && result.status=="success"){
		 parent.location.reload(false);
     }else{
    	 $.frontEngineDialog.executeDialogContentTime(result.infoStr,2000);
     }    
}

//验证数据
var valid = true;
// 提交表单
function btnsubmit(){
	// 赋值,并验证
	setValues();
	if (valid) {
		$("#myform").submit();
	}
}

/**
 * 单选按钮选择
 */
function radioChecked(obj){
	var v = $(obj).val(); // 单选按钮的值
	var str = ""
	if ("0" == v) {
		str = "<tr><td align='center'><input type='text' name='selectionStart' onkeyup='onlyInputNum(this);' onblur='changNumber(this,0,0)' value=''/></td><td align='center'></td>" +
				"<td align='center'><input type='text' name='activityPrice' onblur='changPrice()' onkeyup='onlyInputPrice(this);' value=''/></td></tr>";
	} else if("1" == v){
		str = "<tr><td align='center'><input type='text' name='selectionStart' onkeyup='onlyInputNum(this);' value=''/>&nbsp;&nbsp;&nbsp;<input type='text' name='selectionEnd' onkeyup='onlyInputNum(this);' onblur='changNumber(this,0,1)' value=''/></td>" +
				"<td align='center'></td><td align='center'><input type='text' name='activityPrice' onblur='changPrice()' onkeyup='onlyInputPrice(this);' value=''/></td>" +
				"</tr>"+
			  "<tr><td align='center'><input type='text' name='selectionStart' onkeyup='onlyInputNum(this);' readonly='readonly' value=''/></td><td align='center'></td>" +
				"<td align='center'><input type='text' name='activityPrice' onblur='changPrice()' onkeyup='onlyInputPrice(this);' value=''/></td></tr>";
	} else if("2" == v){
		str = "<tr><td align='center'><input type='text' name='selectionStart' onkeyup='onlyInputNum(this);' value=''/>&nbsp;&nbsp;&nbsp;<input type='text' name='selectionEnd' onkeyup='onlyInputNum(this);' onblur='changNumber(this,0,1)' value=''/></td>" +
				"<td align='center'></td><td align='center'><input type='text' name='activityPrice' onblur='changPrice()' onkeyup='onlyInputPrice(this);' value=''/></td>" +
				"</tr>"+
			  "<tr><td align='center'><input type='text' onkeyup='onlyInputNum(this);' name='selectionStart' readonly='readonly' value=''/>&nbsp;&nbsp;&nbsp;<input type='text' name='selectionEnd' onkeyup='onlyInputNum(this);' onblur='changNumber(this,1,1)' value=''/></td>" +
				"<td align='center'></td><td align='center'><input type='text' name='activityPrice' onblur='changPrice()' onkeyup='onlyInputPrice(this);' value=''/></td>" +
				"</tr>"+
			  "<tr><td align='center'><input type='text' name='selectionStart' onkeyup='onlyInputNum(this);' readonly='readonly' value=''/></td><td align='center'></td>" +
				"<td align='center'><input type='text' name='activityPrice' onblur='changPrice()' onkeyup='onlyInputPrice(this);' value=''/></td></tr>";
	}
	// 清空
	$("#dateTr").html("");
	// 添加
	$("#dateTr").append(str);
}

/**
 * 购买数
 * @param obj
 */
function changNumber(obj,index,position){
	var count = $(obj).val();
	var buyCount = "0";
	var temp = document.getElementsByName("buyCount");
	for(var i=0;i<temp.length;i++) {
	     if(temp[i].checked)
	    	 buyCount = temp[i].value;
	}
	// 操作数据
	if ("" == count) {
		$.frontEngineDialog.executeDialogContentTime("购买数不能为空",1500);
		return;
	}
	// 编辑结束购买数
	if (1 == position) {
		$("#dateTr tr:eq("+(index+1)+")").find("input:eq(0)").val(parseInt(count)+1);
	}
	// 取值
	var startCount = $(obj).parent().find("input:eq(0)").val();
	var endCount = $(obj).parent().find("input:eq(1)").val();
	// 购买数
	if ("0" == buyCount) {
		if ("" != startCount) {
			$(obj).parent().parent().find("td").eq(1).text("["+startCount+" , ]");
		} else {
			$.frontEngineDialog.executeDialogContentTime("第"+(index+1)+"行的开始购买数不能为空",1500);
		}
	} else if ("1" == buyCount) {
		// 当前对象--操作
		if ("" != startCount && "" != endCount) {
			// 比较两个值
			if (parseInt(startCount) >= parseInt(endCount)) {
				$.frontEngineDialog.executeDialogContentTime("结束值应该大于起始值，请填写正确",1500);
			} else {
				$(obj).parent().parent().find("td:eq(1)").text("["+startCount+" , "+endCount+"]");
			}
		} else {
			$.frontEngineDialog.executeDialogContentTime("第"+(index+1)+"行的开始/结束购买数不能为空",1500);
		}
		// 下一个对象--操作
		var sCount = $("#dateTr tr:eq("+(index+1)+")").find("input:eq(0)").val();
		$("#dateTr tr:eq("+(index+1)+")").find("td").eq(1).text("["+sCount+" , ]");
	} else {
		// 当前操作对象
		if (0 == index) {
			// 当前对象--操作
			if ("" != startCount && "" != endCount) {
				// 比较两个值
				if (parseInt(startCount) >= parseInt(endCount)) {
					$.frontEngineDialog.executeDialogContentTime("结束值应该大于起始值，请填写正确",1500);
				} else {
					$(obj).parent().parent().find("td:eq(1)").text("["+startCount+" , "+endCount+"]");
				}
			} else {
				$.frontEngineDialog.executeDialogContentTime("第"+(index+1)+"行的开始/结束购买数不能为空",1500);
			}
			// 下一个对象--操作
			var sCount = $("#dateTr tr:eq("+(index+1)+")").find("input:eq(0)").val();
			var eCount = $("#dateTr tr:eq("+(index+1)+")").find("input:eq(1)").val();
			if ("" != startCount && "" != endCount) {
				// 比较两个值
				if (parseInt(sCount) >= parseInt(eCount)) {
					$.frontEngineDialog.executeDialogContentTime("结束值应该大于起始值，请填写正确",1500);
				} else {
					$("#dateTr tr:eq("+(index+1)+")").find("td").eq(1).text("["+sCount+" , "+eCount+"]");
				}
			} else {
				$.frontEngineDialog.executeDialogContentTime("第"+(index+2)+"行的开始/结束购买数不能为空",1500);
			}
		} else if(1 == index) {
			// 当前对象--操作
			if ("" != startCount && "" != endCount) {
				// 比较两个值
				if (parseInt(startCount) >= parseInt(endCount)) {
					$.frontEngineDialog.executeDialogContentTime("结束值应该大于起始值，请填写正确",1500);
				} else {
					$(obj).parent().parent().find("td:eq(1)").text("["+startCount+" , "+endCount+"]");
				}
			} else {
				$.frontEngineDialog.executeDialogContentTime("第"+(index+1)+"行的开始/结束购买数不能为空",1500);
			}
			// 下一个对象--操作
			var sCount = $("#dateTr tr:eq("+(index+1)+")").find("input:eq(0)").val();
			$("#dateTr tr:eq("+(index+1)+")").find("td").eq(1).text("["+sCount+" , ]");
		}
		
	}
	
}


function changPrice(){
	// 最高价格、最低价格
	var min = "0";
	var max = "0";
	// 遍历，获取最大值、最小值
	$("#dateTr tr").each(function(index){
		var price = $(this).find("input[name='activityPrice']").val();
		if ("" != price && null != price) {
			if (index == 0) {
				min = price;
				max = price;
			} else {
				if (parseFloat(min) > parseFloat(price)) {
					min = price;
				}
				if(parseFloat(max) < parseFloat(price)) {
					max = price;
				}
			}
		}
	});
	// 给隐藏域赋值：最大值最小值相同，则给一个；否则给区间值
	if (min == max) {
		$("#showPrice").val(min);
	} else {
		$("#showPrice").val(min+" - "+max);
	}
}


// 赋值数据，验证数据
function setValues(){
	var jsonObj = "[";
	// 遍历表格
	$("#dateTr tr").each(function(index){
//		var skuId = $("#commodityId").val();	// item的商品skuid
//		var skuCode = $("#commodityNo").val();	// item的商品sku编码
//		var skuBarcode = $("#commodityBarcode").val();	// item的商品sku条形码
		var skuCode = $("#commodityNo").val();	// item的商品编码
		var skuTitle = $("#commName").val().replace(/[\r\n]/g,"");	// 商品标题
		var skuPrice = $("#price").val();	// 商品价格
		var selectionStart = $(this).find("input[name='selectionStart']").val();	// 开始购买数
		var selectionEnd = $(this).find("input[name='selectionEnd']").val();	// 结束购买数
		// 非空验证
		if ("" == selectionStart) {
			valid = false;
			$.frontEngineDialog.executeDialogContentTime("第"+(index+1)+"行的开始购买数不能为空",1500);
		}
		if ("" == selectionEnd) {
			if (index != 2) {
				valid = false;
				$.frontEngineDialog.executeDialogContentTime("第"+(index+1)+"行的结束购买数不能为空",1500);
			}
		}
		// 值验证
		if (parseInt(selectionStart) >= parseInt(selectionEnd)) {
			valid = false;
			$.frontEngineDialog.executeDialogContentTime("结束值应该大于起始值，请填写正确",1500);
		}
		if ("undefined" == typeof(selectionEnd)) {
			selectionEnd = "";
		}
		var activityPrice = $(this).find("input[name='activityPrice']").val();	// item的商品活动价
		// 验证
		if ("" == selectionStart || "" == activityPrice) {
			valid = false;
			$.frontEngineDialog.executeDialogContentTime("请填写完整数据",1500);
		}
		// 拼接json
		jsonObj += "{'commodityNo':'"+skuCode+"','commoditySkuId':'','commoditySkuNo':'','commoditySkuBarcode':'',"+
					"'commoditySkuTitle':'"+skuTitle+"'," +
					"'attrGroup':'','commoditySkuPrice':"+skuPrice+",'selectionStart':'"+selectionStart+"'," +
					"'selectionEnd':'"+selectionEnd+"','activityPrice':"+activityPrice+"},";
	});
	jsonObj = jsonObj.substring(0, jsonObj.length-1);
	jsonObj += "]";
	// 赋值
	$("#activityitemDate").val(jsonObj);
}

/**
 * 返回
 */
function toBack(){
	var actId = $("#activityId").val(); // 活动id
	var actNo = $("#activityNo").val(); // 活动No
	window.location.href = rootPath + '/wholesaleActivity/commoditylist.do?activityId='+actId+'&activityNo='+actNo;
}
