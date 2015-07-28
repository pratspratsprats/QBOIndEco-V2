function preparetestdata()
{
	var order = {
			   orderId:"67231148568", 
			   channel:"myntra",
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

	var lineitem4= new createlineitem("P3", "Nike Men Blue ODI SS Fan T-shirt", "1895","1", "1895", "planetsports3@gmail.com","planetsports@gmail.com" );
	lineitems.push(lineitem4);
	
	var lineitem5= new createlineitem("P4", "Roadster Men Black Corvette Slim Fit Jeans", "1119","1", "1119", "arrowretail3@gmail.com","arrowretail@gmail.com" );
	lineitems.push(lineitem5);

	return lineitems;
}