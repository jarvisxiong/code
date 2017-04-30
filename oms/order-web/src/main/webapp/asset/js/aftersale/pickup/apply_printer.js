/**
 * 雷---2016-07-27---加载
 */
$(function() {
	/**
	 * 申请售后原因选择，分析结果
	 * 
	 */
	var prefix = "原因选择：";
	var orderStatus = $("#orderStatus").html();
	// console.log(orderStatus+"orderStatus________"+(orderStatus=="16"));
	switch (orderStatus) {
	case "0":
		$("#orderStatus").html(prefix + "长时间没收到货");
		break;
	case "1":
		$("#orderStatus").html(prefix + "信息填写错误，没收到货");
		break;
	case "2":
		$("#orderStatus").html(prefix + "没收到货，不想要了");
		break;
	case "3":
		$("#orderStatus").html(prefix + "其他原因");
		break;
	case "14":
		$("#orderStatus").html(prefix + "收到商品破损");
		break;
	case "15":
		$("#orderStatus").html(prefix + "商品错发/漏发");
		break;
	case "16":
		$("#orderStatus").html(prefix + "收到商品不符");
		break;
	case "17":
		$("#orderStatus").html(prefix + "商品质量问题");
		break;
	case "18":
		$("#orderStatus").html(prefix + "商品比周边商贵");
		break;
	case "31":
		$("#orderStatus").html(prefix + "收到商品破损");
		break;
	case "32":
		$("#orderStatus").html(prefix + "商品错发/漏发");
		break;
	case "33":
		$("#orderStatus").html(prefix + "商品质量问题");
		break;
	case "30":
		$("#orderStatus").html(prefix + "其他原因");
		break;

	}
});
