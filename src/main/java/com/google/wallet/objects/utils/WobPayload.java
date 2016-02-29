package com.google.wallet.objects.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.walletobjects.model.GiftCardClass;
import com.google.api.services.walletobjects.model.GiftCardObject;
import com.google.api.services.walletobjects.model.LoyaltyClass;
import com.google.api.services.walletobjects.model.LoyaltyObject;
import com.google.api.services.walletobjects.model.OfferClass;
import com.google.api.services.walletobjects.model.OfferObject;
import com.google.gson.Gson;
import com.google.wallet.objects.webservice.WebserviceResponse;

/**
 * Bean to represent the Wob payload object. To add an object to the WobPayload,
 * use the addObject method. This classes uses reflection to determine the
 * correct array to insert into.
 *
 * @author pying
 */
public class WobPayload {
  private List<GenericJson> loyaltyClasses = new ArrayList<GenericJson>();
  private List<GenericJson> offerClasses = new ArrayList<GenericJson>();
  private List<GenericJson> giftCardClasses = new ArrayList<GenericJson>();

  private List<GenericJson> loyaltyObjects = new ArrayList<GenericJson>();
  private List<GenericJson> offerObjects = new ArrayList<GenericJson>();
  private List<GenericJson> giftCardObjects = new ArrayList<GenericJson>();

  private WebserviceResponse webserviceResponse;

  private transient Gson gson = new Gson();
  private transient JsonFactory jsonFactory = new GsonFactory();

  /**
   * Empty default constructor for Gson deserialization
   */
  public WobPayload() {
  }

  /**
   * Adds the object to the appropriate list
   *
   * @param object
   */
  public void addObject(GenericJson object) {
    object.setFactory(jsonFactory);

    if (LoyaltyObject.class.isAssignableFrom(object.getClass())) {
      addLoyaltyObject(gson.fromJson(object.toString(), GenericJson.class));
    } else if (OfferObject.class.isAssignableFrom(object.getClass())) {
      addOfferObject(gson.fromJson(object.toString(), GenericJson.class));
    } else if (GiftCardObject.class.isAssignableFrom(object.getClass())) {
      addGiftCardObject(gson.fromJson(object.toString(), GenericJson.class));
    } else if (LoyaltyClass.class.isAssignableFrom(object.getClass())) {
      addLoyaltyClass(gson.fromJson(object.toString(), GenericJson.class));
    } else if (OfferClass.class.isAssignableFrom(object.getClass())) {
      addOfferClass(gson.fromJson(object.toString(), GenericJson.class));
    } else if (GiftCardClass.class.isAssignableFrom(object.getClass())) {
      addGiftCardClass(gson.fromJson(object.toString(), GenericJson.class));
    } else
      throw new IllegalArgumentException("Invalid Object type: "
          + object.getClass());
  }

  public void addLoyaltyObject(GenericJson object) {
    loyaltyObjects.add(object);
  }

  public List<GenericJson> getLoyaltyObjects() {
    return loyaltyObjects;
  }

  public void setLoyaltyObjects(List<GenericJson> loyaltyObject) {
    this.loyaltyObjects = loyaltyObject;
  }

  public void addOfferObject(GenericJson object) {
    offerObjects.add(object);
  }

  public List<GenericJson> getOfferObjects() {
    return offerObjects;
  }

  public void setOfferObjects(List<GenericJson> offerObject) {
    this.offerObjects = offerObject;
  }

  public void addGiftCardObject(GenericJson object) {
    giftCardObjects.add(object);
  }

  public List<GenericJson> getGiftCardObjects() {
    return giftCardObjects;
  }

  public WebserviceResponse getResponse() {
    return webserviceResponse;
  }

  public void setResponse(WebserviceResponse resp) {
    this.webserviceResponse = resp;
  }

  /**
   * @return the loyaltyClasses
   */
  public List<GenericJson> getLoyaltyClasses() {
    return loyaltyClasses;
  }

  /**
   * @param loyaltyClasses the loyaltyClasses to set
   */
  public void setLoyaltyClasses(List<GenericJson> loyaltyClasses) {
    this.loyaltyClasses = loyaltyClasses;
  }

  public void addLoyaltyClass(GenericJson object) {
    loyaltyClasses.add(object);
  }

  /**
   * @return the offerClasses
   */
  public List<GenericJson> getOfferClasses() {
    return offerClasses;
  }

  /**
   * @param offerClasses the offerClasses to set
   */
  public void setOfferClasses(List<GenericJson> offerClasses) {
    this.offerClasses = offerClasses;
  }

  public void addOfferClass(GenericJson object) {
    offerClasses.add(object);
  }


  /**
   * @return the giftCardClasses
   */
  public List<GenericJson> getGiftCardClasses() {
    return giftCardClasses;
  }

  /**
   * @param giftCardClasses the offerClasses to set
   */
  public void setGiftCardClasses(List<GenericJson> giftCardClasses) {
    this.giftCardClasses = giftCardClasses;
  }

  public void addGiftCardClass(GenericJson object) {
    giftCardClasses.add(object);
  }
}
