/**
 * 
 */
package com.intuit.qbo.provision;

import com.google.gson.JsonObject;
import com.intuit.qbo.ecommerce.ius.IUSClient;

/**
 * @author bgopinath
 *
 */
public class IUSClientTest {
    public static void main(String[] args) {
	IUSClient iusClient = new IUSClient();

	JsonObject jsonObject = iusClient.login(
		"VenkataGopinath_Bodagala1415088725262", "intuit$123");
	System.out.println(jsonObject);

	String ticket = jsonObject.get("ticket").getAsString();
	String userId = jsonObject.get("userId").getAsString();

	iusClient.updatePassword(userId, ticket, "intuit$1234", "intuit$123");

    }
}
