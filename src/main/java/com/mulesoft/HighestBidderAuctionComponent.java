package com.mulesoft;

import java.util.List;

public class HighestBidderAuctionComponent {

    public String getHighestBidder(List<String> bids) {
        int highestBid = 0;
        String highestBidder = "";
        int currentBid;

        for (String bid : bids) {
            currentBid = new Integer(bid.substring(bid.indexOf("=") + 1, bid.indexOf(";", 0)));
            if (currentBid > highestBid) {
                highestBid = currentBid;
                highestBidder = bid;
            }
        }

        return highestBidder;
    }
}
