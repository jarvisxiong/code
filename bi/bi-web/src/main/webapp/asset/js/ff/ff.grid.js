var ff = ff || {};
ff.grid = {};

ff.grid.load= function(element,data)
{   
	try
	{
		 var obj = ff.com.getJqObj(element);
		    if(null != data.url)
		    {
		          obj.DataTable($.extend({}, OPT_DATATABLE, {
						    paging: data.paging,
						    ajax:data.ajax,
					   }));	
		    }
		    else
		    {
		         var fields = [];
		          obj.children("thead").each(function(){
		               $(this).find("tr").each(function(){
		                  $(this).find("th").each(function(){
		                    var th = $(this);
		                    var dataOptions=ff.com.getOptions(th);
		                    fields.push(dataOptions.field);
		                  });
		               });
		          });
		          
		           var temp =[];
		           
		          for(var i =0; i<data.data.length;i++)
		          {
		               var cols = [];
		               for(var j=0; j<fields.length;j++)
		               {
		                    cols[j]= data.data[i][fields[j]];
		               }
		               temp[i] = cols;
		          }
		          data.data = temp;
		           requirejs(['ff/datatable'], function(){
		           
		           var search = true;
			           if ( $.fn.dataTable.isDataTable( '#'+obj.attr('id') ) ) 
			           {
			              obj.DataTable().destroy();	
 			               
			           }
			          obj.DataTable( data);
				        
										
		           });
		    }

	}
	catch(e)
	{
		ff.log.warn("load grid failed",e);
	}
   
};

