package com.google.wallet.objects.verticals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.services.walletobjects.model.Barcode;
import com.google.api.services.walletobjects.model.DateTime;
import com.google.api.services.walletobjects.model.GiftCardClass;
import com.google.api.services.walletobjects.model.GiftCardObject;
import com.google.api.services.walletobjects.model.Image;
import com.google.api.services.walletobjects.model.LatLongPoint;
import com.google.api.services.walletobjects.model.LinksModuleData;
import com.google.api.services.walletobjects.model.Money;
import com.google.api.services.walletobjects.model.TextModuleData;
import com.google.api.services.walletobjects.model.Uri;

/**
 * Class to generate example Loyalty class and objects
 *
 * @author mahues
 */
public class GiftCard {

  /**
   * Generates a GiftCard Object
   *
   * @param issuerId
   * @param classId
   * @param objectId
   * @return loyaltyObject
   */
  public static GiftCardObject generateGiftCardObject(String issuerId,
      String classId, String objectId) {
    // Define Barcode
    Barcode barcode = new Barcode().setType("qrCode")
        .setValue("28343E3")
        .setAlternateText("12345")
        .setLabel("User Id");

    Money balance = new Money();
    balance.setCurrencyCode("USD");
    balance.setMicros(20000000L);

    DateTime balanceUpdateTime = new DateTime();
    balanceUpdateTime.setDate(new com.google.api.client.util.DateTime(new Date()));

    // Define Text Module Data
    List<TextModuleData> textModulesData = new ArrayList<TextModuleData>();
    TextModuleData textModuleData = new TextModuleData()
        .setHeader("Earn double points")
        .setBody("Jane, don't forget to use your Baconrista Rewards when " +
            "paying with this gift card to earn additional points");
    textModulesData.add(textModuleData);

    // Define Links Module Data
    List<Uri> uris = new ArrayList<Uri>();
    Uri uri1 = new Uri().setDescription("My Baconrista Gift Card Purchases")
        .setUri("http://www.baconrista.com/mybalance?id=1234567890");
    uris.add(uri1);
    LinksModuleData linksModuleData = new LinksModuleData().setUris(uris);

    // Define Wallet Instance
    GiftCardObject object = new GiftCardObject()
        .setClassId(issuerId + "." + classId).setId(issuerId + "." + objectId)
        .setState("active").setVersion(1L).setBarcode(barcode)
        .setTextModulesData(textModulesData)
        .setLinksModuleData(linksModuleData)
        .setBalance(balance)
        .setBalanceUpdateTime(balanceUpdateTime)
        .setEventNumber("123456").setCardNumber("123jkl4889").setPin("1111");

    return object;
  }

  /**
   * Generates a GiftCard Class
   *
   * @param issuerId
   * @param classId
   * @return loyaltyClass
   */
  public static GiftCardClass generateGiftCardClass(String issuerId,
      String classId) {

    // Define Text Module Data
    List<TextModuleData> textModulesData = new ArrayList<TextModuleData>();

    TextModuleData textModuleData = new TextModuleData().setHeader("Where to Redeem")
        .setBody(
            "All US gift cards are redeemable in any US and Puerto Rico " +
                "Baconrista retail locations, or online at Baconrista.com where" +
                "available, for merchandise or services.");
    textModulesData.add(textModuleData);

    // Define Links Module Data
    List<Uri> uris = new ArrayList<Uri>();
    Uri uri1 = new Uri().setDescription("Baconrista").setUri("http://www.baconrista.com/");
    uris.add(uri1);
    LinksModuleData linksModuleData = new LinksModuleData().setUris(uris);

    // Define Geofence locations
    List<LatLongPoint> locations = new ArrayList<LatLongPoint>();
    locations.add(new LatLongPoint().setLatitude(37.422601).setLongitude(
        -122.085286));

    // Create class
    GiftCardClass wobClass = new GiftCardClass()
        .setId(issuerId + "." + classId)
        .setIssuerName("Baconrista")
        .setMerchantName("Baconrista")
        .setProgramLogo(
            new Image().setSourceUri(new Uri()
                .setUri("http://farm8.staticflickr.com/7340/11177041185_a61a7f2139_o.jpg")))
        .setTextModulesData(textModulesData)
        .setLinksModuleData(linksModuleData)
        .setReviewStatus("underReview").setAllowMultipleUsersPerObject(true)
        .setLocations(locations);

    return wobClass;
  }
}
