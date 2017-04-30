<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>新增</title>
</head>
<body>
	<style>
	.form-group .ztree{max-height:100%;margin-top:5px;margin-left:-5px;}

	</style>
    <form id="myform" action="${BasePath !}/catelogData/saveData.do" method="post" class="ff-form">
       <input type="hidden" id="id" name="id" value="${(catelogData.id) !}">                    
       <input type="hidden" id="catelogId" name="catelogId" value="${(catelogId)! }">
       <input type="hidden" id="tempContent" name="tempContent" value="${(catelogData.content) !}">
       <input type="hidden" id="imageUrl" name="imageUrl" value="${(catelogData.imageUrl) !}">
       <input type="hidden" id="imgRootPath" name="imgRootPath" value="${(image_path)!}">
       <div id="error_con" class="tips-form">
           <ul></ul>
       </div>            
           
        <div class="form-group">
            <label><i>*</i>标题：</label>
            <div>
                <input type="text" id="title" name="title" value="${(catelogData.title) !}" 
                data-rule-required="true" data-msg-required="标题不能为空">                  
 			</div>
        </div>
        <div class="form-group">
            <label>关键字：</label>
            <div>
                <input type="text" id="key" name="key" value="${(catelogData.key) !}">                     
            </div>
        </div>
        
          <div class="form-group">
            <label>url1：</label>
            <div>
                <input type="text" id="url1" name="url1" value="${(catelogData.url1) !}">                     
            </div>
        </div>
        
        <div class="form-group">
            <label>url2：</label>
            <div>
                <input type="text" id="url2" name="url2" value="${(catelogData.url2) !}">                     
            </div>
        </div>
        
         <div class="form-group single-row">
            <label>内容：</label>
            <div>
           		 <script type="text/plain" id="content" name="content" class="ueditor"></script>
            </div>
        </div>
       
        
        <div class="form-group single-row">
            <label>图片：</label>
            <div>
                <div class="webuploader" id="upload_img_single"></div>                   
            </div>
        </div>
     

		<div class="clearfix"></div>
		
               
    
        <div class="wrapper-btn">
            <input type="submit" class="ff-btn" value="保存">
            <input type="button" class="ff-btn white btn-close-iframeFullPage" value="返回">
        </div>           

   </form>

<script type="text/javascript">

var imgRootPath = $("#imgRootPath").val();
var imgSize = "_150X150";
function findUploadedImgArr(){
	var nowPath = $("#imageUrl").val();
	
	if(nowPath==null||nowPath==""||nowPath.trim()==","){
		return [];
	}
	
	var imgArr = nowPath.split(",");
	var finalImgArr = [];
	$.each(imgArr,function(index,obj){
		finalImgArr[index] = {
                src: imgRootPath+obj.replace("_size",imgSize)
		}
	})
	return finalImgArr;
}

var imgArr = findUploadedImgArr();
function updateImageUrl(path,type){
	var nowPath = $("#imageUrl").val();
	if(type=="REMOVE"){
		nowPath = nowPath.replace(new RegExp(path, 'g'),"");
	}else{
		if(nowPath.length>0){
			nowPath+=",";
		}
		nowPath+=path;
	}
	
	nowPath = nowPath.replace(/\,,/g,",");
	if(nowPath.charAt(0)==","){
		nowPath = nowPath.substring(1,nowPath.length);
	}
	if(nowPath.charAt(nowPath.length-1)==","){
		nowPath = nowPath.substring(0,nowPath.length-1);
	}
	$("#imageUrl").val(nowPath);

}

$(function() {
	ffzx.ui([ 'select2','validate'], function(){
		$("select").select2();
		executeValidateFrom('myform', '', '', function(){
			parent.reloadData('catelogData_list');
		});
    });
	ffzx.ui(['upload'], function(){
	    ffzx.init.upload({
	        id: 'upload_img_single',
	        type: 'image', 
	        multiple: true, 
	        server: '/portal-web/portal/upload.do', 
	        uploaded: imgArr,
	        callback: {
	            uploadSuccess: function(file, response){
	            	if(response.status=='0'){
	            		var path = response.path;
				        file.name = path;
				        updateImageUrl(path,"ADD");
	            	}
	            },
	            fileDeleted: function(file){
	            	console.log(file)
	            	updateImageUrl(file.name,"REMOVE");
	            }
	        }
	    });
	}); 
	$("#content").val($("#tempContent").val());
	
	   $(document).on('click', '.remove-img', function(){
			var fullPath = $(this).prev().attr("src");
			fullPath = fullPath.replace(imgRootPath,"");
			fullPath = fullPath.replace(imgSize,"_size");
			updateImageUrl(fullPath,"REMOVE");
	   });
});
</script>
</body>
</html>