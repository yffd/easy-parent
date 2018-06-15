<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
// var $combotree_org;
// $(function() {
// 	$.post('/uumc/common/listCombotreeData', 
// 			{'keyCodes':['org'].toString()}, 
// 			function(result) {
// 				if("OK"==result.status) {
// 					var jsonData = result.data;
// 					var orgData = jsonData['org'];
// 					var checkedValue = '';
// 					if(parent.$.modalDialog.handler.combotree_org_checkedValue) {
// 						checkedValue = parent.$.modalDialog.handler.combotree_org_checkedValue;
// 						parent.$.modalDialog.handler.combotree_org_checkedValue = null;
// 					} else if(orgData && orgData[0]) {
// 						checkedValue = orgData[0]['id'];
// 					}
// 					initCombotree(checkedValue, jsonData['org']);
// 				}
// 			}, 'json');
// 	function initCombotree(checkedValue, arrData) {
// 		$combotree_org = $('input[name="orgCode"]').combotree({value: checkedValue, data: arrData});
// 	}


	
// 	$.post('/uumc/common/listComboboxData', 
// 			{'keyCodes':[uidict.acntType].toString()},
// 			function(result) {
// 				if("OK"==result.status) {
// 					var jsonData = result.data;
// 					$.initCombobox($('input[name="acntType"]'), {data: jsonData[uidict.acntType], skipValues:[], selectValues:['u']});
// 				}
// 			}, 'json');
	
// });
function accountInput(self) {
	$('input[name="acntId"]').val($(self).val());
	$('input[name="acntPwd"]').val($(self).val());
}
</script>
<style type="text/css">
.form-div {
	background-color: #F5F5F5;
    border: 1px solid #E3E3E3;
    border-radius: 4px 4px 4px 4px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset;
    margin-top: 0px;
    margin-bottom: 0px;
    min-height: 20px;
    padding: 5px;
}
.form-div table {
/* 	border: 1px solid #cccccc; */
	border: 0px solid #cccccc;
	border-collapse: collapse; 
	border-spacing: 0;
	background-color: #F5F5F5;
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 5px;
}
.form-div table tr {
	border: 1px solid #cccccc;
	padding: 0px;
	height: 30px;
	overflow: hidden;
}
.form-div table tr th {
	word-break: keep-all;
	white-space:nowrap;
  	padding: 0px;
	line-height: 30px;
  	vertical-align: center;
  	text-align: right;
  	min-width: 65px;
  	width:calc(20%);
}
.form-div table tr td {
	word-break: keep-all;
	white-space:nowrap;
  	padding: 0px;
	line-height: 30px;
  	vertical-align: center;
  	text-align: left;
  	width:calc(30%);
}

.form-div table input {
     -webkit-border-radius:4px;
     -moz-border-radius:4px;
     -ms-border-radius:4px;
     -o-border-radius:4px;
     border-radius:4px;
     border: 1px solid #DBDBDB;
     height:20px;
     text-indent:2px;
}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div class="form-div">
			<form id="form_id" method="post" >
				<fieldset>
					<legend class="text-center text-primary">用户基本信息</legend>
					<table>
						<tr>
							<th>名称：</th>
							<td><input name="userName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
							<th>编号：</th>
							<td><input name="userCode" onblur="accountInput(this);" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
						</tr>
						<tr>
							<th>状态：</th>
							<td><input name="userStatus" class="easyui-textbox" required="required"/></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</fieldset>
				<div style="min-height:10px;"/>
				<fieldset>
					<legend class="text-center text-primary">所属机构信息</legend>
					<table>
						<tr>
							<th>机构：</th>
							<td><input name="orgCode" class="easyui-textbox" required="required"/></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</fieldset>
				<div style="min-height:10px;"/>
				<fieldset>
					<legend class="text-center text-primary">账号信息</legend>
					<table>
						<tr>
							<th>账号ID：</th>
							<td><input name="acntId" class="easyui-textbox" required="required"/></td>
							<th>账号密码：</th>
							<td><input name="acntPwd" type="password" class="easyui-textbox" required="required"/></td>
						</tr>
						<tr>
							<th>账号类型：</th>
							<td><input name="acntType" class="easyui-textbox" required="required"/></td>
							<th>账号状态：</th>
							<td><input name="acntStatus" class="easyui-textbox" required="required"/></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>
