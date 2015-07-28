/**
 * 
 */
package com.intuit.qbo.ecommerce.krc.synch;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author bgopinath
 *
 */
public class GsonTest {
    public static void main(String[] args) {
	String json = "{\r\n"
		+ "    \"QueryResponse\": {\r\n"
		+ "        \"Invoice\": [\r\n"
		+ "            {\r\n"
		+ "                \"Deposit\": 0,\r\n"
		+ "                \"AllowIPNPayment\": false,\r\n"
		+ "                \"AllowOnlinePayment\": false,\r\n"
		+ "                \"AllowOnlineCreditCardPayment\": false,\r\n"
		+ "                \"AllowOnlineACHPayment\": false,\r\n"
		+ "                \"domain\": \"QBO\",\r\n"
		+ "                \"sparse\": false,\r\n"
		+ "                \"Id\": \"16\",\r\n"
		+ "                \"SyncToken\": \"0\",\r\n"
		+ "                \"MetaData\": {\r\n"
		+ "                    \"CreateTime\": \"2014-11-04T00:14:17-08:00\",\r\n"
		+ "                    \"LastUpdatedTime\": \"2014-11-04T00:14:17-08:00\"\r\n"
		+ "                },\r\n"
		+ "                \"CustomField\": [],\r\n"
		+ "                \"DocNumber\": \"3794400007\",\r\n"
		+ "                \"TxnDate\": \"2014-11-04\",\r\n"
		+ "                \"CurrencyRef\": {\r\n"
		+ "                    \"value\": \"INR\",\r\n"
		+ "                    \"name\": \"Indian Rupee\"\r\n"
		+ "                },\r\n"
		+ "                \"LinkedTxn\": [],\r\n"
		+ "                \"Line\": [\r\n"
		+ "                    {\r\n"
		+ "                        \"Id\": \"1\",\r\n"
		+ "                        \"LineNum\": 1,\r\n"
		+ "                        \"Amount\": 60,\r\n"
		+ "                        \"DetailType\": \"SalesItemLineDetail\",\r\n"
		+ "                        \"SalesItemLineDetail\": {\r\n"
		+ "                            \"ItemRef\": {\r\n"
		+ "                                \"value\": \"3\",\r\n"
		+ "                                \"name\": \"Orange\"\r\n"
		+ "                            },\r\n"
		+ "                            \"UnitPrice\": 30,\r\n"
		+ "                            \"Qty\": 2\r\n"
		+ "                        }\r\n"
		+ "                    },\r\n"
		+ "                    {\r\n"
		+ "                        \"Id\": \"2\",\r\n"
		+ "                        \"LineNum\": 2,\r\n"
		+ "                        \"Amount\": 50,\r\n"
		+ "                        \"DetailType\": \"SalesItemLineDetail\",\r\n"
		+ "                        \"SalesItemLineDetail\": {\r\n"
		+ "                            \"ItemRef\": {\r\n"
		+ "                                \"value\": \"4\",\r\n"
		+ "                                \"name\": \"Mango\"\r\n"
		+ "                            },\r\n"
		+ "                            \"UnitPrice\": 50,\r\n"
		+ "                            \"Qty\": 1\r\n"
		+ "                        }\r\n"
		+ "                    },\r\n"
		+ "                    {\r\n"
		+ "                        \"Id\": \"3\",\r\n"
		+ "                        \"LineNum\": 3,\r\n"
		+ "                        \"Amount\": 25,\r\n"
		+ "                        \"DetailType\": \"SalesItemLineDetail\",\r\n"
		+ "                        \"SalesItemLineDetail\": {\r\n"
		+ "                            \"ItemRef\": {\r\n"
		+ "                                \"value\": \"4\",\r\n"
		+ "                                \"name\": \"Mango\"\r\n"
		+ "                            }\r\n"
		+ "                        }\r\n"
		+ "                    },\r\n"
		+ "                    {\r\n"
		+ "                        \"Amount\": 135,\r\n"
		+ "                        \"DetailType\": \"SubTotalLineDetail\",\r\n"
		+ "                        \"SubTotalLineDetail\": {}\r\n"
		+ "                    }\r\n"
		+ "                ],\r\n"
		+ "                \"CustomerRef\": {\r\n"
		+ "                    \"value\": \"4\",\r\n"
		+ "                    \"name\": \"KR Customer\"\r\n"
		+ "                },\r\n"
		+ "                \"BillAddr\": {\r\n"
		+ "                    \"Id\": \"56\",\r\n"
		+ "                    \"Line1\": \"Test\",\r\n"
		+ "                    \"Line2\": \"Test\",\r\n"
		+ "                    \"Line3\": \"Karnataka\",\r\n"
		+ "                    \"City\": \"Bangalore\",\r\n"
		+ "                    \"Country\": \"India\",\r\n"
		+ "                    \"PostalCode\": \"560037\"\r\n"
		+ "                },\r\n"
		+ "                \"ShipAddr\": {\r\n"
		+ "                    \"Id\": \"57\",\r\n"
		+ "                    \"Line1\": \"Test\",\r\n"
		+ "                    \"Line2\": \"Test\",\r\n"
		+ "                    \"Line3\": \"Karnataka\",\r\n"
		+ "                    \"City\": \"Bangalore\",\r\n"
		+ "                    \"Country\": \"India\",\r\n"
		+ "                    \"PostalCode\": \"560037\"\r\n"
		+ "                },\r\n"
		+ "                \"DueDate\": \"2014-12-04\",\r\n"
		+ "                \"GlobalTaxCalculation\": \"NotApplicable\",\r\n"
		+ "                \"TotalAmt\": 135,\r\n"
		+ "                \"PrintStatus\": \"NeedToPrint\",\r\n"
		+ "                \"EmailStatus\": \"NotSet\",\r\n"
		+ "                \"BillEmail\": {\r\n"
		+ "                    \"Address\": \"krcustomer@gmail.com\"\r\n"
		+ "                },\r\n"
		+ "                \"Balance\": 135\r\n" + "            }\r\n"
		+ "        ],\r\n" + "        \"startPosition\": 1,\r\n"
		+ "        \"maxResults\": 1,\r\n"
		+ "        \"totalCount\": 1\r\n" + "    },\r\n"
		+ "    \"time\": \"2014-11-04T01:30:47.878-08:00\"\r\n" + "}";
	json = "{\r\n" + "    \"QueryResponse\": {},\r\n"
		+ "    \"time\": \"2014-11-04T01:40:16.949-08:00\"\r\n" + "}";
	Gson gson = new Gson();
	JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
	JsonObject responseObject = jsonObject.get("QueryResponse")
		.getAsJsonObject();
	JsonElement customerElement = responseObject.get("Invoice");

	if (customerElement == null) {
	    System.out.println("empty invoice");
	    return;
	}
	JsonArray customerArray = customerElement.getAsJsonArray();
	JsonObject customerObject = customerArray.get(0).getAsJsonObject();
	System.out.println(customerObject);

    }
}
