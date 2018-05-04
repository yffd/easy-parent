<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
	table {
	    background-color: transparent;
	    border-collapse: collapse;
	    border-spacing: 0;
	    max-width: 100%;
	}
	fieldset {
	    border: 0 none;
	    margin: 0;
	    padding: 0;
	}
	legend {
	    -moz-border-bottom-colors: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	    border-color: #E5E5E5;
	    border-image: none;
	    border-style: none none solid;
	    border-width: 0 0 1px;
	    color: #999999;
	    line-height: 20px;
	    display: block;
	    margin-bottom: 10px;
	    padding: 0;
	    width: 100%;
	}
	input, textarea {
	    font-weight: normal;
	}
	table, th, td {
		text-align:left;
		padding: 2px;
	}
	.error {
	border-color: #c43d3d!important;
	color:#5A0000 !important;
	text-shadow:1px 1px 1px #E64040;
}
</style>

<script type="text/javascript">
	function savePwd() {
		var _old = $('#oldPassword').val();
		var _new = $('#newPassword').val();
		var _confirm = $('#confirmPassword').val();
		if(_old=='') {
			$('#alertMessage').addClass('error').html("旧密码不能为空！");
		} else if(_new=='') {
			$('#alertMessage').addClass('error').html("新密码不能为空！");
		} else if(_new!=_confirm) {
			$('#alertMessage').addClass('error').html("新密码和确认密码不一致");
		} else {
			$('#alertMessage').addClass('error').html("");
		}
		
		var data = {
			oldPassword : _old,
			newPassword : _new
		};
		$.post("pms/user/changePassword", data, function(result) {
			if('OK'==result.statusCode) {
				$changePassword.dialog('close');
			}
			$.messager.show({
				title : "系统提示",
				msg : result.statusDesc,
				timeout : 1000 * 2
			});
		}, "JSON").error(function() {
			$.messager.show({
				title : "系统提示",
				msg : result.statusDesc,
				timeout : 1000 * 2
			});
		});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" style="overflow: hidden;padding: 10px;">
		<form id="form_id" method="post">
			<fieldset>
				<legend>密码修改</legend>
				<div id="alertMessage"></div>
				<table>
					<tr>
						<th>旧密码:</th>
						<td><input id="oldPassword" type="password"/></td>
					</tr>
					<tr>
						<th>新密码:</th>
						<td><input id="newPassword" type="password"/></td>
					</tr>
					<tr>
						<th>确认新密码:</th>
						<td><input id="confirmPassword" type="password"/></td>
					</tr>
					<tr></tr>
				</table>
			</fieldset>
		</form>
	</div>
	
</div>
