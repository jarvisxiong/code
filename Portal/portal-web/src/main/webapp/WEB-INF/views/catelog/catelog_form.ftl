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
    <form id="myform" action="${BasePath !}/catelog/saveData.do" method="post" class="ff-form">
       <input type="hidden" id="id" name="id" value="${(data.id) !}">         
       <input type="hidden" id="jsonStr" name="jsonStr" value="${(data.jsonStr) !}">               
       <div id="error_con" class="tips-form">
           <ul></ul>
       </div>            
        <div class="form-group">
            <label>名称：</label>
            <div>
                <input data-rule-required="true" data-msg-required="名称不能为空"
                type="text" name="name" value="${(data.name) !}"  >                     
 			</div>
        </div>
         <div class="form-group">
            <label>英文名称：</label>
            <div>
                <input data-rule-required="true" data-msg-required="英文名称不能为空"
                type="text" name="enName" value="${(data.enName) !}"  >                     
 			</div>
        </div>
        <div class="form-group">
            <label>编号：</label>
            <div>
                <input data-rule-required="true" data-msg-required="编号不能为空" type="text" id="number" name="number" value="${(data.number) !}">                     
 			</div>
        </div>   
        <div class="form-group">
            <label>序号：</label>
            <div>
                <input data-rule-required="true" data-msg-required="序号不能为空"  data-rule-isNumber="true"  data-msg-isNumber="排序值必须为数值类型" type="text" id="sort" name="sort" value="${(data.sort) !}">                     
 			</div>
        </div> 
        <div class="form-group col-span-2">
            <label>URL链接：</label>
            <div>
                <input 
                type="text" id="url" name="url" value="${(data.url) !}">                     
            </div>
        </div>
         <div class="form-group ">
            <label>父级：</label>
            <div>
                  <input type="hidden" id="parentId" name="parent.id" value="${(data.parent.id)  !}">
                      <input value="${(data.parent.name) !}" readOnly="readOnly"  type="text" id=parentName name=parentName 
                      onClick='showTree("选择父级","/catelog/treeDataList.do?type=1","parentName","parentId")'
                       >
             </div>
        </div>
         <div class="form-group">
            <label>所属页签：</label>
            <div class="f7">
                <input type="hidden" id="pageTabId" name="pageTab.id" value="${(data.pageTab.id)  !}">
                <input readOnly="readOnly" type="text" id="pageTabName" name="pageTab.name" value="${(data.pageTab.name) !}">
                <span class="selectBtn">选</span>
 			</div>
        </div>
        <div class="form-group">
            <label>数据类型：</label>
            <div>              
                <select id="catelogDataType" name="catelogDataType">
                    <#if catelogDataTypeList ??>
                    <#list catelogDataTypeList as item >                                      
                        <option value="${(item.value) !}" <#if (data.catelogDataType) ??><#if (data.catelogDataType) == (item.value)>selected="selected"</#if></#if>>${(item.name) !}</option>
                    </#list>
                    </#if>
                </select> 
            </div>
        </div>
         <div class="form-group">
            <label>排序方式：</label>
            <div>              
                <select id="catelogSortType" name="catelogSortType">
                    <#if catelogSortTypeList ??>
                    <#list catelogSortTypeList as item >                                      
                        <option value="${(item.value) !}" <#if (data.catelogSortType) ??><#if (data.catelogSortType) == (item.value)>selected="selected"</#if></#if>>${(item.name) !}</option>
                    </#list>
                    </#if>
                </select> 
            </div>
        </div>

		<div class="form-group col-span-2">
			<label>是否启用：</label>
			<div>
				<label class="radio-inline"><input name="isEnable"
					type="radio" value="1"<#if (data.isEnable) ??><#if
					data.isEnable ==1
					>checked="checked"</#if><#else>checked="checked"</#if>/>启用</label> <label
					class="radio-inline"><input name="isEnable" type="radio"
					value="0"<#if (data.isEnable) ??><#if data.isEnable == 0
					>checked="checked"</#if></#if>/>禁用</label>
			</div>
		</div>

		<div class="form-group single-row">
        <label>图片：</label>
        <div class="webuploader" id="upload_img_single"></div>
		</div>
    	<div id = "imgDiv" style="display:none;">
    	<#if imgDataList??>
        <#list imgDataList as imgItem >
        <input type='hidden' id = "${(imgItem.id) !}" name='imgs' sort="${(imgItem.sort) !}" key="${(imgItem.path) !}" value ="${(imgItem.path) !}" />
        </#list>
        </#if>
    	</div>
		<div class="clearfix"></div>
        <div class="wrapper-btn">
            <input type="submit" class="ff-btn" value="保存">
            <input type="button" class="ff-btn white btn-close-iframeFullPage" value="返回">
        </div>           
   </form>
