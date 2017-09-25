package org.yajac.rvaweek.cache;

import redis.clients.jedis.Jedis;

import java.util.List;

public class CacheManager {

	public static void setCache(final String hash, final String key, final String value) {
		final String redisServer = System.getenv("redisServer");
		final Jedis jedis = new Jedis(redisServer);
		jedis.hset(hash, key, value);
		jedis.close();
	}

	public static String getCache(final String hash, final String key) {
		final String redisServer = System.getenv("redisServer");
		final Jedis jedis = new Jedis(redisServer);
		final String value = jedis.hget(hash, key);
		jedis.close();
		return value;
	}

	public static List<String> getCacheValues(final String hash) {
		final String redisServer = System.getenv("redisServer");
		final Jedis jedis = new Jedis(redisServer);
		final List<String> value = jedis.hvals(hash);
		jedis.close();
		return value;
	}

}
