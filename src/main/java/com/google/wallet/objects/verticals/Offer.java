package com.google.wallet.objects.verticals;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.walletobjects.model.*;

/**
 * Generates an example Offer Class and Object
 *
 * @author pying
 */
public class Offer {

  /**
   * Create an example Offer Object
   *
   * @param issuerId
   * @param classId
   * @param objectId
   * @return
   */
  public static OfferObject generateOfferObject(String issuerId,
      String classId, String objectId) {

    // Define Barcode
    Barcode barcode = new Barcode().setType("upcA").setValue("123456789012")
        .setAlternateText("12345").setLabel("User Id");

    // Define Wallet Object
    OfferObject object = new OfferObject().setClassId(issuerId + "." + classId)
        .setId(issuerId + "." + objectId).setBarcode(barcode)
        .setState("active").setVersion(1L);

    return object;
  }

  /**
   * Create an example Offer Class
   *
   * @param issuerId
   * @param classId
   * @return
   */
  public static OfferClass generateOfferClass(String issuerId,
      String classId) {

    // Define the Image Module Data
    List<ImageModuleData> imageModuleData = new ArrayList<ImageModuleData>();

    ImageModuleData image = new ImageModuleData().setMainImage(
        new Image().setSourceUri(
            new Uri().setUri("http://farm4.staticflickr.com/3738/12440799783_3dc3c20606_b.jpg")));

    imageModuleData.add(image);

    // Define Links Module Data
    List<Uri> uris = new ArrayList<Uri>();
    Uri uri1 = new Uri().setDescription("Nearby Locations").setUri("http://maps.google.com/maps?q=google");
    Uri uri2 = new Uri().setDescription("Call Customer Service").setUri("tel:6505555555");

    uris.add(uri1);
    uris.add(uri2);

    LinksModuleData linksModuleData = new LinksModuleData().setUris(uris);

    // Define Text Areas
    List<TextModuleData> textModulesData = new ArrayList<TextModuleData>();

    TextModuleData details = new TextModuleData().setHeader("Details").setBody(
        "20% off one cup of coffee at all Baconrista Coffee locations.  Only one can be used per visit.");
    TextModuleData finePrint = new TextModuleData().setHeader("About Baconrista").setBody(
        "Since 2013, Baconrista Coffee has been committed to making high quality bacon coffee. Visit us in our stores or online at www.baconrista.com");

    textModulesData.add(details);
    textModulesData.add(finePrint);

    // Define Geofence locations
    List<LatLongPoint> locations = new ArrayList<LatLongPoint>();
    locations.add(new LatLongPoint().setLatitude(37.422601).setLongitude(
        -122.085286));
    locations.add(new LatLongPoint().setLatitude(37.424354).setLongitude(
        -122.09508869999999));
    locations.add(new LatLongPoint().setLatitude(40.7406578).setLongitude(
        -74.00208940000002));

    OfferClass wobClass = new OfferClass()
        .setId(issuerId + "." + classId)
        .setIssuerName("Baconrista Coffee")
        .setTitle("20% off one bacon fat latte")
        .setProvider("Baconrista Deals")
        .setTitleImage(
            new Image().setSourceUri(new Uri()
                .setUri("http://farm4.staticflickr.com/3723/11177041115_6e6a3b6f49_o.jpg")))
        .setRedemptionChannel("both")
        .setReviewStatus("underReview")
        .setLinksModuleData(linksModuleData)
        .setImageModulesData(imageModuleData)
        .setTextModulesData(textModulesData)
        .setLocations(locations).setAllowMultipleUsersPerObject(true);

    return wobClass;
  }
}
