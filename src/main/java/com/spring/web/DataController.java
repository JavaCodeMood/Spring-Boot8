package com.spring.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dao.PersonRepository;
import com.spring.domain.Person;

@RestController
public class DataController {
	//Spring Data JPA已经自动为你注册bean,所以可自动注入
	@Autowired
	PersonRepository personRepository;
	
	/*
	 * 保存
	 * save支持批量保存:<S extends T> Iterable<S> save(Iterable<S> entities);
	 * 
	 * 删除
	 * 支持使用id删除对象，批量删除以及删除全部
	 * void delete(ID id)
	 * void delete(T entity)
	 * void delete(Iterable<? extends T> entities);
	 * void deleteAll();
	 */
	
	//http://localhost:8080/save?name=tom&address=北京 &age=22
	@RequestMapping("/save")
	public Person save(String name,String address,Integer age){
		Person p = personRepository.save(new Person(null,name,age,address));
		return p;
	}
	
	//测试findByAddress
	//http://localhost:8080/q1?address=北京
	@RequestMapping("/q1")
	public List<Person> q1(String address){
		List<Person> p1 = personRepository.findByAddress(address);
		return p1;
	}
	
	//测试findByNameAndAddress
	//http://localhost:8080/q2?name=tom&address=北京
	@RequestMapping("/q2")
	public Person q2(String name,String address){
		Person p2 = personRepository.findByNameAndAddress(name, address);
		return p2;
	}
	
	//测试withNameAndAddressQuery
	//http://localhost:8080/q3?address=北京&name=tom
	@RequestMapping("/q3")
	public Person q3(String name,String address){
		Person p3 = personRepository.withNameAndAddressQuery(name, address);
		return p3;
	}
	
	//测试withNameAndAddressNamedQuery
	//http://localhost:8080/q4?address=昆明&name=aa
	@RequestMapping("/q4")
	public Person q4(String name,String address){
		Person p4 = personRepository.withNameAndAddressNamedQuery(name, address);
		return p4;
	}
	
	//测试排序
	//http://localhost:8080/sort
	@RequestMapping("/sort")
    public List<Person> sort(){
		List<Person> listPerson = personRepository.findAll(new Sort(Direction.ASC, "age"));
		return listPerson;
	}
	
	//测试分页  5条数据分一页
	//http://localhost:8080/page
	@RequestMapping("/page")
	public Page<Person> page(){
		Page<Person> pagePeople = personRepository.findAll(new PageRequest(0,5));
		return pagePeople;
	}
}
