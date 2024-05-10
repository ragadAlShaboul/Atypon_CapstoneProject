<%@ page import="java.util.List" %>
<%@ page import="com.example.testApplication.data.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATYPON Bank</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 20px;
            text-align: center;
        }

        form {
            max-width: 300px;
            margin: 20px auto;
            padding: 15px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #ff69b4;
            color: #fff;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #d44693;
        }

        input[type="text"],
        input[type="password"] {
            border: 1px solid #ccc;
            border-radius: 3px;
            padding: 10px;
            box-sizing: border-box;
        }
        ::placeholder {
            color: #a9a9a9;
        }
        table {
                width: 100%;
                margin-top: 20px;
                border-collapse: collapse;
            }
        th, td {
                border: 1px solid #ddd;
                padding: 8px;
            }

            th {
                background-color: #ffb6c1;
            }

            table {
                margin-bottom: 20px;
            }
    </style>
</head>
<body>

<%

  UserModel localUserModel = (UserModel) request.getAttribute("userModel");
%>
    Welcome <%=localUserModel.getName()%> Your Balance <%=localUserModel.getBalance()%>
    <form action="deposit">
        <input name="amount" placeholder="amount" type="number"/>
         <input type ="hidden" name="hidden" value=<%=localUserModel.getName()%>>
         <p color="red"><%=localUserModel.getMessage1()%></p>
        <input type="submit" value="deposit"/>
    </form>
    <form action="withdraw">
            <input name="amount" placeholder="amount" type="number"/>
             <input type ="hidden" name="hidden" value=<%=localUserModel.getName()%>>
             <p color="red"><%=localUserModel.getMessage2()%></p>
            <input type="submit" value="withdraw"/>
        </form>
    <form action="logout">
        <input type="submit" value="Logout" />
    </form>
</body>
</html>
