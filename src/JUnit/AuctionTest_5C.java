package JUnit;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import model.Auction;
import model.AuctionItem;
import model.Bid;
import model.Bidder;

/**
 * Tests that an auction will not allow bids from a bidder
 * that has reached the maximum allowed bids.
 * 
 * @author Jared Malone (5/3/2018)
 */
public class AuctionTest_5C {

	/** Auction test fixture. **/
	private static Auction auction;
	
	/** Bidder test fixture. **/
	private static Bidder bidder;
	
	/** The maximum allowed bids for the bidder. **/
	private final static int MAX_BID_COUNT = 4;
	
	@Before
	public void setUp() throws Exception {
		auction = new Auction(null, 10, MAX_BID_COUNT);
		bidder = new Bidder("tester", "Test Bidder");
		
		//add item test fixtures
		for (int i = 0; i < MAX_BID_COUNT - 1; i++) {
			AuctionItem testItem;
			testItem = new AuctionItem(BigDecimal.valueOf((i+1)), 
					null, "Test Item " + (i + 1));
			auction.addItem(testItem);
		}
		
		//add bid test fixtures
		Bid testBid;
		Collection<AuctionItem> items = auction.getAllItems();
		for (AuctionItem e : items) {
			testBid = new Bid(bidder, e, 
					e.getMinimumBid().add(BigDecimal.valueOf(1)));
			auction.addBid(bidder, testBid);
		}
	}

	@Test
	public void isAllowedToBidAuctionHasOneLessThanMaximumAllowed_ShouldReturnTrue() {
		assertTrue(auction.isAllowingNewBid(bidder));
	}
	
	@Test
	public void IsAllowToBidAuctionHasMaximumAllowedBids_ShouldReturnFalse() {
		// add one more bid to reach maximum
		AuctionItem testItem;
		testItem = new AuctionItem(BigDecimal.valueOf((MAX_BID_COUNT)), 
				null, "Test Item " + MAX_BID_COUNT);
		auction.addItem(testItem);
		
		Bid testBid = new Bid(bidder, testItem, 
				BigDecimal.valueOf(MAX_BID_COUNT+1));
		auction.addBid(bidder, testBid);
		
		assertFalse(auction.isAllowingNewBid(bidder));
	}
	

}
