package com.gangxin.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gangxin.common.utils.DateUtil;
import com.gangxin.common.utils.RandomUtil;
import com.gangxin.common.utils.StringUtil;
import com.gangxin.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class RedisTest {

	@Resource
    private RedisTemplate redisTemplate;
	
	@Test
	public void JDKTest() {
		
		//创建一个集合存储
		ArrayList<User> user_list = new ArrayList<User>();
		
		for (int i = 1; i <= 50000; i++) {
			//随机姓名
			String name=StringUtil.randomChineseString(3);
			//随机性别
			String sex=null;
			int num = StringUtil.randomSex();
			if(num==1) {
				sex="男";
			}else {
				sex="女";
			}
			//随机手机号
			String phone=RandomUtil.randomString(9);
			//随机邮箱
			String emil=RandomUtil.randomEmain();
			//随机生日
			Date date = DateUtil.randomDate(new Date(909000000l), new Date());	
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String birthday=sdf.format(date);
			User user = new User(i,name,sex,"13"+phone,emil,birthday);
			
			//将对象添加到集合中
			user_list.add(user);
		}
		
		ListOperations opsForList = redisTemplate.opsForList();
		
		//向数据库用List存入数据
		Long start=System.currentTimeMillis();
		opsForList.leftPushAll("user_jdk", user_list);
		Long end=System.currentTimeMillis();
		System.out.println("方式：jdk");
		System.out.println("数量：50000");
		System.out.println("储存完毕,共用"+(end-start)+"毫秒");
	}
	
	
	
	@Test
	public void JSONTest() {
		//创建一个集合存储
		ArrayList<User> user_list = new ArrayList<User>();
		
		for (int i = 1; i <= 50000; i++) {
			//随机姓名
			String name=StringUtil.randomChineseString(3);
			//随机性别
			String sex=null;
			int num = StringUtil.randomSex();
			if(num==1) {
				sex="男";
			}else {
				sex="女";
			}
			//随机手机号
			String phone=RandomUtil.randomString(9);
			//随机邮箱
			String emil=RandomUtil.randomEmain();
			//随机生日
			Date date = DateUtil.randomDate(new Date(909000000l), new Date());	
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String birthday=sdf.format(date);
			User user = new User(i,name,sex,"13"+phone,emil,birthday);
			
			//将对象添加到集合中
			user_list.add(user);
		}
		ListOperations opsForList = redisTemplate.opsForList();
		
		//向数据库用List存入数据
		Long start=System.currentTimeMillis();
		opsForList.leftPushAll("user_json", user_list);
		Long end=System.currentTimeMillis();
		System.out.println("方式：json");
		System.out.println("数量：50000");
		System.out.println("储存完毕,共用"+(end-start)+"毫秒");
	}
	
	@Test
	public void HashTest() {
		//创建一个map容器存储对象
		HashMap<String, User> map = new HashMap<String, User>();
		for (int i = 1; i <= 50000; i++) {
			//随机姓名
			String name=StringUtil.randomChineseString(3);
			//随机性别
			String sex=null;
			int num = StringUtil.randomSex();
			if(num==1) {
				sex="男";
			}else {
				sex="女";
			}
			//随机手机号
			String phone=RandomUtil.randomString(9);
			//随机邮箱
			String emil=RandomUtil.randomEmain();
			//随机生日
			Date date = DateUtil.randomDate(new Date(909000000l), new Date());	
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String birthday=sdf.format(date);
			User user = new User(i,name,sex,"13"+phone,emil,birthday);
			
			//将对象添加到集合中
			map.put(i+"", user);
		}
		HashOperations opsForHash = redisTemplate.opsForHash();
		
		//向数据库用List存入数据
		Long start=System.currentTimeMillis();
		opsForHash.putAll("user_hash", map);
		Long end=System.currentTimeMillis();
		System.out.println("方式：hash");
		System.out.println("数量：50000");
		System.out.println("储存完毕,共用"+(end-start)+"毫秒");
		
	}
}
