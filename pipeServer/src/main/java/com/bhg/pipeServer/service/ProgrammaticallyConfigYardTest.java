package com.bhg.pipeServer.service;

import com.bhg.pipeServer.zookeeper.ProgrammaticallyConfigYard;

/**
 * @author llq
 * 
 */
public class ProgrammaticallyConfigYardTest {

	public static void main(String[] args) {
		ProgrammaticallyConfigYard yard = new ProgrammaticallyConfigYard(
				"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", null);
		yard.add("testKey1", "1");
		yard.add("testKey2", "2");
		yard.add("testKey3", "3");
		yard.add("testKey4", "4");
		yard.add("testKey5", "5");
		yard.add("testKey6", "6");
		System.out.println("value is===>" + yard.get("testKey1"));
		System.out.println("value is===>" + yard.get("testKey2"));
		System.out.println("value is===>" + yard.get("testKey3"));
		System.out.println("value is===>" + yard.get("testKey4"));
		System.out.println("value is===>" + yard.get("testKey5"));
		System.out.println("value is===>" + yard.get("testKey6"));
		yard.update("testKey6", "testKey6");
		System.out.println("update testKey6 value is===>" + yard.get("testKey6"));
		yard.delete("testKey1");
		yard.delete("testKey2");
		yard.delete("testKey3");
		yard.delete("testKey4");
		yard.delete("testKey5");
		yard.delete("testKey6");
	}
}