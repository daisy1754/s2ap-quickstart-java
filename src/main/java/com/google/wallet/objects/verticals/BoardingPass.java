package com.google.wallet.objects.verticals;

import com.google.api.services.walletobjects.model.Barcode;
import com.google.api.services.walletobjects.model.BoardingPassClass;
import com.google.api.services.walletobjects.model.BoardingPassObject;
import com.google.api.services.walletobjects.model.Image;
import com.google.api.services.walletobjects.model.PassengerName;
import com.google.api.services.walletobjects.model.RenderSpec;
import com.google.api.services.walletobjects.model.Uri;
import com.google.api.services.walletobjects.model.WalletObjectMessage;
import com.google.common.collect.ImmutableList;
import com.google.wallet.objects.utils.WobUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate example BoardingPass class and objects.
 *
 * @author pying
 */
public class BoardingPass {

  /**
   * Create a Boarding Pass Object
   *
   * @param issuerId
   * @param classId
   * @param objectId
   * @return
   */
  public static BoardingPassObject generateBoardingPassObject(String issuerId,
      String classId, String objectId) {
    // Define Barcode
    Barcode barcode = new Barcode().setType("qrCode").setValue("28343E3")
        .setAlternateText("12345").setLabel("User Id");

    // Define Messages:
    List<WalletObjectMessage> messages = new ArrayList<WalletObjectMessage>();
    WalletObjectMessage message = new WalletObjectMessage()
        .setHeader("Hi Joe")
        .setBody("Have a safe trip!")
        .setImage(
            new Image().setSourceUri(new Uri()
                .setUri("https://ssl.gstatic.com/codesite/ph/images/search-48.gif")))
        .setActionUri(new Uri().setUri("http://www.google.com"));
    messages.add(message);

    // Define PassengerName:
    PassengerName passengerName = new PassengerName().setPrefix("Mr.")
        .setFirst("Joseph").setMiddle("Robert").setLast("Passenger")
        .setSuffix("Jr.");

    // Define Wallet instance
    BoardingPassObject boardingPass = new BoardingPassObject()
        .setClassId(issuerId + "." + classId)
        .setId(issuerId + "." + objectId)
        .setState("active")
        .setBarcode(barcode)
        .setMessages(messages)

            // BoardingPassObject specific fields
        .setBoardingZone("2").setElectronicTicket(true)
        .setFreqFlierAccountId("31415927").setFreqFlierTierLevel("Gold")
        .setPassengerName(passengerName)
        .setPassengerStatus(ImmutableList.of("STBY"))
        .setRecordLocator("XYZZY1").setSeat("14F").setSeatClass("Economy")
        .setSeatDescriptions(ImmutableList.of("Window"))
        .setSecuritySelecteeStatus("SSSS").setSequenceNumber("17")
        .setSpecialServiceCodes(ImmutableList.of("UMNR"))
        .setTicketNumber("0112358132134");

    return boardingPass;
  }

  /**
   * Create a Boarding Pass Class
   *
   * @param issuerId
   * @param classId
   * @return
   */
  public static BoardingPassClass generateBoardingPassClass(String issuerId,
      String classId) {

    // Define general messages
    List<WalletObjectMessage> messages = new ArrayList<WalletObjectMessage>();
    WalletObjectMessage message = new WalletObjectMessage()
        .setHeader("Free Wifi")
        .setBody("Free Wifi on Flight 123, compliments of Google")
        .setImage(
            new Image().setSourceUri(new Uri()
                .setUri("https://ssl.gstatic.com/codesite/ph/images/search-48.gif")))
        .setActionUri(new Uri().setUri("http://www.google.com"));
    messages.add(message);

    // Define rendering templates per view
    List<RenderSpec> renderSpec = new ArrayList<RenderSpec>();

    RenderSpec listRenderSpec = new RenderSpec().setViewName("g_list")
        .setTemplateFamily("1.boardingPass1_list");
    RenderSpec expandedRenderSpec = new RenderSpec().setViewName("g_expanded")
        .setTemplateFamily("1.boardingPass1_expanded");

    renderSpec.add(listRenderSpec);
    renderSpec.add(expandedRenderSpec);

    BoardingPassClass flight = new BoardingPassClass()
        .setId(issuerId + "." + classId)
        .setIssuerName("Imagine Airlines")
        .setHomepageUri(new Uri().setUri("https://www.example.com"))
        .setRenderSpecs(renderSpec)
        .setMessages(messages)
        .setAllowMultipleUsersPerObject(true)

            // BoardingPassClass specific fields
        .setAircraftType("737")
        .setArrivalAirportCode("SFO")
        .setArrivalCityName("San Francisco")
        .setArrivalDateTimeActual(WobUtils.toDateTime("2013-07-29T18:00:00Z"))
        .setArrivalDateTimeScheduled(
            WobUtils.toDateTime("2013-07-29T17:30:00Z"))
        .setArrivalGate("C12")
        .setArrivalTerminal("C")
        .setArrivalTimeZone("America/Los_Angeles")
        .setBoardingDateTime(WobUtils.toDateTime("2013-07-29T11:30:00Z"))
        .setCarrierCode("IA")
        .setCarrierLogoImage(
            new Image().setSourceUri(new Uri()
                .setUri("https://ssl.gstatic.com/codesite/ph/images/search-48.gif")))
        .setCarrierName("Imagine Airlines")
        .setDepartureAirportCode("BOS")
        .setDepartureCityName("Boston")
        .setDepartureDateTimeActual(WobUtils.toDateTime("2013-07-29T12:30:00Z"))
        .setDepartureDateTimeScheduled(
            WobUtils.toDateTime("2013-07-29T12:00:00Z"))
        .setDepartureGate("B11").setDepartureTerminal("B")
        .setDepartureTimeZone("America/New_York").setFlightNumber("123")
        .setHomepageUri(new Uri().setUri("http://www.google.com"))
        .setOnboardServices(ImmutableList.of("Wifi"))
        .setOperatingCarrierCode("SO")
        .setOperatingCarrierName("SomeOther Airline")
        .setOperatingFlightNumber("456").setStatusCode("delayed")
        .setReviewStatus("underReview");

    return flight;
  }

}
