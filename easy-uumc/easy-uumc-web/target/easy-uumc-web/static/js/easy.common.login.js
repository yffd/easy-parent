// 表单提交
function submitForm() {
	var submit = true;
	var errorMsg = "";
	if($("input[name='userName']").val() == "") {
		errorMsg = "请输入用户名!";
		submit = false;
	}
	if($("input[name='password']").val() == "") {
		errorMsg = "请输入密码!";
		submit = false;
	}
	if($("input[name='captchaCode']").val() == "") {
		errorMsg = "请输入验证码!";
		submit = false;
	}
	if(!submit) {
		showError(errorMsg);
		jrumble();
		setTimeout('hideTop()', 1000);
	} else {
		hideTop();
		loading('登陆中..', 1);
		setTimeout("unloading()", 1000);
		setTimeout("login()", 1000);
	}

}

//显示错误提示
function showError(str) {
	$('#alertMessage').addClass('error').html(str).stop(true, true).show().animate({
		opacity : 1,
		right : '0'
	}, 500);

}
//隐藏错误提示
function hideTop() {
	$('#alertMessage').animate({
		opacity : 0,
		right : '-20'
	}, 500, function() {
		$(this).hide();
	});
}
// 表单晃动
function jrumble() {
	$('.inner').jrumble({
		x : 4,
		y : 0,
		rotation : 0
	});
	$('.inner').trigger('startRumble');
	setTimeout('$(".inner").trigger("stopRumble")', 500);
}

//加载信息
function loading(name, overlay) {
	$('body').append('<div id="overlay"></div><div id="preloader">' + name + '..</div>');
	if (overlay == 1) {
		$('#overlay').css('opacity', 0.1).fadeIn(function() {
			$('#preloader').fadeIn();
		});
		return false;
	}
	$('#preloader').fadeIn();
}
//取消加载信息
function unloading() {
	$('#preloader').fadeOut('fast', function() {
		$('#overlay').fadeOut();
	});
}

//登录处理函数
function login() {
	var actionUrl = $('form').attr('action');//提交路径
 	var formData = new Object();
	var data = $(":input").each(function() {
		 formData[this.name] =$("input[name='"+this.name+"']").val();
	});
	$.ajax({
		async: false,
		cache: false,
		type: 'POST',
		url: actionUrl,// 请求的action路径
		data: formData,
		error: function() {// 请求失败处理函数
		},
		success: function(data) {
			if(data.statusCode=='OK') {
				loginSuccess();
				setTimeout("window.location.href='admin/index.jsp'", 1000);
			} else {
				showError(data.statusDesc);
				$("#Kaptcha").attr("src", 'Kaptcha.jpg?' + new Date().getTime());
			}
		}
	});
}

//验证通过加载动画
function loginSuccess() {
	$("#login").animate({
		opacity : 0,
		top : '60%'
	}, 500, function() {
		$(this).fadeOut(200, function() {
			$(".text_success").slideDown();
			$("#successLogin").animate({
				opacity : 1,
				height : "200px"
			}, 1000);
		});
	});
}
