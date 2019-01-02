package com.wxm.mapper;

import com.wxm.domain.Item;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    
    @Insert("insert into item (item_id,url,source,title,auther,coverUrl,tags,type,description,publish_time) values (#{itemId},#{url},#{source},#{title},#{auther},#{coverurl},#{tags},#{type},#{description},#{publishTime})")
	@Options(useGeneratedKeys = true)
	int insert(Item item); 
	
	@Update(" update item set item_id=#{itemId},url=#{url},source=#{source},title=#{title},auther=#{auther},coverUrl=#{coverurl},tags=#{tags},type=#{type},description=#{description},publish_time=#{publishTime} where id=#{id}")
	int update(Item item);
	
	@Delete("delete from item where id=#{id}")
	int delete(@Param("id")int id);
	
	@Select("select id,item_id,url,source,title,auther,coverUrl,tags,type,description,publish_time,ctime,utime from item order by id desc limit #{offset},#{limit}")
	List<Item> queryList(@Param("offset")int offset,@Param("limit")int limit);
	
	@Select("select id,item_id,url,source,title,auther,coverUrl,tags,type,description,publish_time,ctime,utime from item where id=#{id}")
	Item getById(@Param("id")int id);
	
	@Select("select count(1) from item")
	int getCount();
}
