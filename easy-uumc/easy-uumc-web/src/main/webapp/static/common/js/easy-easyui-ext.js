(function($){
	/**
	 * 全局系统对象
	 */
    window['easyuiExt'] = {};
    
    //默认每页记录数
	commonui.pageSize = 20;
	commonui.error_msg = "系统错误，请与管理员联系！";
	//消息框超时时间
	commonui.msg_title = "系统提示";
	commonui.msg_timeout = 1000 * 4;
	//页脚的高度
	commonui.remainHeight = 11;
	
	/**
	 * 切换皮肤
	 */
	commonui.chgSkin = function(selectId, cookiesColor) {
		docchgskin(document,selectId,cookiesColor);
        $("iframe").each(function () {
            var dc = this.contentWindow.document;
            docchgskin(dc,selectId,cookiesColor);
        });
        function docchgskin(dc,selectId,cookiesColor) {
        	removejscssfile(dc,"static/easyui/themes/"+cookiesColor+"/easyui.css", "css");
        	createLink(dc,"static/easyui/themes/"+selectId+"/easyui.css");
    	}
        function createLink(dc,url) {
        	var urls = url.replace(/[,]\s*$/ig,"").split(",");
	    	var links = [];
	    	for( var i = 0; i < urls.length; i++ ) {
			    links[i] = dc.createElement("link");
			    links[i].rel = "stylesheet";
			    links[i].href = urls[i];
			    dc.getElementsByTagName("head")[0].appendChild(links[i]);
	     	}
    	}
        function removejscssfile(dc,filename, filetype) {
        	var targetelement=(filetype=="js")? "script" : (filetype=="css")? "link" : "none"
            var targetattr=(filetype=="js")? "src" : (filetype=="css")? "href" : "none"
            var allsuspects=dc.getElementsByTagName(targetelement)
            for (var i=allsuspects.length; i>=0; i--) {
            	if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)!=null && allsuspects[i].getAttribute(targetattr).indexOf(filename)!=-1)
	                allsuspects[i].parentNode.removeChild(allsuspects[i])
            }
    	}
	};
	
	/**
	 * 高级查询
	 */
	commonui.gradeSearch = function($dg,formId,url) {
		$("<div/>").dialog({
			href:url,
			modal:true,
			title:'高级查询',
			top:120,
			width:480,
			buttons:[{
				text:'增加一行',
				iconCls:'icon-add',
				handler:function() {
					var currObj = $(this).closest('.panel').find('table');
					currObj.find('tr:last').clone().appendTo(currObj);
					currObj.find('tr:last a').show();
				}
			},{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					$dg.datagrid('reload',utils.serializeObject($(formId)));
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$(this).closest('.window-body').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	};
	
	/*
	 * 定义图标样式的数组
	 */
	commonui.iconData = [{
		value : '',
		text : '默认',
		selected : true
	},{
		value : 'icon-adds',
		text : 'icon-adds'
	},{
		value : 'icon-ok',
		text : 'icon-ok'
	},{
		value : 'icon-tip',
		text : 'icon-tip'
	},{
		value : 'icon-remove',
		text : 'icon-remove'
	},{
		value : 'icon-undo',
		text : 'icon-undo'
	},{
		value : 'icon-cancel',
		text : 'icon-cancel'
	},{
		value : 'icon-save',
		text : 'icon-save'
	},{
		value : 'icon-config',
		text : 'icon-config'
	},{
		value : 'icon-comp',
		text : 'icon-comp'
	},{
		value : 'icon-sys',
		text : 'icon-sys'
	},{
		value : 'icon-db',
		text : 'icon-db'
	},{
		value : 'icon-pro',
		text : 'icon-pro'
	},{
		value : 'icon-role',
		text : 'icon-role'
	},{
		value : 'icon-bug',
		text : 'icon-bug'
	},{
		value : 'icon-time',
		text : 'icon-time'
	},{
		value : 'icon-easy icon-easy-sys',
		text : 'icon-easy-sys'
	},{
		value : 'icon-easy icon-easy-set',
		text : 'icon-easy-set'
	},{
		value : 'icon-easy icon-easy-add',
		text : 'icon-easy-add'
	},{
		value : 'icon-easy icon-easy-nav',
		text : 'icon-easy-nav'
	},{
		value : 'icon-easy icon-easy-users',
		text : 'icon-easy-users'
	},{
		value : 'icon-easy icon-easy-role',
		text : 'icon-easy-role'
	},{
		value : 'icon-easy icon-easy-set',
		text : 'icon-easy-set'
	},{
		value : 'icon-easy icon-easy-log',
		text : 'icon-easy-log'
	},{
		value : 'icon-easy icon-easy-delete',
		text : 'icon-easy-delete'
	},{
		value : 'icon-easy icon-easy-edit',
		text : 'icon-easy-edit'
	},{
		value : 'icon-easy icon-easy-magic',
		text : 'icon-easy-magic'
	},{
		value : 'icon-easy icon-easy-database',
		text : 'icon-easy-database'
	}];
	
	/***************** easyui默认实现的覆盖 BEGIN *****************/
	
	var easyuiErrorFunction = function(XMLHttpRequest) {
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText);
	};
	$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
	$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
	$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
	$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
	$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
	$.fn.form.defaults.onLoadError = easyuiErrorFunction;
	
	/**
	 * 取消easyui默认开启的parser
	 * 在页面加载之前，先开启一个进度条
	 * 然后在页面所有easyui组件渲染完毕后，关闭进度条
	 */
	$.parser.auto = false;
	$(function() {
		$.messager.progress({
			text : '加载中....',
			interval : 100
		});
		$.parser.parse(window.document);
		window.setTimeout(function() {
			$.messager.progress('close');
			if (self != parent) {
				window.setTimeout(function() {
					try {
						parent.$.messager.progress('close');
					} catch (e) {
					}
				}, 500);
			}
		}, 1);
		$.parser.auto = true;
	});
	
	/**
	 * 使panel和datagrid在加载时提示
	 */
	$.fn.panel.defaults.loadingMessage = '加载中....';
	$.fn.datagrid.defaults.loadMsg = '加载中....';
	
	/**
	 * 防止panel/window/dialog组件超出浏览器边界
	 */
	var easyuiPanelOnMove = function(left, top) {
		var l = left;
		var t = top;
		if (l < 1) {
			l = 1;
		}
		if (t < 1) {
			t = 1;
		}
		var width = parseInt($(this).parent().css('width')) + 14;
		var height = parseInt($(this).parent().css('height')) + 14;
		var right = l + width;
		var buttom = t + height;
		var browserWidth = $(window).width();
		var browserHeight = $(window).height();
		if (right > browserWidth) {
			l = browserWidth - width;
		}
		if (buttom > browserHeight) {
			t = browserHeight - height;
		}
		$(this).parent().css({/* 修正面板位置 */
			left : l,
			top : t
		});
	};
	$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
	$.fn.window.defaults.onMove = easyuiPanelOnMove;
	$.fn.panel.defaults.onMove = easyuiPanelOnMove;
		
	/**
	 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
	 */
	var createGridHeaderContextMenu = function(e, field) {
		console.info(">>common.ui>>>createGridHeaderContextMenu");
		e.preventDefault();
		var grid = $(this);/* grid本身 */
		var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
		if (!headerContextMenu) {
			var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
			var fields = grid.datagrid('getColumnFields');
			for ( var i = 0; i < fields.length; i++) {
				var fildOption = grid.datagrid('getColumnOption', fields[i]);
				if (!fildOption.hidden) {
					$('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
				} else {
					$('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
				}
			}
			headerContextMenu = this.headerContextMenu = tmenu.menu({
				onClick : function(item) {
					var field = $(item.target).attr('field');
					if (item.iconCls == 'icon-ok') {
						grid.datagrid('hideColumn', field);
						$(this).menu('setIcon', {
							target : item.target,
							iconCls : 'icon-empty'
						});
					} else {
						grid.datagrid('showColumn', field);
						$(this).menu('setIcon', {
							target : item.target,
							iconCls : 'icon-ok'
						});
					}
				}
			});
		}
		headerContextMenu.menu('show', {
			left : e.pageX,
			top : e.pageY
		});
	};
	$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
	$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
	
	/**
	 * panel关闭时回收内存
	 */
	$.fn.panel.defaults.onBeforeDestroy = function() {
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				frame[0].contentWindow.document.write('');
				frame[0].contentWindow.close();
				frame.remove();
				if ($.browser.msie) {
					CollectGarbage();
				}
			}
		} catch (e) {
		}
	};
	
	/**
	 * 扩展treegrid，使其支持平滑数据格式
	 */
	$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
		console.info(">>common.ui>>>$.fn.treegrid.defaults.loadFilter");
		var opt = $(this).data().treegrid.options;
		var idFiled, textFiled, parentField;
		if (opt.parentField) {
			idFiled = opt.idFiled || 'id';
			textFiled = opt.textFiled || 'text';
			parentField = opt.parentField;
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idFiled]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][textFiled];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][textFiled];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	};
		
	/**
	 * 扩展tree，使其支持平滑数据格式
	 */
