<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<body style="overflow: hidden;"> 
<div class="row">
    <div class="col-md-12">
        <div class="zTreeDemoBackground left ztree_table_zt" >
         <div id="leftToolBar" class="inquire-ul" style="margin-top:0;">
                    <div class="form-tr">
                        <div class="form-td" style="width:70%;margin-right:5px">
                            <div class="div-form" style="width:100%"><input class="form-control input-sm txt_mid" style="width:100%"  type="text" id="fuzzyQueryName" name="fuzzyQueryName" value=""></div>
                        </div>
                        <div class="form-td" style="margin-right:0">
                            <div class="btn-div"><button id="treeSearch" class="btn btn-primary btn-sm" ><i class="fa fa-search"></i></button></div>
                        </div>
                    </div>
                </div>
            <ul id="left_menu_tree" class="ztree" style="margin-top:0;"></ul>
        </div>
        <div id="openClose" class="closeac" ></div>
        <div class="box-body ztree_table" >
            <div class="row" style="float: left; margin-left: 0px;width: 100%;">
                
                <div class="search">
                        <form id="find-page-orderby-form" action="${BasePath !}/order/addressListSelect.do" method="post">
                            <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
                            <input id="find-page-index" type="hidden" name="searchType" value="1" />
                            <input id="find-page-index" type="hidden" name="officeId" value="${(officeId) !}" />
                            <input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
                            <div class="inquire-ul" style="margin-top:0;">
                                <div class="form-tr">
                                    <div class="form-td">
                                        <label>编码：</label>
                                        <div class="div-form"><input class="form-control input-sm txt_mid" type="text" id="addressCode" name="addressCode" value="${(params.addressCode) !}"> </div>
                                    </div>
                                    <div class="form-td">
                                        <label>名称：</label>
                                        <div class="div-form"><input id="loginName" class="form-control txt_mid input-sm" type="text" placeholder="" name="name" value="${(params.name) !}"></div>
                                    </div>
                                    <div class="form-td">
                                        <button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit">
                                        <i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
                                    </div>
                                </div>                              
                            </div>
                        </form>
                    </div>
            
                <div class="box-header" style="float: left;padding-left: 0px;width: 99%; ">
                        <table class="table table-hover table-striped bor2 table-common" id="TreeTable">
                            <thead>
                            <tr>
                                <th width="10%">编码</th>
                                <th width="10%">名称</th>
                                <th width="20%">上级地址</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if treeTable?? >
                            <#list treeTable as item >
                                <tr  id="${(item.id) !} " preAddress="${(item.preAddress) !} "   name ="${(item.name) !}"  type="${(item.type) !}" ondblclick="selectOkObj(this);" >
                                    <td>${(item.addressCode) !}</td>
                                    <td>${(item.name) !}</td>
                                    <td>${(item.preAddress) !}</td>
                                </tr>
                            </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>  
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<script type="text/javascript">
    var zNodes = ${result};
    function selectOkObj(obj){
    	window.parent.getSelectAddr(obj);
    }
    
    var tree_setting = {
    		data : {
    			simpleData : {
    				enable : true
    			},
    			key : {
    				url : ""
    			}
    		},
    		view : {
    			selectedMulti : false
    		},
    		callback : {
    			beforeClick : beforeClick
    		}
    	};
    	$(function() {
    		$.fn.zTree.init($("#left_menu_tree"), tree_setting, zNodes);
    		var option = {
    			theme : 'vsStyle',
    			expandLevel : 6
    		};
    		$('#TreeTable').treeTable(option);
    	});
    	/**
    	 * openClose 显示和隐藏树形栏
    	 */
    	$("#openClose").click(function () {
    	    if ($(this).hasClass("closeac")) {
    	        $(this).removeClass("closeac");
    	        $(this).addClass("open");
    	        
    	        $(".zTreeDemoBackground .ztree").addClass("disalyact");
    	        $(".box-body").addClass("disalyact");
    	        $(".zTreeDemoBackground").addClass("disalyact");
    	       /* $(".box-body").css("width","98%");
    	        $(".zTreeDemoBackground").css("width","0%");*/
    	    } else {
    	        $(this).addClass("closeac");
    	        $(this).removeClass("open");
    	        
    	        
    	        $(".zTreeDemoBackground .ztree").removeClass("disalyact");
    	        $(".box-body").removeClass("disalyact");
    	        $(".zTreeDemoBackground").removeClass("disalyact");
    	       /* $(".box-body").css("width","84%");
    	        $(".zTreeDemoBackground").css("width","15%");*/
    	    }
    	});
    	//左侧树形单击
    	function beforeClick(treeId, treeNode) {
    		window.location.href = rootPath+"/order/addressListSelect.do?id="+treeNode.id;
    		return true;
    	}

    	/**
    	 * 点击查询事件
    	 */
    	$("#treeSearch").click(function(){
    		var name = $('#fuzzyQueryName').val();
    		treeLocate('left_menu_tree','name',name);
    	})
    
</script>
<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/>
</body>
</html>
