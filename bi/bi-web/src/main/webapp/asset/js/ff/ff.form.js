var ff = ff || {};
ff.form = {};

ff.form.load = function(element,data,callback)
{
 
   var obj = ff.com.getJqObj(element);
    var inputs = obj.find("input");
    inputs.each(function () {
 
            var name = $(this).attr("name");
	        if (null != name) {
               if(null == data)
               {
                 $(this).val("");  
               }
               else
               {
                  $(this).val(data[name]);
               }
	           
	        }
 
	    });
   if(null != callback)
   {
      callback(obj,data);
   }
};

ff.form.reset = function(element,callback)
{ 
    	var obj = ff.com.getJqObj(element);
    	obj[0].reset();
    	if(null != callback)
    	{
    	     callback(obj);
    	}
};

ff.form.time = function(element,data)
{
	
	var obj = ff.com.getJqObj(element);

	data = data || ff.com.getOptions(obj);
	data.field_name = data.field_name || "create_date";
	data.option = data.option || [];
	
 
	var html = "";
	
	if(data.option.length>0)
	{
		html = html + "<div class=\"form-td chooseDate\">";
		html = html + "<div class=\"btn-group\" data-toggle=\"buttons\">";
		
		var defaultOp = {0:'今天',1:'昨天',7:'近一周',30:'近30天',182:'近半年'};
		
		 
		data.check = data.check || data.option[0];
		
		for(var i=0;i<data.option.length;i++)
		{
			
			if(data.check == data.option[i])
			{
				html = html + "<label class=\"btn btn-default btn-sm active\">";
				html = html + "<input type=\"radio\" name=\"options\" data-options='" + data.option[i] + "' checked >" + defaultOp[data.option[i]]   ;
			}
			else
			{
				html = html + "<label class=\"btn btn-default btn-sm \">";
				html = html + "<input type=\"radio\" name=\"options\" data-options='" + data.option[i] + "' >" + defaultOp[data.option[i]]   ;

			}
			html = html + "</label>";

		}	
		html = html + "</div>";
		html = html + "</div>";
	}	

	
	html = html + "<div class=\"form-td\">";
 

	html = html + "<div class=\"div-form\">";
	html = html + "<input  filter=\"gt_eq\" name=\""+data.field_name+"\" id=\"ff_form_time_min_date\" class=\"form-control txt_mid input-sm\"  readonly=\"readonly\" onfocus=\"WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd'});clearDate();\"/>";
	html = html + "</div> - ";
	
	html = html + "<div class=\"div-form\">";
	html = html + "<input  filter=\"lt_eq\" name=\""+data.field_name+"\" id=\"ff_form_time_max_date\" class=\"form-control txt_mid input-sm\"  readonly=\"readonly\" onfocus=\"WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd'});clearDate();\"/>";
	html = html + "</div>";
	
	html = html + "</div>";
	
	obj.html(html);
	
	changeData($('.chooseDate label.active input[type="radio"]')[0]);
	   
	$('.chooseDate label').unbind('click').on('click', function(e) {
				
				var $chooseDate = $(this).find('input[type="radio"]');
	 
				changeData($chooseDate[0]);
	});
}

 
 
function clearDate()
{  
    var obj = $('.chooseDate label.active');
    if(null != obj)
    {
      obj.attr('class','btn btn-default btn-sm');
    }
};
function changeData(obj){
 	 var nowDate = new Date();
 	 var val = $(obj).attr('data-options');
 	 var prvDate =  new Date(nowDate.valueOf() -  val*24*60*60*1000);
 	 $('#ff_form_time_min_date').val(new Date(prvDate).format('yyyy-MM-dd')); 
	 $('#ff_form_time_max_date').val(new Date(nowDate.valueOf()-24*60*60*1000).format('yyyy-MM-dd'));
}
