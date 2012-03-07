package com.sumilux.idme.sdk;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.sumilux.ssi.client.Idme;
import com.sumilux.ssi.client.IdmeClient;
import com.sumilux.ssi.client.IdmeException;


public class SimpleTest {

	@Test
	public void testIsMatchAppNameAndSecret() {
		String token = "dummy";
		try {
			Idme idme = new Idme(token);
			assertTrue(idme.isMatchAppNameAndSecret("SSISample", "9ccf50d91d8c4f0cbb4742131e5fe13a"));
		} catch (IdmeException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
