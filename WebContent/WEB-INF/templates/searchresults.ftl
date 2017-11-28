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
                            Search By: <input style="height:18px" type="text" value="Name, Author, ISBN or All"/>
                            <select style="height:18px">
                                <option value="all">All</option>
                                <option value="name">Name</option>
                                <option value="author">Author</option>
                                <option value="isbn">ISBN</option>
                            </select>
                        </td>
                        <td align='right'>
                            <span>Not Logged In</span>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="menu_side" class="sidenav">
                <ul class="menuList">
                    <li style="float:right">
                        <a href="javascript:void(0)" onclick="closeNav()">&times;</a>
                    </li>
                    <li><a href="index.html">Home</a></li>
                    <li><a href="login.html">Login</a></li>
                    <li><a href="registration.html">Register</a></li>
                    <li><a href="#">About Us</a></li>
                    <li><a href="#">Contact Us</a></li>     
                </ul>
</div>

<#list books as book>
<figure class="searchbook">
  <img class = "images"  src="${book.coverphoto}"/>
  <div class="price">${book.price}</div>
  <figcaption>
    <h3>${book.title}</h3>
    <p>
      ${book.title} by ${book.author} <br/>
      Rating: ${book.rating}
    </p>
    <#if loggedin>
    	<a href="#">Add to Cart</a>    	
    </#if>
  </figcaption>
</figure>
</#list>


</div>
</body>
</html>