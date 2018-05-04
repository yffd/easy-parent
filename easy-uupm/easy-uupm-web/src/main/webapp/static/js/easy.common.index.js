/**
 * 暂未使用
 * @param $
 */
(function($){
	//全局系统对象
    window['commonindex'] = {};
    
    //初始化左侧菜单
    commonindex.initLeftMenu = function(menuJson) {
    	initLeft(menuJson);
    	tabClose();
		tabCloseEven();
		
		if(menuJson && menuJson.length>0) {
			var first = menuJson[0];
			if(first.children && first.children.length>0) {
				var firstName = first.children[0].text;
				var firstUrl = first.children[0].inUrl;
				var iconCls = first.children[0].iconCls;
				$('#tabs').tabs('add',{
					title:firstName,
					content:createFrame(firstUrl),
					closable:true,
	    			icon:iconCls
				}).tabs({
			        onSelect: function (title) {
			            var currTab = $('#tabs').tabs('getTab', title);
			            var iframe = $(currTab.panel('options').content);
						var src = iframe.attr('src');
						if(src)
							$('#tabs').tabs('update', { tab: currTab, options: { content: createFrame(src)} });
			        }
			    });
			}
		}
    }
    
    //初始化左侧
    var initLeft = function(menuJson) {
    	var $ma=$("#menuAccordion");
    	$ma.accordion({animate:false});
        $.each(menuJson, function(i, n) {
            var menulist ='';
            menulist +='<ul>';
            if(n.children && n.children.length>0) {
                $.each(n.children, function(j, o) {
                    menulist += '<li><div><a ref="'+o.id+'" href="javascript:void(0);" rel="' + o.inUrl + '" ><span class="'+o.iconCls+'" >&nbsp;</span><span class="nav">' + o.text + '</span></a></div></li> ';
                })
            }
            menulist += '</ul>';

    		$ma.accordion('add', {
                title: n.text,
                content: menulist,
                iconCls: n.iconCls
            });

        });

    	$('.easyui-accordion li a').click(function() {
    		var tabTitle = $(this).children('.nav').text();
    		var url = $(this).attr("rel");
    		var menuid = $(this).attr("ref");
    		var icon = getIcon(menuid,menuJson);
    		addTab(tabTitle,url,icon);
    		$('.easyui-accordion li div').removeClass("selected");
    		$(this).parent().addClass("selected");
    	}).hover(function(){
    		$(this).parent().addClass("hover");
    	},function(){
    		$(this).parent().removeClass("hover");
    	});

    	//选中第一个
    	var panels = $ma.accordion('panels');
    	var t = panels[0].panel('options').title;
    	$ma.accordion('select', t);
    }

    //获取左侧导航的图标
    var getIcon = function(menuid, menuJson) {
    	var icon = '';
    	$.each(menuJson, function(i, n) {
    		if(n.children && n.children.length>0) {
    			$.each(n.children, function(j, o) {
    			 	if(o.id==menuid) {
    					icon += o.iconCls;
    				}
    			 });
    		}
    	});
    	return icon;
    }

    var addTab = function(subtitle,url,icon) {
    	if(url=='login') {}
    	if(!$('#tabs').tabs('exists',subtitle)) {
    		$('#tabs').tabs('add', {
    			title:subtitle,
    			content:createFrame(url),
    			closable:true,
    			icon:icon
    		});
    	}else{
    		$('#tabs').tabs('select',subtitle);
    		$('#mm-tabupdate').click();
    	}
    	tabClose();
    }
    
    var createFrame = function(url) {
    	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    	return s;
    }
    
    var tabClose = function() {
    	/*双击关闭TAB选项卡*/
    	$(".tabs-inner").dblclick(function() {
    		var subtitle = $(this).children(".tabs-closable").text();
    		$('#tabs').tabs('close',subtitle);
    	})
    	/*为选项卡绑定右键*/
    	$(".tabs-inner").bind('contextmenu', function(e){
    		$('#mm').menu('show', {
    			left: e.pageX,
    			top: e.pageY
    		});
    		var subtitle =$(this).children(".tabs-closable").text();
    		$('#mm').data("currtab",subtitle);
    		$('#tabs').tabs('select',subtitle);
    		return false;
    	});
    }

    //绑定右键菜单事件
    var tabCloseEven = function() {
    	//刷新
    	$('#mm-tabupdate').click(function(){
    		var currTab = $('#tabs').tabs('getSelected');
    		var url = $(currTab.panel('options').content).attr('src');
    		$('#tabs').tabs('update', {
    			tab:currTab,
    			options:{
    				content:createFrame(url)
    			}
    		})
    	})
    	//关闭当前
    	$('#mm-tabclose').click(function() {
    		var currtab_title = $('#mm').data("currtab");
    		$('#tabs').tabs('close',currtab_title);
    	})
    	//全部关闭
    	$('#mm-tabcloseall').click(function() {
    		$('.tabs-inner span').each(function(i,n) {
    			var t = $(n).text();
    			$('#tabs').tabs('close',t);
    		});
    	});
    	//关闭除当前之外的TAB
    	$('#mm-tabcloseother').click(function() {
    		$('#mm-tabcloseright').click();
    		$('#mm-tabcloseleft').click();
    	});
    	//关闭当前右侧的TAB
    	$('#mm-tabcloseright').click(function() {
    		var nextall = $('.tabs-selected').nextAll();
    		if(nextall.length==0) {
    			msgShow('系统提示','后边没有啦~~','error');
//     			alert('后边没有啦~~');
    			return false;
    		}
    		nextall.each(function(i,n) {
    			var t=$('a:eq(0) span',$(n)).text();
    			$('#tabs').tabs('close',t);
    		});
    		return false;
    	});
    	//关闭当前左侧的TAB
    	$('#mm-tabcloseleft').click(function() {
    		var prevall = $('.tabs-selected').prevAll();
    		if(prevall.length==0) {
    			msgShow('系统提示','后边没有啦~~','error');
    			return false;
    		}
    		prevall.each(function(i,n) {
    			var t = $('a:eq(0) span',$(n)).text();
    			$('#tabs').tabs('close',t);
    		});
    		return false;
    	});

    	//退出
    	$("#mm-exit").click(function() {
    		$('#mm').menu('hide');
    	})
    }
    //弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
    var msgShow = function(title, msgString, msgType) {
    	$.messager.alert(title, msgString, msgType);
    }
})(jQuery);