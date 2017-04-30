define(['ff/datatable'], function(){

	/*
	
	全局方法：创建 DataTable
	参数说明：obj 为 json 格式对象
  
	{
		div_id: 'demo', // 不需要加 # 并确保在当前页面里唯一！
		url: rootPath + "/help/test/queryData.do", // 无需添加系统根目录
		before_request: function(json){}, // 可选。向服务器端发出请求时，预处理要发送的数据，已包含 $('#find-page-orderby-form') 按钮触发提交的查询条件
		after_response: function(json){}, // 可选。从服务器端返回数据，在初始化 DataTable 之前
		columns: [
			{ "data": "name", 'class':'text-nowrap'},
			{ "data": "age" },
			{ "data": "email" },
			{ "data": "status", 
			  "render": function ( data, type, item ) {				
				 return (item.loginFlag == '1') ? '启用' : '禁用';
			  }
			},
			{ "data": "createBy.name" },
			{ "data": "createDate", 'class':'text-nowrap'},
			{ "data": "lastUpdateBy.name" },
			{ "data": "lastUpdateDate", 'class':'text-nowrap'}
		],
		show_index: true, //可选。隐藏序号列，默认显示
		show_checkbox: true, //可选。隐藏复选框列，默认显示
		show_action: true, //可选。隐藏操作列，默认显示
		clm_action: {
			info: {
				label: '查看',
				href: "javascript:iframeFullPage('"+ rootPath + "/help/test/form.do?id=" + id + "')"
			},
			edit: {
				label: '编辑',
				href: "javascript:iframeFullPage('"+ rootPath + "/help/test/form.do?id=" + id + "')"
			},
			delete: {
				label: '删除',
				href: rootPath + '/help/test/delete.do?id=' + id
			}
		},
		row_dblclick: function(row, data){} //双击<tr> 的事件
		
		//更多注释，请往 http://confluence.ffzxnet.com:8090/pages/viewpage.action?pageId=7700551
	}

	*/
	
	var initDataTable = function(obj){
		
		if (typeof obj.div_id == 'undefined') {			
			alert('亲，请定义一个 DataTable 的 ID');
			return false;
			
		} else {
			
			if ($('#' + obj.div_id).length > 1) {
				alert('亲，请确保 DataTable 的 ID 在当前页面中是“唯一”的！');
				return false;
			}
		}
		
		this.dataTableId = 'dt_' + obj.div_id;
		
		var that = this;		
		var dataTableId = this.dataTableId;
		var hasProp = function(key){
			return (typeof obj[key] != 'undefined');
		};
		var hasPropFunc = function(key){
			return (typeof obj[key] != 'undefined' && $.isFunction(obj[key]));
		};
		
		this._dataCurrPage = [];
		this._selectedIndex = [];
		this.indexLabel = (hasProp('index_label')) ? obj.index_label : '序号';
		//this.hasClmIndex = false;
		this.hasClmCheckbox = false;
		this.hasClmAction = false;
		this.rawResponse = {};
		
		
		// 方法：生成操作列内链接
		var renderAction = function(item, permission) {
			
			this.item = item;
			this.permission = permission || [];
			this._data = {};	
		}

		renderAction.prototype = {

			getItem: function(){
				return this.item;
			},
			
			setData: function(data){
				this._data = data;
			},
			
			genHtml: function(){
				var arrAction = [];
				var _data = this._data;
				
				if ($.isArray(this.permission) && this.permission.length) {
				
					$.each(this.permission, function(idx, ele){	
						var dataItem = _data[ele] || [];
						
						if (dataItem.length > 0) {
							
							$.each(dataItem, function(key, val){
								var strParam = '';
								
								if (!('class' in val)) {
									val['class'] = '';
								}
								
								if (!('href' in val)) {
									val['href'] = 'javascript:void(0);';
								}
								
								$.each(val, function(k, v){
									
									if (k == 'class') {
										strParam += ' class="' + v + ' action_' + ele + '"';
										
									} else if (k != 'label') {
										strParam += ' ' + k + '="' + v + '"';
									}
								});
								arrAction.push('<a' + strParam + '>'+ val.label +'</a>');
							});
						}				
					});			
				}
				return arrAction.join('&nbsp;|&nbsp;');
			}
		}		
		
		// 方法：遍历列设置，只循环一次！
		var loopColumn = function(type){
			var _obj = {};
			
			_obj['data_dict'] = {};
			_obj['format'] = {};
			_obj['thead'] = [];
			_obj['tfoot'] = [];
			
			$.each(obj.columns, function(idx, ele){
				
				if ('data_dict' in ele) {
					_obj['data_dict'][ele.data] = ele.data_dict;
				}
				
				if ('format' in ele) {
					_obj['format'][ele.data] = ele.format;
				}

				if (ele.data == 'clm_checkbox') {
					that.hasClmCheckbox = true;
				}

				if (ele.data == 'clm_action') {
					that.hasClmAction = true;
				}
				
				_obj['thead'].push('<th class="clm_'+ ele.data +'">'+ ele.label +'</th>');
				_obj['tfoot'].push('<th class="clm_'+ ele.data +'"></th>');
			});
			return _obj[type];
		};
		var map_format = loopColumn('format'); 
		var map_dict = loopColumn('data_dict');	
		var map_permission = (hasPropFunc('gen_permission')) ? obj.gen_permission() : [];
		
		// 表格基本代码
		var strTable = '<table id="'+ dataTableId +'" class="table table-hover table-striped table-bordered">'
			+ 	'<thead>'
			+		'<tr>'					
			+			'<th class="clm_index">'+ this.indexLabel +'</th>'
			+			((!this.hasClmCheckbox) ? '<th class="clm_checkbox"><input type="checkbox" class="selectAll" /></th>' : '')
			+			loopColumn('thead').join('')
			+			((!this.hasClmAction) ? '<th class="clm_action">操作</th>' : '')
			+		'</tr>'
			+ 	'</thead>'
			+	'<tfoot>'
			+		'<tr>'
			+			'<th class="clm_index"></th>'
			+			((!this.hasClmCheckbox) ? '<th class="clm_checkbox"></th>' : '')
			+			loopColumn('tfoot').join('')
			+			((!this.hasClmAction) ? '<th class="clm_action"></th>' : '')
			+		'</tr>'
			+	'</tfoot>'
			+ '</table>';
		$('#'+ obj.div_id).append(strTable);	
		
		// 默认配置
		var _obj = {
			before_request: function(json){return json;},
			after_response: function(json){return json;},
			ordering: false,
			fixedHeader: true,
			autoWidth: false,
			dataSrcName: 'infoData',
			columns: [
				{
					data: "clm_index",
					//visible: ('show_index' in obj) ? obj.show_index : true,
					class: 'text-nowrap clm_index'+ ((('show_index' in obj) && obj.show_index == false) ? ' hide' : '')
				}
				
			],			
			column_action: [
				{	
					data: "clm_action",
					visible: ('show_action' in obj) ? obj.show_action : true,
					class: 'text-nowrap clm_action',
					render: function ( data, type, item ) {
						var id = item.id;
						var renderIt = new renderAction(item, map_permission);
						
						renderIt.setData(_obj.clm_action(item));
						return renderIt.genHtml();
					}
				}
			],
			clm_action: function(item){ return {}; },
			row_dblclick: function(row, data){}
		};
		
		if (!this.hasClmCheckbox) {
			_obj.columns.push({ 
				data: "clm_checkbox", 
				//visible: ('show_checkbox' in obj) ? obj.show_checkbox : true,
				class: 'clm_checkbox'+ ((('show_checkbox' in obj) && obj.show_checkbox == false) ? ' hide' : ''), 
				render: function ( data, type, item ) {	
					var id = ('id' in item ) ? item.id : '';
					return '<input type="checkbox" name="id" value="'+ id +'" />';
				}
			});
		}
		
		//合并“默认”和“自定义” obj 对象		
		_obj.columns = [].concat(_obj.columns, obj.columns, ((!this.hasClmAction) ? _obj.column_action : []));		
		obj.columns = [];
		$.extend(true, _obj, obj);
		

		// 绑定日期的 input.datepicker
		$('#' + _obj.div_id).on('mouseenter', '.datepicker', function(){
			var $this = $(this);
			
			requirejs(['jq/datepicker'], function(){
				
				if ($this.hasClass('hasDatepicker') == false){
					$this.datepicker();
				}				
			});
		})
		// 绑定 select 的 change 事件
		.on('change', '.dataTables_scrollBody select', function(){
			
			if (hasPropFunc('select_change')) {
				_obj.select_change(this);
			}
		});	

		// TODO: 过长字串截断，加省略号
		/*
		$.fn.dataTable.render.dotdotdot = function ( _length ) {
			return function ( data, type, row ) {
				if ( type === 'display' ) {
					var str = data.toString(); // cast numbers
		 
					return str.length < _length ?
						str :
						str.substr(0, _length-1) +'&#8230;';
				}
		 
				// Search, order and type can use the original data
				return data;
			};
		};
		*/

		//初始化表格
		FFZX.DT[dataTableId] = $('#' + dataTableId).DataTable($.extend(true, {}, OPT_DATATABLE, {
			"deferRender": true,
			"ajax": {
				url: _obj.url,
				type: 'POST',
				
				// JSON: 查询或翻页时，在 request 中添加其它参数，比如：查询表单中的值
				data: function(json){

					var _json = {};
					var _pageSize = json.length;
					var param = $('#find-page-orderby-form').form2Json() || {};
					
					param.pageSize = _pageSize;
					param.pageIndex = json.start/_pageSize + 1;
					_json = $.extend(true, {}, json, param);
					_json = _obj.before_request(_json) || json;
					
					return _json;
				},
				
				// 在构建 <table> 前，预处理 response 返回的 JSON 数据
				dataSrc: function(json){
					
					that.rawResponse = $.extend(true, {}, json);
					
					if ('status' in json && 'infoStr' in json) {
						
						// 处理错误/异常
						if('error' == json.status || 'exception' == json.status){

							$.frontEngineDialog.executeDialogContentTime(json.infoStr, 4000);
							return false;
						}
					}
					
					json.recordsFiltered = json.recordsTotal;

					// 从多维 JSON 数据中提取数据源
					var _tmpDataSrcNames = _obj.dataSrcName.split('.');					
					var _dataSrc = null;
					
					for (var ij=0; ij<_tmpDataSrcNames.length; ij++) {
						if (_dataSrc == null){
							_dataSrc = json[_tmpDataSrcNames[ij]];
						} else {
							_dataSrc = _dataSrc[_tmpDataSrcNames[ij]];
						}
						json.data = _dataSrc;					
					};

					var _json = $.extend(true, {}, json);
					
					if (_json.data != null && _json.data != undefined && _json.data != '') {
					
						$.each(_json.data, function(key, val){
							
							// 保留一份原始数据，便于“操作”列依赖使用
							val['raw'] = $.extend(true, {}, val);
							
							val['clm_index'] = key + 1;
							val['clm_checkbox'] = '';
							val['clm_action'] = '';
							
							$.each(val, function(k, v){

								if (k in map_dict) {
									
									if (v in map_dict[k]) {
										val[k] = map_dict[k][v];
									}
								}
								
								if (k in map_format) {
									
									if ('datetime' in map_format[k]) {
										
										if (val[k]) {
											val[k] = (new Date(val[k])).format(map_format[k]['datetime']);
											
										} else {
											val[k] = '';
										}
									}
								}
							});
						});
					} else {
						return false;
					}
					_json = _obj.after_response(_json) || json;
					
					//缓存当前页数据
					that._dataCurrPage = _json.data;
					
					//清空选中复选框缓存的数据
					that._selectedIndex = [];
					$('#' + _obj.div_id).find('.selectAll').prop('checked', false);
					
					return _json.data;
				},
				error: function(e){
					
					// 如果超时退出
					if (e.readyState == 0 && e.responseText == '' && e.status == 0 && e.statusText == 'error') {
						$.frontEngineDialog.executeDialogContentTime('请重新登录！', 5000);
					}
				}
			},
			footerCallback: function ( row, data, start, end, display ) {
				
				var api = this.api();
				
				// 确保为数字类型。否，则转为 0
				var intVal = function ( i ) {
					return typeof i === 'string' ?
						i.replace(/[\$,]/g, '')*1 :
						typeof i === 'number' ?
							i : 0;
				};

				var action = {
					
					// 合计当前页的某（些）列：clmIndex 为数组
					sumup: function(clmIndex){
						var ttl = function(idx){

							return api
							.column( idx, { page: 'current'} )
							.data()
							.reduce( function (a, b) {
								return intVal(a) + intVal(b);
							}, 0 );
						};
						
						if ($.isArray(clmIndex)) {

							if (clmIndex.length) {

								$.each(clmIndex, function(idx, ele){
									$(api.column(ele).footer()).html(ttl(ele));
								});
							}
						}
						$(row).closest('tfoot').show();
					}				
				};				

				if (hasProp('initFooter')) {
					
					$.each(action, function(key, func){
						
						if (key in _obj.initFooter) {
							func(_obj.initFooter[key]['data']);
						}
					});
				}
			}
		}, _obj));
		
		// 双击 <tr> 事件
		$(document).on('dblclick', '#'+ dataTableId + ' tr', function(){
			var data = FFZX.DT[dataTableId].row(this).data();
			_obj.row_dblclick(this, data);
		})
		// 复选框事件
		.on('click', '#'+ dataTableId + '_wrapper .clm_checkbox :checkbox', function(){
			var $allCheckbox = $('#'+ dataTableId + ' tbody .clm_checkbox :checkbox');
			that._selectedIndex = [];
			
			$.each($allCheckbox, function(idx, ele){
				
				if ($(this).is(':checked')) {
					
					that._selectedIndex.push(idx);
				}
			});
			//console.log(that._selectedIndex)
		});
		
		// 点击“查询”按钮，重载当前页数据
		$('#find-page-orderby-button').click(function(){
			FFZX.DT[dataTableId].ajax.reload();
		});
		
		// 当数据表格渲染结束后触发
		$('#' + this.dataTableId).on('draw.dt',function() {
			var $this = $(this);
			
			// 清空“跳到...”输入框中数值
			$this.closest('.dataTables_wrapper').find('.redirect').val('');
		});
	};
	
	initDataTable.prototype = {
	
		// 获取选中行的数据，raw 下的为原始数据，rendered 下的为渲染后的
		getSelectedData: function(){
			var ret = {
				raw: [],
				rendered: []
			};
			var selectedIndex = this._selectedIndex;
			
			$.each(this._dataCurrPage, function(idx, ele){
				var raw = $.extend(true, {}, ele.raw);
				var rendered = $.extend(true, {}, ele);
				delete rendered.raw;
				
				if ($.inArray(idx, selectedIndex) > -1) {
					ret.raw.push(raw);
					ret.rendered.push(rendered);
				}
			});
			
			return ret;
		}
	}

	return initDataTable;
});