package com.mulesoft;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

import static org.junit.Assert.*;

public class ScatterGatherLabTestCase extends FunctionalTestCase {

    private final int NO_OF_BIDS = 100;

    @Override
    protected void doSetUp() throws Exception {
      int amount;
      int bidderId;
      Random randomGenerator = new Random();
      BufferedWriter file = new BufferedWriter(new FileWriter("auction/bids.txt"));

      for (int i = 0; i <= NO_OF_BIDS; i++) {
          amount = randomGenerator.nextInt(NO_OF_BIDS);
          bidderId = randomGenerator.nextInt(NO_OF_BIDS);
          file.write("BID_AMOUNT=" + amount + ";BIDDER_ID=" + bidderId + "\n");
      }

      file.close();
    }

    protected String getConfigResources() {
      return "src/main/app/mule-config.xml";
    }

    @Test
    public void testAuctionService() throws Exception {
      MuleClient client = muleContext.getClient();
      MuleMessage result = client.request("vm://auction.service", RECEIVE_TIMEOUT * 3);
      assertNotNull(result);
      assertNull(result.getExceptionPayload());
      assertFalse(result.getPayload() instanceof NullPayload);
      assertTrue(result.getPayload() instanceof String);
      System.err.println("TestResult: " + result.getPayloadAsString());
      assertTrue(result.getPayloadAsString().contains("BIDDER_EMAIL"));
    }
}
