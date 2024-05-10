<%@ page import="java.util.List" %>
<%@ page import="com.example.testApplication.data.model.*" %>
<%@ page import="com.fasterxml.jackson.databind.JsonNode" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="java.io.IOException" %>

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

    AdminModel localAdminModel = (AdminModel) request.getAttribute("adminModel");
%>
    Welcome <%=localAdminModel.getName()%>
    <form action="addUser">
        <input name="username" placeholder="User Name" type="text"/>
        <input name="password" placeholder="Password" type="password"/>
        <input name="role" placeholder="user|employee" type="text"/>
         <input type ="hidden" name="hidden" value=<%=localAdminModel.getName()%>>
         <p color="red"><%=localAdminModel.getMessage()%></p>
        <input type="submit" value="Add User"/>
    </form>
    <form action="getUsers">
            <input type="submit" value="Get Users"/>
          <input type ="hidden" name="hidden" value=<%=localAdminModel.getName()%>>
               <table>
                           <thead>
                               <tr>
                                   <th>User Name</th>
                                   <th>balance</th>
                               </tr>
                           </thead>
                           <tbody>
                           <%
                           List<String> users = (List<String>) localAdminModel.getUsers() ;
                                    ObjectMapper objectMapper = new ObjectMapper();
                               for (int i = 0; i < users.size(); i++) {
                               try {
                             JsonNode jsonNode = objectMapper.readTree(users.get(i));

                           %>
                               <tr>
                                   <td><%=jsonNode.get("username").asText()%></td>
                                   <td><%=jsonNode.get("balance").asDouble()%></td>
                               </tr>
                           <%
                           } catch (IOException e) {
                           e.printStackTrace();
                            }
                               }
                           %>
                           </tbody>
                       </table>
           </form>
    </form>
    <form action="getEmployees">
            <input type="submit" value="Get Employees"/>

        <input type ="hidden" name="hidden" value=<%=localAdminModel.getName()%>>
        <table>
                    <thead>
                        <tr>
                            <th>Employee Name</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                    List<String> employees = (List<String>) localAdminModel.getEmployees() ;

                        for (int i = 0; i < employees.size(); i++) {
                        try {
                        JsonNode jsonNode = objectMapper.readTree(employees.get(i));
                    %>
                        <tr>
                            <td><%=jsonNode.get("username").asText()%></td>
                        </tr>
                    <%
                                               } catch (IOException e) {
                                               e.printStackTrace();
                                                }
                                                   }
                                               %>
                    </tbody>
                </table>
    </form>
    <form action="logout">
        <input type="submit" value="Logout" />
    </form>
</body>
</html>
