<html>
<head>
<meta name="decorator" content="list" />
<title>链接页面</title>
</head>
<body>
	<input id="couponUrl" style="width:400px;" type="text" disabled="true" value="${url !}?id=${id !}"> <button id='copyCouponUrl' class='btn btn-primary btn-sm btn-inquire'>复制</button>
	<script type="text/javascript">
	$(document).ready(function () {       
	    init();   
	});  
	function init() {
		var clip = new ZeroClipboard.Client(); // 新建一个对象
		clip.setHandCursor( true );
		clip.addEventListener('mouseDown', function (client) {  //创建监听  
			clip.setText($('#couponUrl').val()); // 设置要复制的文本。
		}); 
		// 注册一个 button，参数为 id。点击这个 button 就会复制。
		//这个 button 不一定要求是一个 input 按钮，也可以是其他 DOM 元素。
		 // 和上一句位置不可调换
		clip.glue("copyCouponUrl");
		clip.addEventListener('complete',function(){
			parent.closeDialog();
		});
	}
	</script>
	<script type="text/javascript" src="${BasePath !}/asset/js/common/ZeroClipboard.js?v=${ver !}"></script>
</body>
</html>
