package com.sumilux.idme.sdk;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sumilux.ssi.client.Idme;
import com.sumilux.ssi.client.IdmeException;


public class SimpleTest {

	@Test
	public void testIsMatchAppNameAndSecret() {
		String token = "dummy";
		try {
			Idme idme = new Idme(token);
			assertTrue(idme.isMatchAppNameAndSecret("gnt", "gntsumilux"));
		} catch (IdmeException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	

//	@Test
//	public void TestSSICache() {
//		try {
//			String token = Idme.allocateTestUser();
//			Idme idme = new Idme(token);
//			JSONObject jo1 = idme.getUserProfile();
//			JSONObject jo2 = idme.getUserProfile();
//			assertEquals(jo1.toString(), jo2.toString());
//		} catch (IdmeException e) {
//			e.printStackTrace();
//			assertTrue(false);
//		}
//	}
}
