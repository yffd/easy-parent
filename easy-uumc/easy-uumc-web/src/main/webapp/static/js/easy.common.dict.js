(function($){
	//全局系统对象
    window['uidict'] = {
    		status : 'status',
		    acntType : "acntType",
			appSysType : "appSysType",
			ttType : "ttType",
			ttServeStatus : "ttServeStatus",
			ttServeType : "ttServeType",
			rsType : "rsType"
    };
    
    /**
	 * 格式化字典，根据字典的值，返回显示文本
	 * {value:"name", label:"名称"}
	 */
    uidict.fmtDict = function(jsonData, value) {
		var ret = "";
		if(!value) return ret;
		$.each(jsonData, function(index, obj) {
			if(obj) {
				if(obj['value']==value) {
					ret = obj['label'];
					return false;
				}
			}
		});
		return ret;
	}
	
})(jQuery);