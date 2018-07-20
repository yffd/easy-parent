package com.yffd.easy.demo.ssm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yffd.easy.demo.ssm.domain.User;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月20日 上午9:48:54 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User select(String userCode) {
		String sql = "select id, user_code, user_name, org_code from demo_user where user_code=?";
		User user = this.jdbcTemplate.queryForObject(sql, new Object[]{userCode}, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserCode(rs.getString("user_code"));
				user.setUserName(rs.getString("user_name"));
				user.setOrgCode(rs.getString("org_code"));
				return user;
			}
			
		});
		return user;
	}
	
	public int insert(User user) {
		String sql = "insert into demo_user(user_code, user_name, org_code) values (?,?,?)";
		int rows = this.jdbcTemplate.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user.getUserCode());
				ps.setString(2, user.getUserName());
				ps.setString(3, user.getOrgCode());
			}
			
		});
		return rows;
	}
}

