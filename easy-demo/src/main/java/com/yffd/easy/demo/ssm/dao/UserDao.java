package com.yffd.easy.demo.ssm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
	
	public User getUser(String userCode) {
		String sql = "SELECT ID AS id, USER_CODE AS userCode, USER_NAME AS userName FROM uumc_user where USER_CODE=?";
//		Map<String, Object> result = this.jdbcTemplate.queryForMap(sql, new Object[]{userCode});
//		System.out.println(result);
		User user = this.jdbcTemplate.queryForObject(sql, new Object[]{userCode}, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setUserCode(rs.getString("userCode"));
				user.setUserName(rs.getString("userName"));
				return user;
			}
			
		});
		return user;
	}
}

