package com.google.wallet.objects.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.walletobjects.model.GiftCardObject;
import com.google.api.services.walletobjects.model.LoyaltyObject;
import com.google.api.services.walletobjects.model.OfferObject;
import com.google.wallet.objects.utils.Config;
import com.google.wallet.objects.utils.WobCredentials;
import com.google.wallet.objects.utils.WobPayload;
import com.google.wallet.objects.utils.WobUtils;
import com.google.wallet.objects.verticals.Loyalty;
import com.google.wallet.objects.verticals.Offer;
import com.google.wallet.objects.verticals.GiftCard;

/**
 * This servlet generates Save to Wallet JWTs based on the type URL parameter in
 * the request. Loyalty, Offer, and Generic only contain the Object. Boarding
 * pass contains 2 Classes and 2 Objects representing the a multi leg fight.
 * Credentials are stored in web.xml which is why it needs ServletContext.
 *
 * @author pying
 */
@SuppressWarnings("serial")
public class WobGenerateJwtServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    // Access credentials from web.xml
    ServletContext context = getServletContext();

    Config config = Config.getInstance();

    // Create a credentials object
    WobCredentials credentials = null;
    WobUtils utils = null;

    try {
      credentials = config.getCredentials(context);
      utils = new WobUtils(credentials);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


    // Get type of JWT to generate
    String type = req.getParameter("type");

    // Add valid domains for the Save to Wallet button
    List<String> origins = new ArrayList<String>();
    origins.add("http://localhost:8080");
    origins.add("https://localhost:8080");
    origins.add("http://wobs-quickstart.appspot.com");
    origins.add("https://wobs-quickstart.appspot.com");
    origins.add(req.getScheme() + "://" + req.getServerName() + ":" + req.getLocalPort());

    WobPayload payload = new WobPayload();

    // Create the appropriate Object/Classes
    if (type.equals("loyalty")) {
      LoyaltyObject obj = Loyalty.generateLoyaltyObject(credentials.getIssuerId(),
          context.getInitParameter("LoyaltyClassId"), context.getInitParameter("LoyaltyObjectId"));

      obj.setFactory(new GsonFactory());
          payload.addObject(obj);
    } else if (type.equals("offer")) {
      OfferObject obj = Offer.generateOfferObject(credentials.getIssuerId(),
          context.getInitParameter("OfferClassId"), context.getInitParameter("OfferObjectId"));
      obj.setFactory(new GsonFactory());

      payload.addObject(obj);
    } else if (type.equals("giftcard")) {
      GiftCardObject obj = GiftCard.generateGiftCardObject(credentials.getIssuerId(),
          context.getInitParameter("GiftCardClassId"), context.getInitParameter("GiftCardObjectId"));

      obj.setFactory(new GsonFactory());
      payload.addObject(obj);
    } /*else if (type.equals("generic")) {
      payload.addObject(Generic.generateGenericObject(utils.getIssuerId(),
          "GenericClass", "GenericObject"));
    } else if (type.equals("boardingpass")) {
      payload.addObject(BoardingPass.generateBoardingPassClass(
          utils.getIssuerId(), "BoardingPassClassFirstLeg"));
      payload.addObject(BoardingPass.generateBoardingPassObject(
          utils.getIssuerId(), "BoardingPassClassFirstLeg",
          "BoardingPassObjectFirstLeg"));
      payload.addObject(BoardingPass.generateBoardingPassClass(
          utils.getIssuerId(), "BoardingPassClassSecondLeg"));
      payload.addObject(BoardingPass.generateBoardingPassObject(
          utils.getIssuerId(), "BoardingPassClassSecondLeg",
          "BoardingPassObjectSecondLeg"));
    } */

    // Convert the object into a Save to Wallet Jwt
    String jwt = null;
    // Respond with JWT
    PrintWriter out = null;

    try {
      jwt = utils.generateSaveJwt(payload, origins);
      out = resp.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SignatureException e) {
      e.printStackTrace();
    }
    out.write(jwt);
  }

}
