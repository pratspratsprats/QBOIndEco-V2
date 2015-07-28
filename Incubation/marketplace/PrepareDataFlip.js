function preparetestdata()
{
	var order = {
			   orderId:"67231148569", 
			   channel:"flipkart",
			   voucher:"APP500", 
			   paymentMode:"Cash On Delivery", 
			   customerFirstName: "Loyal",
			   customerLastName: "Customer",
			   customerEmailId: "loyal_customer@intuit.com",
			   shippingAddress: "Ecospace, Bellandur",
			   state: "KA",
			   city: "Bangalore",
			   country: "India",
			   postalCode: "560103",
			   customerMobile: "9741920694", 
			   lineItems:prepareLineItems() };
	return order;
}

function createlineitem(productId, itemName, sellingPrice, quantity, mrp, sellerId, emailId) {
    this.productId = productId;  
    this.sellingPrice = sellingPrice;
    this.sellerId = sellerId;
    this.quantity = quantity;
    this.mrp=mrp;
    this.itemName=itemName;
    this.emailId=emailId;
}

function payClicked()
{
	var order = preparetestdata();
	posttointuit(order);
}

function prepareLineItems()
{
	var lineitems =  new Array();

	var lineitem4= new createlineitem("P1", "DigiFlip 2.1 Channel 40W PS045 Backlit", "2799","1", "2799", "wsretail3@gmail.com","wsretail@gmail.com" );
	lineitems.push(lineitem4);
	
	var lineitem5= new createlineitem("P2", "Jack and Jones Slim Fit Men Trousers", "2097","1", "2097", "arrowretail3@gmail.com","arrowretail@gmail.com" );
	lineitems.push(lineitem5);

	return lineitems;
}