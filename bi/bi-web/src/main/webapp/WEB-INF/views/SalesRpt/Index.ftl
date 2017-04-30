<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="list" />

<title>报表管理</title>

<style>
.getnumbercount{width: 20% !important;text-align: -webkit-center;text-align: center;border: 1px solid #ccc;padding-top: 5px;}
</style>
<script type="text/javascript" src="../asset/js/ff/ff.res.js?v=${ver !}"/></script>

 
</head>
<body>

	<div class="tab-content">
	
		<div id="ff_page_config"    data-options="action:'${action}'" style="display:none" >
		   <div id="ff_rpt_indicator" data-options="indicator:['sale_num','sale_amount','order_finish_num','refund_amount','order_cancel_num','num_mom_ratio','num_yoy_ratio','amount_mom_ratio','amount_yoy_ratio']" ></div>
		</div>
		
		<div class="tab-pane fade in active" id="brandList">
			<div class="col-md-12">
				<div class="search">
					<form id="ff_filter"   method="post">
	 
 						<div class="inquire-ul">
						<div class="form-tr"><br/>
								<div id="ff_filter_time" ff-class="ff.form.time" data-options="field_name:'create_date',option:[1,7,30,182]"></div>
                                <div class="form-td">
                                    <label>条形码：</label> 
                                    <div class="div-form"><input id="commodity_barcode" filter="eq" name="commodity_barcode"  class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.code) !}"></div>
                                </div>
 
                                 <div class="form-td">
                                    <label>商品类目：</label>
                                       <div class="div-form">
                                        <div class="f7" onclick="ff.service.category({data:[{id:'category_id',field:'id'},{id:'category_name',field:'name'}]})">
                                        <input type = 'hidden' id="category_id"  filter="like" name="category_parent_ids"   class="form-control txt_mid input-sm" type="text" />
                                        
                                        <input id="category_name"  name="category_name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.code) !}" />
                                        	<span class="selectDel" onclick="ff.select.clear(this,event);" >×</span>
                                        	<span class="selectBtn">选</span>
                                       	</div>
                                       </div>
                                </div>
 
                                <div class="form-td">
                                    <label>供应商：</label> 
                                    <div class="div-form">
                                    <div class="f7" onclick="ff.service.vendor({data:[{id:'vendor_id',field:'vendor_id'},{id:'vendor_name',field:'vendor_name'}]})">  
                                     <input type = 'hidden' id="vendor_id" filter="eq" name="vendor_id" class="form-control txt_mid input-sm" type="text" placeholder="" />
                                     <input id="vendor_name"  name="vendor_name" class="form-control txt_mid input-sm" type="text" placeholder="" />
                                       <span class="selectDel" onclick="ff.select.clear(this,event);" >×</span>
                                       <span class="selectBtn">选</span>
                                      </div>
                                    </div>
                                </div>
   
                                <div class="form-td">
					                    <label>地址：</label>
					                    <div class="div-form">
						                    <div class="f7" onclick="ff.service.address({data:[{id:'address_id',field:'address_id'},{id:'address_name',field:'address_name'}]})">   
						                          <input type="hidden" id="address_id" filter="like" name="address_parent_ids"  />
						                          <input id="address_name"  name="address_name" class="form-control txt_mid input-sm" type="text" placeholder="" />
						                          <span class="selectDel" onclick="ff.select.clear(this,event);" >×</span>
 						                           <span class="selectBtn">选</span>
						                    </div>
					                     </div>
	                            </div>
							</div>
						</div>
						<div class="btn-div3">
                            <a onclick="load()" class="btn btn-primary btn-sm" ><i class="fa fa-arrow-circle-up"></i>&nbsp;&nbsp;查询</a>
                            <a onclick="location.href=location.href;"  class="btn btn-primary btn-sm"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
							<a onclick="ff.rpt.excel()" class="btn btn-primary btn-sm" ><i class="fa fa-arrow-circle-up"></i>&nbsp;&nbsp;导出</a>
 						</div>
					</form>
				</div>
			</div> 
 
 			
 			 <div id="ff_rpt">
            
				<div  class="col-md-12"  style="margin-bottom: 10px;">
					<form ff-class="ff.rpt.sum" id="ff_rpt_sum"></form>
				</div>
				
				<div  class="col-md-12" style="margin-bottom: 10px;">
				    <div id = "dim" ></div>
				</div>
				
				<div class="col-md-12">
				 <div id="num_mom_ratio" style="float: left;width: 25%;height:400px;"></div>
				 <div id="num_yoy_ratio" style="float: left;width: 25%;height:400px;"></div>
				 <div id="amount_mom_ratio" style="float: left;width: 25%;height:400px;"></div>
				 <div id="amount_yoy_ratio" style="float: left;width: 25%;height:400px;"></div>
	 			</div>
				
	 
	 			<div  class="col-md-12">
				   <div id="ff_rpt_chart" ff-class="ff.rpt.chart" style="height:400px;"> </div>
	  			</div>
				<div class="tab-content">
	 				<table id="ff_rpt_table"  ff-class="ff.rpt.grid"  class="table table-striped table-bordered"></table>
	 			</div>
 			</div>
 		</div>
	</div>
 
<script type="text/javascript">

$(document).ready(function()
 { 
      ff.form.time("ff_filter_time");
      ff.rpt.loadDim("#dim");
      ff.rpt.loadIndicator("#ff_rpt_indicator",
      {
         callback:load
      });
 	 
});
 
function load(obj)
{
   
   FFZX.mask('show');
   ff.rpt.loadData(ff.rpt.getConfig(),loadSuccess);
   
   var data = {};
   data.indicator = ff.rpt.getIndicator(indi_sale);
   ff.rpt.sum("#ff_rpt_sum",data);

};

var indi_ratio = [ "num_mom_ratio","num_yoy_ratio","amount_mom_ratio","amount_yoy_ratio"];
var indi_sale = ["sale_num","sale_amount","order_finish_num","refund_amount","order_cancel_num"];

function loadSuccess(rspObj)
{
        FFZX.mask('hide');
		rspObj = rspObj || {chart_data:[],table_data:[]};
  
        var data = {};
        data.dim = ff.rpt.getDim();
        data.indicator = ff.rpt.getIndicator(indi_sale);
        data.title = "销售数据";
        data.data = rspObj.table_data[data.dim.dim];
        ff.rpt.grid("#ff_rpt_table",data);
        
        data.data = rspObj.chart_data;
        ff.rpt.chart("#ff_rpt_chart",data);
        
        var ratio_indicator = ff.rpt.getIndicator(indi_ratio);
        for(var i=0;i<ratio_indicator.length;i++)
        {
              var temp = ff.obj.find(rspObj.chart_data,'indicator',ratio_indicator[i].indicator)
              temp = temp || {};
              ff.chart.load("#"+ratio_indicator[i].indicator,temp.y_name,temp);
        }
    
};


 
 

 
 
 
	
</script>
</body>
</html>
