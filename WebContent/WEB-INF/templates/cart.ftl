<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>
            Bookers
        </title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="scripts/script.js"></script>
        <script>
        </script>
    <head>
    <body>
        <div class="main">
            <h1>BOOKERS</h1>
            <div>
                <table>
                    <tr>
                        <td align="left">
                            <span class="menu_icon" onclick="openNav()">&#9776; Menu</span>
                        </td>
                        <td class="search" align='center'>
                            <form action="HomeServlet" method="post">
                            Search: <input style="height:18px;width:200px" type="text" name="searchValue"/> by 
                            <select style="height:18px" name="searchType">
                            	<option value="title">Title</option>
                                <option value="subject">Subject</option>
                                <option value="author">Author</option>
                                <option value="isbn">ISBN</option>
                            </select>
                            <input type="submit" value="Search" name="searchBook" style="width:100px" />
                        </form>
                        </td>
                        <td align='right'>
                            <span>Welcome ${first} ${last}!</span>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="menu_side" class="sidenav">
                <ul class="menuList">
                    <li style="float:right">
                        <a href="javascript:void(0)" onclick="closeNav()">&times;</a>
                    </li>
                    <li><a href="#">My Account</a></li>
                    <li><a href="#">My Cart</a></li>
                    <li><a href="#">Order History</a></li>
                    <li><a href="login.html">Log Out</a></li>
                </ul>
            </div>
            <div>
          <table class = "carttable">
          <tr>
          	<th> Book Name </th>
          	<th> Quantity </th>
          	<th> Price </th>
          </tr>
          <#list books as book>
          <tr>
          	<td> ${book.title} </td>
          	<td> x ${quantity} </td>
          	<td> $${book.price} </td>
          </tr>
          </#list>
 		<tr>
	 		<td> </td>
	 		<td>Subtotal: </td>
	 		<td>$${subtotal}</td>
 		</tr>
 		<tr>
 			<td> </td>
 			<td>Tax: </td>
 			<td> $${tax} </td>
 		</tr>
 		<tr>
 			<td> </td>
 			<td><strong>Total: </strong></td>
 			<td> $${total} </td>
 		</tr>
          </table>
          </div>
            <div id="cartInfo"> 
                You have (2) items in your cart. Please choose your payment method.
                <br><br>
                <form action="" method="post" id="payment">
                    <select name="filter">
                        <option value="cc1">Visa Credit Card XXXX-0521</option>
                        <option value="cc2">Mastercard Credit Card XXXX-2894</option>
                    </select>
                    <br>
                    <input type="button" value="Select">
                    <input type="button" value="Edit">
                    <br><br>
                    <input type="button" value="Add new payment method">
                    <br><br><br><br>
                    <input id="placeOrder" type="button" value="Place Order">
                </form>
            </div>
            </div>
    </body>
</html>