//	$.fn.tree.defaults.loadFilter = function(data, parent) {
//		console.info(">>common.ui>>>$.fn.tree.defaults.loadFilter");
//		var opt = $(this).data().tree.options;
//		var idFiled, textFiled, parentField;
//		if (opt.parentField) {
//			idFiled = opt.idFiled || 'id';
//			textFiled = opt.textFiled || 'text';
//			parentField = opt.parentField;
//			var i, l, treeData = [], tmpMap = [];
//			for (i = 0, l = data.length; i < l; i++) {
//				tmpMap[data[i][idFiled]] = data[i];
//			}
//			for (i = 0, l = data.length; i < l; i++) {
//				if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
//					if (!tmpMap[data[i][parentField]]['children'])
//						tmpMap[data[i][parentField]]['children'] = [];
//					data[i]['text'] = data[i][textFiled];
//					tmpMap[data[i][parentField]]['children'].push(data[i]);
//				} else {
//					data[i]['text'] = data[i][textFiled];
//					treeData.push(data[i]);
//				}
//			}
//			return treeData;
//		}
//		return data;
//	};
	/**
	 * 扩展combotree，使其支持平滑数据格式
	 */
	$.fn.combotree.defaults.loadFilter = function(data, parent) {
		console.info(">>common.ui>>>$.fn.combotree.defaults.loadFilter");
		var opt = $(this).data().tree.options;
		var idFiled, textFiled, parentField;
		if (opt.parentField) {
			idFiled = opt.idFiled || 'id';
			textFiled = opt.textFiled || 'text';
			parentField = opt.parentField;
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idFiled]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][textFiled];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][textFiled];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	};
	
	/**
	 * 扩展树表格级联选择（点击checkbox才生效）：
	 * 		自定义两个属性：
	 * 		cascadeCheck ：普通级联（不包括未加载的子节点）
	 * 		deepCascadeCheck ：深度级联（包括未加载的子节点）
	 */
	$.extend($.fn.treegrid.defaults,{
		onLoadSuccess : function() {
			var target = $(this);
			var opts = $.data(this, "treegrid").options;
			var panel = $(this).datagrid("getPanel");
			var gridBody = panel.find("div.datagrid-body");
			var idField = opts.idField;//这里的idField其实就是API里方法的id参数
			gridBody.find("div.datagrid-cell-check input[type=checkbox]").unbind(".treegrid").click(function(e) {
				if(opts.singleSelect) return;//单选不管
				if(opts.cascadeCheck || opts.deepCascadeCheck) {
					var id = $(this).parent().parent().parent().attr("node-id");
					var status = false;
					if($(this).attr("checked")){
						target.treegrid('select', id);
						status = true;
					}else{
						target.treegrid('unselect', id);
					}
					//级联选择父节点
					selectParent(target, id, idField, status);
					selectChildren(target, id, idField, opts. deepCascadeCheck, status);
				}
				e.stopPropagation();//停止事件传播
			});
		}
	});
		
	/**
	 * 扩展树表格级联勾选方法：
	 */
	$.extend($.fn.treegrid.methods,{
		/**
		 * 级联选择
		 *		param包括两个参数:
	     *			id:勾选的节点ID
	     *			deepCascade:是否深度级联
		 */
		cascadeCheck : function(target, param){
			var opts = $.data(target[0], "treegrid").options;
			if(opts.singleSelect)
				return;
			var idField = opts.idField;//这里的idField其实就是API里方法的id参数
			var status = false;//用来标记当前节点的状态，true:勾选，false:未勾选
			var selectNodes = $(target).treegrid('getSelections');//获取当前选中项
			for(var i=0;i<selectNodes.length;i++){
				if(selectNodes[i][idField]==param.id)
					status = true;
			}
			//级联选择父节点
			selectParent(target,param.id,idField,status);
			selectChildren(target,param.id,idField,param.deepCascade,status);
		}
	});
		
	/**
	 * 级联选择父节点
	 * id 节点ID
	 * status 节点状态，true:勾选，false:未勾选
	 */
	function selectParent(target, id, idField, status) {
		var parent = target.treegrid('getParent', id);
		if(parent){
			var parentId = parent[idField];
			if(status) {
				target.treegrid('select', parentId);
			} else {
				var isSelect = false;
				var children = target.treegrid('getChildren', parentId);
				if(children) {
					var checkedRows = target.treegrid("getChecked");
					$.each(checkedRows, function(i, obj) {
						$.each(children, function(j, m) {
							if(obj==m) { isSelect = true; return false; }
						});
					});
				}
				if(!isSelect) target.treegrid('unselect', parentId);
			}
			selectParent(target, parentId, idField, status);
		}
	}
	
	/**
	 * 级联选择子节点
	 * @param {Object} target
	 * @param {Object} id 节点ID
	 * @param {Object} deepCascade 是否深度级联
	 * @param {Object} status 节点状态，true:勾选，false:未勾选
	 * @return {TypeName} 
	 */
	function selectChildren(target, id, idField, deepCascade, status) {
		//深度级联时先展开节点
		if(status&&deepCascade)
			target.treegrid('expand', id);
		//根据ID获取下层孩子节点
		var children = target.treegrid('getChildren', id);
		for(var i=0;i<children.length;i++) {
			var childId = children[i][idField];
			if(status)
				target.treegrid('select', childId);
			else
				target.treegrid('unselect', childId);
			selectChildren(target, childId, idField, deepCascade, status);//递归选择子节点
		}
	}
	
	/**
	 * 创建一个模式化的dialog
	 * @returns $.modalDialog.handler 这个handler代表弹出的dialog句柄
	 * @returns $.modalDialog.xxx 这个xxx是可以自己定义名称，主要用在弹窗关闭时，刷新某些对象的操作，可以将xxx这个对象预定义好
	 */
	$.modalDialog = function(options) {
		if ($.modalDialog.handler == undefined) {// 避免重复弹出
			var opts = $.extend({
				title : '',
				width : 840,
				height : 450,
				modal : true,
				onClose : function() {
					$.modalDialog.handler = undefined;
					$(this).dialog('destroy');
				}
				/*onOpen : function() {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
				}*/
			}, options);
			opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
			return $.modalDialog.handler = $('<div/>').dialog(opts);
		}
	};
	
	$.initCombobox = function(selector, options) {
		var opts = $.extend({
			editable:false,
			panelHeight: 120,
		    valueField:'value',
		    textField:'label',
		}, options);
		if(options && opts.data && (options.skipValues || options.selectValues)) {
			opts.data = $.grep(opts.data, function(n,i){
				if($.inArray(n['value'], options.selectValues) == -1) {
					n['selected'] = false;
				} else {
					n['selected'] = true;
				}
				return $.inArray(n['value'], options.skipValues) == -1;
		    });
			if(options.selectValues && options.selectValues.length==0) opts.data[0]['selected'] = true;
			if(!options.selectValues) opts.data[0]['selected'] = true;
		}
		return selector.combobox(opts);
	}
	
	/***************** easyui默认实现的覆盖 END   *****************/
	/**
	 * easyUI 校验扩展
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		// 验证中文
		chinese: {
			validator: function(value) { return /^[\u0391-\uFFE5]+$/.test(value); },
			message: "只能输入汉字"
		},
		// 验证中文
		english: {
			validator: function(value) { return /^[A-Za-z]+$/i.test(value); },
			message: "只能输入英文"
		},
		// 字符验证
		stringCheck: {
			validator: function(value) { return /^[\u0391-\uFFE5\w]+$/.test(value); },
			message: "只能包括中文字、英文字母、数字和下划线"
		},
		// 验证中文,英文,数字
		stringCheckSub: {
			validator: function(value) { return /^[a-zA-Z0-9\u4E00-\u9FA5]+$/.test(value); },
		    message: "只能包括中文字、英文字母、数字"
		},
		// 验证英文字母、数字
		englishCheckSub: {
			validator: function(value) { return /^[a-zA-Z0-9]+$/.test(value); },
		    message: "只能包括英文字母、数字"
		},
		// 验证数字
		numberCheckSub: {
		    validator: function(value) { return /^[0-9]+$/.test(value); },
		    message: "只能输入数字"
		},
		// 手机号码验证
		mobile: {
			validator: function(value) {
				var reg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
				return value.length == 11 && reg.test(value);
			},
		    message: "手机号码格式不正确"
		},
		// 电话号码验证
		telephone: {
			validator: function(value) {
				// 电话号码格式010-12345678
				var reg = /^\d{3,4}?\d{7,8}$/;
				return reg.test(value);
			},
			message: "电话号码格式不正确"
		},
		// 联系电话(手机/电话皆可)验证
		mobileTelephone: {
			validator: function(value) {
				var cmccMobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
				var tel = /^\d{3,4}?\d{7,8}$/;
				return tel.test(value) || (value.length == 11 && cmccMobile.test(value));
			},
			message: "联系电话格式不正确"
		},
		// 传真验证
		faxno: {
			validator: function(value) {
				return /^((\d2,3  )|(\d{3}\-))?(0\d2,3  |0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
			},
			message: "传真号码不正确"
		},
		// 验证国内邮编验证
		zipCode: {
			validator: function(value) {
				var reg = /^[1-9]\d{5}$/;
				return reg.test(value);
			},
			message: "邮编必须长短0开端的6位数字"
		},
		// 身份证号码验证
		idCardNo: {
	      validator: function(value) {
	    	  return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
	      },
	      message: "身份证号码格式不正确"
		},
		// 数字验证大小，结束值应该大于开始值
		// 可以自定义提示信息
		compareDigit: {
			validator: function(toValue, fromValue) {
				if (fromValue == null || fromValue.length == 0
						|| fromValue[0] == null || fromValue[0] == "") {
					return true;
				}
				if (parseFloat(toValue) > parseFloat(fromValue[0])) {
					return true;
				} else {
					if (fromValue.length >= 2) {
						$.fn.validatebox.defaults.rules.compareDigit.message = fromValue[1];
					} else {
						$.fn.validatebox.defaults.rules.compareDigit.message = '结束值应该大于开始值';
					}
					return false
				}
			},
			message: ""
		},
		// 日期、时间验证大小，结束日期应该大于开始日期
		// 可以自定义提示信息
	     compareDate: {
	    	 validator: function(toDate, param) {
		    	  if (param == null || param.length == 0
		    			  || param[0] == null || param[0] == "") {
		    		  return true;
		    	  }
		    	  if (toDate > param[0]) {
		    		  return true;
		    	  } else {
		    		  if (param.length >= 2) {
		    			  $.fn.validatebox.defaults.rules.compareDate.message = param[1];
		    		  } else {
		    			  $.fn.validatebox.defaults.rules.compareDate.message = '结束日期应该大于开始日期';
		    		  }
		    		  return false
		    	  }
  			},
  			message : ""
	     },
	     // 最短长度验证
	     minLength: {
	    	 validator: function(value, param) {
	    		 return value.length >= param[0];
	    	 },
	         message: "请输入至少{0}个字符"
        },
        // 范围长度验证
        length: {
        	validator: function(value, param) {
        		var len = $.trim(value).length;
        		return len >= param[0] && len <= param[1];
        	},
        	message: "输入内容长度必须介于{0}和{1}之间"
        },
        // 整数或小数验证
        intOrFloat: {
        	validator: function(value, param) {
        		return /^\d+(\.\d+)?$/i.test(value);
        	},
        	message: "请输入数字，并确保格式正确"
        },
        // 整数验证
        integer: {
        	validator: function(value, param) {
        		return /^[+]?[1-9]+\d*$/i.test(value);
        	},
        	message: "请输入数字，并确保格式正确"
        },
        // 货币验证
        currency: {
        	validator: function(value, param) {
        		return /^\d+(\.\d+)?$/i.test(value);
        	},
        	message: "货币格式不正确"
        },
        // 年龄验证
        age: {
        	validator: function(value, param) {
        		return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        	},
        	message: "年龄必须是0到120之间的整数"
        },
        // ip验证
        ip: {
        	validator: function(value, param) {
        		return /d+.d+.d+.d+/i.test(value);
        	},
        	message: "IP地址格式不正确"
        },
        // 用户名验证
        username: {
        	validator: function(value, param) {
        		return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        	},
        	message: "用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）"
        },
        // 密码验证
        userpwd: {
        	validator: function(value, param) {
        		if ($("#" + param[0]).val() != "" && value != "") {
        			return $("#" + param[0]).val() == value;
        		} else {
        	        return true;
        		}
        	},
        	message: "两次输入的密码不一致！"
        }
	});
	
})(jQuery)