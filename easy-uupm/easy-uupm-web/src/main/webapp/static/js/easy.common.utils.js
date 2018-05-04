(function($){
	/**
	 * 全局系统对象
	 */
    window['utils'] = {};
	/**
	 * 修改ajax默认设置
	 */
	$.ajaxSetup({
		type:'POST',
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.progress('close');
			$.messager.alert('错误', XMLHttpRequest.responseText);
		}
	});
	
	/**
	 * IE检测
	 */
	utils.isLessThanIe8 = function() {
		return ($.support.msie && $.support.version < 8);
	};
	
	/**
	 * 序列化表单到对象
	 */
	utils.serializeObject = function(form) {
		var o = {};
		$.each(form.serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + (this['value']==''?' ':this['value']);
			} else {
				o[this['name']] = this['value']==''?' ':this['value'];
			}
		});
		//console.dir(o);
		return o;
	};
	
	/**
	 * cookies
	 */
	utils.cookies = (function() {
		var fn = function() {};
        fn.prototype.get = function(name) {
        	var cookieValue = "";
            var search = name + "=";
            if (document.cookie.length > 0) {
                offset = document.cookie.indexOf(search);
                if (offset != -1) {
                    offset += search.length;
                    end = document.cookie.indexOf(";", offset);
                    if (end == -1) end = document.cookie.length;
                    cookieValue = decodeURIComponent(document.cookie.substring(offset, end))
                }
            }
            return cookieValue;
        };
        fn.prototype.set = function(cookieName, cookieValue, DayValue) {
            var expire = "";
            var day_value = 1;
            if (DayValue != null) {
                day_value = DayValue;
            }
            expire = new Date((new Date()).getTime() + day_value * 86400000);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + encodeURIComponent(cookieValue) + ";path=/" + expire;
        };
        fn.prototype.remvoe = function(cookieName) {
            var expire = "";
            expire = new Date((new Date()).getTime() - 1);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + escape("") + ";path=/" + expire;
            /*path=/*/
        };
        return new fn();
	})();
	
	/**
	 * 获取随机时间
	 */
	utils.getRandTime = function() {
		var nowDate=new Date();
	 	var str="";
		var hour=nowDate.getHours();//HH
		str+=((hour<10)?"0":"")+hour;
		var min=nowDate.getMinutes();//MM
		str+=((min<10)?"0":"")+min;
		var sec=nowDate.getSeconds(); //SS
		str+=((sec<10)?"0":"")+sec;
		return Number(str);
	};
	
	/**
	 * 格式化字符串
	 */
	utils.formatString = function(str) {
       	for (var i = 0; i < arguments.length - 1; i++) {
       		str = str.replace("{" + i + "}", arguments[i + 1]);
       	}
       	return str;
	};
	
	/**
	 * 扩展javascript Date对象，将 Date 转化为指定格式的String；
	 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符；
	 * 年(y)可以用 1-4 个占位符；
	 * 毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	 * eg:	(new Date()).format("yyyy-MM-dd") ==> 2017-10-13
	 *		(new Date()).format("yyyy-MM-dd HH:mm:ss") ==> 2017-10-13 13:45:33
	 *		(new Date()).format("yyyy-M-d h:m:s.S") ==> 2017-10-13 1:45:33.2
	 *		(new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2017-10-13 01:45:33.2
	 *		(new Date()).format("yyyy-MM-dd E HH:mm:ss") ==> 2017-10-13 五 13:45:33
	 *		(new Date()).format("yyyy-MM-dd EE hh:mm:ss") ==> 2017-10-13 周五 01:45:33
	 *		(new Date()).format("yyyy-MM-dd EEE hh:mm:ss") ==> 2017-10-13 星期五 01:45:33
	 *		(new Date()).format("yyyy-MM-dd EEE hh:mm:ss") ==> 2017-10-13 星期五 01:45:33 04
	 */
	Date.prototype.format = function(fmt) {
		var obj = {
			"M+" : this.getMonth()+1, //月份
			"d+" : this.getDate(), //日
		    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
		    "H+" : this.getHours(), //小时
		    "m+" : this.getMinutes(), //分
		    "s+" : this.getSeconds(), //秒
		    "q+" : Math.floor((this.getMonth()+3)/3), //季度
		    "S" : this.getMilliseconds() //毫秒
		};
		var week = {
			"0" : "\u65e5",
		    "1" : "\u4e00",
		    "2" : "\u4e8c",
		    "3" : "\u4e09",
		    "4" : "\u56db",
		    "5" : "\u4e94",
		    "6" : "\u516d"
		};
		if(/(y+)/.test(fmt)){
	        fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
	    }         
	    if(/(E+)/.test(fmt)){         
	        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay()+""]);         
	    }         
	    for(var k in obj){         
	        if(new RegExp("("+ k +")").test(fmt)){         
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (obj[k]) : (("00"+ obj[k]).substr((""+ obj[k]).length)));         
	        }         
	    }         
	    return fmt;
	}
	
	/**
	 * 长格式日期字符串:yyyy-MM-dd HH:mm:ss
	 */
	Date.prototype.formatDate = function() {
		return this.format('yyyy-MM-dd HH:mm:ss');
	}
	
	/**
	 * 短格式日期字符串:yyyy-MM-dd
	 */
	Date.prototype.formatShortDate = function() {
		return this.format('yyyy-MM-dd');
	}
	
	/**
	 * 格式化字典，根据字典的值，返回显示文本
	 * {value:"name", text:"名称"}
	 */
	utils.fmtDict = function(jsonData, val) {
		var ret = "";
		$.each(jsonData, function(index, obj) {
			if(obj['id']==val) {
				ret = obj['text'];
				return false;
			}
		});
		return ret;
	}
})(jQuery)