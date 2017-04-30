
//控制输入的是数字 -- 价格
function onlyInputPrice(obj) {
	// 先把非数字的都替换掉，除了数字和.    negative属性为true可输入负数
	var negative = $(obj).attr("negative");
	if(obj.value.indexOf("-") == 0 && negative == "false"){
		obj.value = obj.value.replace(/[^\d]/g, "");
	}else{
		obj.value = obj.value.replace(/[A-Za-z]/g, "");
	}
	// 保证第一个不是.
	if (obj.value.indexOf(".") == 0) {
		obj.value = obj.value.replace(".", "");
	}
	// 不能存在两个.
	if (obj.value.indexOf(".") < obj.value.lastIndexOf(".")) {
		obj.value = obj.value.substring(0, obj.value.length-1);
	}
}

//控制输入的是数字 -- 整数
function onlyInputNum(obj) {
	// 先把非数字的都替换掉，除了数字和.    negative属性为true可输入负数
	var negative = $(obj).attr("negative");
	if(obj.value.indexOf("-") == 0 && negative == "true"){
		obj.value = obj.value.replace(/[^\d]/g, "");
	}else{
		obj.value = obj.value.replace(/[^\d]/g, "");
	}
}