<script type="text/javascript">
var  imgRoot  ='${(imgRoot)!}'; 
//页签选择器
$(function() {
	
	 requirejs(['ff/select2'], function(){
		$(".input-select2").select2();
	});
	
	requirejs(['ff/validate'], function(){			
		 executeValidateFrom('myform', '', '', function(){
			onClose();
		}); 
	}); 
	
	$("#pageTabName").click(function() {
		var url=rootPath+"/catelog/pagetab_selector.do";
		var pagTabDlg;
		ffzx.ui([
		         'dialog' // 浮动弹窗
		         ], function(){
			pagTabDlg = dialog({
				id:'pagetab_selector_listdata',
				title: '页签',
		        resize: false,
		        drag: false,
		        lock: true,
		        content:"<iframe  id='spframe' name='spframe,"+window.location.href+"' src='"+url+"' style='width:800px;height:500px;' frameborder='0' ></iframe>",
			    button: [
			        {
			            value: '关闭',
			            callback: function () {
			            }
			        }
			    ]
			}).showModal();
		     });
	});
	
	
	
	ffzx.ui(['upload'], function(){
		var imgArr = [];
		$("input[name='imgs']").each(function(){
		    var img = {};
		    img.id = $(this).attr("id");
		    var src = $(this).val();
		    img.src = '${(imgRoot)!}' + src.replace("size","150X150");
		    imgArr.push(img);
		  });
		
		    //上传单个图片
		    ffzx.init.upload({
		        id: 'upload_img_single',
		        type: 'image', // type: image, file
		        multiple: true, // false: 限制只上传一个图片/文件; 默认为 true: 可上传多个
		        server: '/portal-web/portal/upload.do?type=catelog&path=catelog', // Backend script receiving the file(s)
		         
		        /* 以下为可选 */
		         
		        // 已上传的图片
		       /*  uploaded: [
		            {
		                id: 1,
		                name: '表单-前端商业化.jpg',
		                src: rootPath + '/asset/img/thumbnail_01.jpg'
		            }
		        ], */
		         uploaded:imgArr,
		        // 各种回调
		        callback: {
		             
		            // Before single file selected
		            beforeFileQueued: function(file){
		                //console.log(uploaderInstance.getStats())
		            },
		                     
		            // When single file selected
		            fileQueued: function(file){ },
		             
		            // When multiple files selected
		            filesQueued: function(files){ },
		             
		            // When single file deleted
		            fileDeleted: function(file){
					$("input[value='"+file.name+"']").remove();
					initJsonStr();
					console.log(file);			
		            },
		            // Uploading
		            uploadProgress: function(file, percentage){ },
		             
		            // 'response' is returned from server
		            uploadSuccess: function(file, response){
					console.log(file);
					console.log(response);
					    var path = response.path;
					    var imgPath = response.imgPath;
			            file.name = path;
					    $("#imgDiv").append("<input type='hidden' name='imgs' key='"+file.name+"' value ='"+response.path+"' />"); 
					    initJsonStr();
		                console.log(arguments);
		            },
		             
		            // Detailed error messages are printed in console
		            uploadError: function(file){ },
		             
		            // Single file finished no matter it is uploaded successfully or not
		            uploadComplete: function(file){ },
		             
		            // All finished
		            uploadFinished: function(){ }
		        }
		    });
		     
		});
	
	initJsonStr();
	
	$(document).on('click', '.remove-img', function(){
		var $block = $(this).closest('.thumbnail');
		var id = $block.find('input:hidden').val();
		
		if (typeof $block.attr('id') == 'undefined') {
			$("#"+id).remove();
			initJsonStr();
		}
	});
	
});
function afterSelectedPagetab(obj,dlg) {
	dlg.click();
	//设置返回值存放位置
	// SSUI: 将当前行的数据填充到表单
	$("#pageTabName").val(obj.name);
	$("#pageTabId").val(obj.id);
}

function onClose() {
	parent.parent.reload();
	return false;
}
function initJsonStr(){
	var j = 1;
	var imgList = [];
	$("input[name='imgs']").each(function(){
	    var img = {};
	    img.path = $(this).val();
	    img.sort = j++;
	    imgList.push(img);
	  });
	var jsonStr = JSON.stringify(imgList);
	$("#jsonStr").val(jsonStr);
}
</script>
</body>
</html>