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
	table, th, td, p {
		text-align:left;
		padding: 2px;
	}
</style>

<script type="text/javascript">
$(function() {
	$.post("showLoginUserInfo", {}, function(result) {
		if('OK'==result.statusCode) {
			var user = result.respData;
			$("p[name='userName']").html(user.userName);
			$("p[name='userCode']").html(user.userCode);
			$("p[name='orgName']").html(user.organization.orgName);
			$("p[name='tel']").html(user.tel);
			$("p[name='email']").html(user.email);
			
		}
	});
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" style="overflow: hidden;padding: 10px;">
		<form id="form_id" method="post">
			<fieldset>
				<legend>当前用户信息</legend>
				<table>
					<tr>
						<th>名称:</th>
						<td><p name="userName"></p></td>
						<th>编号:</th>
						<td><p name="userCode"></p></td>
					</tr>
					<tr>
						<th>机构:</th>
						<td><p name="orgName"></p></td>
					</tr>
					<tr>
						<th>电话:</th>
						<td><p name="tel"></p></td>
						<th>邮箱:</th>
						<td><p name="email"></p></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
