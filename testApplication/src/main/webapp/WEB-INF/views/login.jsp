<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATYPON Bank</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        form {
            max-width: 400px;
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

        font {
            color: red;
        }
    </style>
</head>
<body>
    <div>
        <p><font color="red">${message}</font></p>
        <form action="login">
            <input name="name" type="text" placeHolder="Username"/>
            <input name="password" type="password" placeHolder="Password"/>
            <input type="submit" value="Login"/>
        </form>
    </div>
</body>
</html>
