package com.example.demo.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
public class cacheController {
	private static int count=0;
	@Cacheable(value="key1",key = "'key1'")
	@GetMapping("test1")
	public String key1() {
		return count+"";
	}
	
	@GetMapping("test2")
	public String test2() {
		count++;
		return count+"";
	}
	//@CacheEvict(value = "key1",key = "'key1'",allEntries = true)
	@Caching(evict = {
			@CacheEvict(value = "key1",key = "'key1'")
	})
	@GetMapping("test3")
	public String test3() {
		return UUID.randomUUID().toString();
	}

}
