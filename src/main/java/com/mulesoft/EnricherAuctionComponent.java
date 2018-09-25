package com.mulesoft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnricherAuctionComponent {

    static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    static String upperalphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	   
    private List<String> enrichList(String bidsArray[]) throws InterruptedException{

        List<String> enrichedBids = new ArrayList<String>();       
        
        for (String bid : bidsArray) {
            enrichedBids.add(enrichSingle(bid));
        }

        return enrichedBids;
    }
    
    private String enrichSingle(String bid) throws InterruptedException{
    	Random randomGenerator = new Random();
        String name;
        String surname;
        String email;
        
        //Do Not Remove, simulates an external lookup
    	Thread.sleep(50);
    	
        name = String.valueOf(upperalphabet.charAt(randomGenerator.nextInt(upperalphabet.length())));
        surname = String.valueOf(upperalphabet.charAt(randomGenerator.nextInt(upperalphabet.length())));
        for (int i = 0; i < 5; i++) {
            name= name.concat(String.valueOf(alphabet.charAt(randomGenerator.nextInt(alphabet.length()))));
        }
        for (int i = 0; i < 5; i++) {
        	surname =surname.concat(String.valueOf(alphabet.charAt(randomGenerator.nextInt(alphabet.length()))));
        }
        email = name+ "." + surname + "@mulesoft.com";
        bid = bid.concat(";BIDDER_NAME=" + name + ";BIDDER_SURNAME=" + surname + ";BIDDER_EMAIL=" + email);
        return bid;
        
    }
    public Object enrichBids(String bids) throws InterruptedException {
        String bidsArray[] = bids.split("\\r?\\n");
        
        int size = bidsArray.length;
        if (size==1){
        	return enrichSingle(bidsArray[0]);
        }else{
        	return enrichList(bidsArray);
        }
        
    }

}
