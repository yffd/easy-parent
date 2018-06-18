<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

</script>
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
							<td><input name="userCode" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
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
			</form>
		</div>
	</div>
</div>
