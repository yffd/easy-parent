(function($){
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