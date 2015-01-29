package com.intuit.starterplugin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/mycoolservice/v1")
public class MyCoolService {	
	public static String APP_ID="Intuit.smallbusiness.idcdemo.idcdemoclient";
	public static String APP_SECRET="preprd6U87iYbrcH7Yini3xL1M2tfmFIsCIrw7WW";
	
	@RequestMapping("/sample")
	@ResponseBody
	List<String> sample() {
		List<String> value = new ArrayList<String>();
		value.add("hello");
		return value;
	}

	@RequestMapping("/callsalestax")
	@ResponseBody
	String getoAuthTokens(@CookieValue("qbn.ptc.authid") String userId, @CookieValue("qbn.ptc.tkt") String ticket, @RequestParam("amount") String amount) {
		String value = "";
		try {
			HttpResponse<JsonNode> response = Unirest.post("https://globalsalestax-e2e.api.intuit.com/v1/gst").
				header("Accept", "application/json").
				header("Content-Type","application/json").
				header("Authorization","Intuit_IAM_Authentication intuit_appid="+APP_ID+", intuit_app_secret="+APP_SECRET+", intuit_userid="+userId+", intuit_token="+ticket).
				body("{ \"salesTaxHeader\": { \"documentId\": \"GOOD_DOCUMENT_ID\", \"documentDateTime\": 1008581447000, \"customer\": { \"customerId\": { \"value\": \"ONE_CUSTOMER_ID\", \"schemeName\": \"\" }, \"customerInfo\": { \"businessName\": \"SOME_BUSINESS_NAME\" }, \"billingAddress\": { \"addressInfo\": { \"addressLine1\": \"7545 Torrey Santa Fe Road\", \"addressLine2\": \"Suite ABC\", \"city\": \"San Diego\", \"state\": \"CA\", \"postalCode\": \"92129\", \"country\": \"US\", \"geoCode\": null }, \"standardizeAddress\": null, \"standardizedAddressInd\": true }, \"shippingAddress\": null }, \"salesContext\": { \"salesOrganization\": \"DocStoc\", \"bizProcessType\": null, \"transactionDateTime\": 1410946247000, \"salesParty\": { \"salesBusiness\": { \"name\": \"DocStoc\", \"companyId\": \"074\", \"taxJurisdiction\": null } }, \"taxInfoSpec\": null }, \"salesQuote\": { \"totalTransaction\": { \"value\": "+amount+", \"currency\": \"USD\" }, \"totalTax\": null, \"totalFreight\": null, \"totalDiscount\": null, \"totalCredit\": null } }, \"salesTaxLines\":  { \"salesTaxLines\": [ { \"lineNumber\": 1, \"orderItem\": { \"itemId\": \"SOME_ITEM_ID\", \"description\": \"SOME_DESCRIPTION\", \"itemAttributes\": { \"productClass\": \"DBSS\" } }, \"orderQuantity\": { \"value\": 1, \"uom\": \"Each\" }, \"unitPrice\": { \"amount\": { \"value\": "+amount+", \"currency\": \"USD\" }, \"perQuantity\": { \"value\": 1, \"uom\": \"Each\" } }, \"adjustments\": null, \"tax\": null, \"freight\": null, \"documentReference\": null } ] }  }").
				asJson();
			value = response.getBody().toString();
		} catch (UnirestException ex) {
			ex.printStackTrace();
		}
		 
		return value;
	}
	
}
