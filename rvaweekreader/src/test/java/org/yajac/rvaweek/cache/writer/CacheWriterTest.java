package org.yajac.rvaweek.cache.writer;



import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.yajac.rvaweek.cache.CacheManager;

import java.io.IOException;
import java.util.List;

public class CacheWriterTest {

	@Test
	public void test() throws IOException {
		String hash = "hash";
		String key = "key";
		String testValue = "{test:A}";
		CacheManager.setCache(hash, key, testValue);
		String value = CacheManager.getCache(hash, key);
		Assert.assertTrue(value.equals(testValue));
		testValue = "{test:B}";
		CacheManager.setCache(hash, key, testValue);
		value = CacheManager.getCache(hash, key);
		Assert.assertTrue(value.equals(testValue));
		String key2 = "key2";
		String testValue2 = "Test2";
		CacheManager.setCache(hash, key2, testValue2);
		String value2 = CacheManager.getCache(hash, key2);
		Assert.assertTrue(value2.equals(testValue2));
		value = CacheManager.getCache(hash, key);
		Assert.assertTrue(value.equals(testValue));
	}

	@Test
	@Ignore
	public void test2() throws IOException {
		String hash = "08192017";
		List<String> values = CacheManager.getCacheValues(hash);
		Assert.assertTrue(values.size() > 0);
	}

}
