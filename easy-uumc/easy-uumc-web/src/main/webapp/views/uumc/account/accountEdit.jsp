<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
				<input type="hidden" name="id"/>
				<fieldset>
					<legend class="text-center text-primary">用户基本信息</legend>
					<table>
						<tr>
							<th>账号ID：</th>
							<td><input name="acntId" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入" /></td>
							<th>账号密码：</th>
							<td><input name="acntPwd" class="easyui-textbox easyui-validatebox" required="required" /></td>
						</tr>
						<tr>
							<th>账号状态：</th>
							<td><input name="acntStatus" class="easyui-textbox" required="required"/></td>
							<th>账号类型：</th>
							<td><input name="acntType" class="easyui-textbox" required="required"/></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>
