package com.intuit.incubation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.intuit.incubation.entity.Seller;


@Repository
public class SellerDAO  extends BaseDAO{

	public Seller insert(final Seller entity) {
		final String sql = "insert into Seller(email, seller_id, company_name,qb_username, qb_password, created_dt, qb_company_id) values (?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
				ps.setObject(0 + 1, entity.getEmailId());
				ps.setObject(1 + 1, entity.getSellerId());
				ps.setObject(2 + 1, entity.getCompanyName());
				ps.setObject(3 + 1, entity.getQbUsername());
				ps.setObject(4 + 1, entity.getQbPassword());
				ps.setObject(5 + 1,  new Date());
				ps.setObject(6+1, entity.getQbCompanyId());
				return ps;
			}
		};
		jdbcTemplate.update(psc, keyHolder);
		entity.setId(new Integer(keyHolder.getKey().toString()));
		return entity;
	}
	
	public void updatePassword(String sellerId)
	{
		String sql = "update seller set qb_password= ?  where seller_id=?";
		jdbcTemplate.update(sql, "intuit01", sellerId);
		
	}
	
	
	public Seller selectBySellerId(String sellerId) {
		String sql = "select * from Seller where seller_id=?";
		Seller entity;

		try {
			entity = this.jdbcTemplate.queryForObject(sql, new SellerRowMapper(), sellerId);
		} catch (EmptyResultDataAccessException e) {
			entity = null;
		}

		return entity;
	}
	
	private class SellerRowMapper implements RowMapper<Seller> {
		public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
			Seller seller = new Seller();
			seller.setId(rs.getInt("id"));
			seller.setEmailId(rs.getString("email"));
			seller.setSellerId(rs.getString("seller_id"));
			seller.setCompanyName(rs.getString("company_name"));
			seller.setQbUsername(rs.getString("qb_username"));
			seller.setQbPassword(rs.getString("qb_password"));
			seller.setCreatedDate(rs.getDate("created_dt"));
			seller.setUpdatedDate(rs.getDate("updated_dt"));
			seller.setQbCompanyId(rs.getString("qb_company_id"));
			return seller;
		}
	}
	
}
