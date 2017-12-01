<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>
            Bookers
        </title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="scripts/script.js"></script>
    <head>
    <body>
        <div class="main">
            <h1>BOOKERS ADMINISTRAION</h1>
			 <div>
                <table>
                    <tr>
                        <td align="left">
                            <span class="menu_icon" onclick="openNav()">&#9776; Menu</span>
                        </td>
                        </tr>
                        </table>
                        </div>
                <div id="menu_side" class="sidenav">
                <ul class="menuList">
                    <li style="float:right">
                        <a href="javascript:void(0)" onclick="closeNav()">&times;</a>
                    </li>
                    <li><a class="adminMenu" href="AdminServlet?action=addbook">Add books</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=updatebook">Update books</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=createpromo">Create Promotion</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=addemployee">Add Employees</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=suspendacct">Suspend Account</a></li>  
	                    <li><a class="adminMenu" href="AdminServlet?action=manageuser">Manage Users</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=managesupplier">Manage Suppliers</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=manageshipper">Manage Shippers</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=viewsales">View End of Day Sales Reports</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=viewinventory">View Inventory Report</a></li>
	                    <li><a class="adminMenu" href="AdminServlet?action=viewpublisher">View Publisher Report</a></li>  
	                    <li><a class="adminMenu" href = "index.html">Logout</a></li>    
                </ul>
            </div>
            <div align="center">
                <p align="center">Manage Supplier</p>
                <form>
                    <table class="registration">
                        <tr><td class="registrationForm">First name: </td>       <td class="registrationInput"><input type="text" required></td></tr>
                        <tr><td class="registrationForm">Last Name: </td>        <td class="registrationInput"><input type="text" required></td></tr>
                        <tr><td class="registrationForm">Email: </td>            <td class="registrationInput"><input type="text" required></td></tr>
                        <tr><td class="registrationForm">Employee ID:</td>          <td class="registrationInput"><input type="text" required></td></tr>
						<tr><td class="registrationForm">Phone number: </td>          <td class="registrationInput"><input type="text" required></td></tr>
						<tr><td class="registrationForm">Type of Employee: </td>          <td class="registrationInput"><input type="text" required></td></tr>
                    </table>
                      <input type="submit"  class="SubmitRegistration" value="Add" />
                </form>
				
            </div>
        </div>
	</body>
</html>
