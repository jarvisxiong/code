<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<body style="overflow: hidden;"> 
<div class="row">
	<div class="col-md-12">
		<div class="zTreeDemoBackground left ztree_table_zt" >
                 <div id="leftToolBar" class="inquire-ul" style="margin-top:0;">
                    <div class="form-tr">
                        <div class="form-td" style="margin-right:0">
                            <div class="btn-div">
                            <@permission name="prms:advertRegion:edit">
                            <button id="treeAdd" onclick="treeAdd();" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</button>
                            </@permission>
                            <@permission name="prms:advertRegion:edit">
                            <button id="treeEdit" onclick="treeEdit();" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;编辑</button>
                            </@permission>
                            <@permission name="prms:advertRegion:delete">
                            <button id="treeDel" onclick="treeDel();" class="btn btn-primary btn-sm" ><i class="fa fa-remove"></i>&nbsp;&nbsp;删除</button> 
                            </@permission>
                            </div>
                    </div></div>
                </div>
            <ul id="left_menu_tree" class="ztree" style="margin-top:0;"></ul>
		</div>
		<div id="openClose" class="closeac" ></div>
		<div class="box-body ztree_table" >
		     <iframe id='myIframeT' 
            width='100%' height='100%' frameborder='0'></iframe> 
		</div> 
	</div>
</div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<link rel="stylesheet"	href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<script type="text/javascript">
	var zNodes = ${result};

</script>
<script src="${BasePath !}/asset/js/prms/advert/advert_index.js?v=${ver !}" /></script> 
<script src="${BasePath !}/asset/js/common/util.js?v=${ver !}"></script>

<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/> 
</body>
</html>