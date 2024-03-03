<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Спасибо за регитрацию</title>
    <style>
        .container {
            display: flex;
            font-family: "Segoe UI", serif;
            height: 100%;
            width: 100%;
            justify-content: center;
            background-color: #607ff2;
        }

        .container-text {
            margin: 5%;
            font-family: "Segoe UI", serif;
            background-color: white;
            padding: 0 20px 20px 20px;
            border-radius: 20px;
            width: 100%;
            color: black;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="container-text">
        <h1 style="text-align: center">Сброс пароля</h1>
        <h4>Поступил запрос на сброс пароля</h4>
        <span>
            <a href="http://localhost:3000/reset-password?token=${token}">Кликните здесь,</a>
             чтобы перейи на страницу сброса пароля.
        </span>
        <p>
            Если вы не запрашивали сброс пароля - просто проигнорируйте это сообщение.
        </p>
    </div>
</div>
</body>
</html>