package com.wxm.mapper;

import com.wxm.domain.ItemSet;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemSetMapper {
    
    @Insert("insert into item_set (name,access_token,item_numbers,terminal_type,main_url) values (#{name},#{accessToken},#{itemNumbers},#{terminalType},#{mainUrl})")
	@Options(useGeneratedKeys = true)
	int insert(ItemSet itemSet); 
	
	@Update("update item_set set name=#{name},access_token=#{accessToken},status=#{status},item_numbers=#{itemNumbers},terminal_type=#{terminalType},main_url=#{mainUrl} where id=#{id}")
	int update(ItemSet itemSet);
	
	@Delete("delete from item_set where id=#{id}")
	int delete(@Param("id")int id);
	
	@Select("select id,name,ctime,utime,access_token,status,item_numbers,terminal_type,main_url from item_set order by id desc limit #{offset},#{limit}")
	List<ItemSet> queryList(@Param("offset")int offset,@Param("limit")int limit);
	
	@Select("select id,name,ctime,utime,access_token,status,item_numbers,terminal_type,main_url from item_set where id=#{id}")
	ItemSet getById(@Param("id")int id);
	
	@Select("select count(1) from item_set")
	int getCount();
}
