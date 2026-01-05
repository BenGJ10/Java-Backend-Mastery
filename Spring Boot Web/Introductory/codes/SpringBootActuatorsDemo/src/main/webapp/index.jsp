<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculator</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <style>
        body{
            margin:0;
            background: linear-gradient(135deg, #0f172a, #1e293b);
            font-family: "Inter", Arial, sans-serif;
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            color:white;
        }

        .calc-container{
            background:#020617;
            padding:22px;
            border-radius:20px;
            width:330px;
            box-shadow:0 30px 70px rgba(0,0,0,.5);
            border:1px solid rgba(255,255,255,0.08);
        }

        .display{
            height:70px;
            background:#020617;
            border:1px solid rgba(255,255,255,0.12);
            border-radius:14px;
            padding:16px;
            font-size:26px;
            text-align:right;
            overflow:hidden;
            letter-spacing:1px;
        }

        .buttons{
            margin-top:18px;
            display:grid;
            grid-template-columns:repeat(4,1fr);
            gap:10px;
        }

        button{
            padding:18px 0;
            border-radius:14px;
            font-size:16px;
            border:none;
            cursor:pointer;
            transition:.2s;
            background:#0b1220;
            color:white;
            border:1px solid rgba(255,255,255,0.1);
        }

        button:hover{
            transform:translateY(-3px);
            box-shadow:0 12px 25px rgba(0,0,0,.3);
        }

        .operator{
            background:#1d4ed8;
        }

        .equal{
            background:#16a34a;
        }

        .clear{
            background:#dc2626;
        }

    </style>
</head>

<body>

<div class="calc-container">

    <div id="display" class="display">0</div>

    <div class="buttons">

        <button class="clear" onclick="clearDisplay()">AC</button>
        <button onclick="append('%')">%</button>
        <button onclick="append('.')">.</button>
        <button class="operator" onclick="append('/')">÷</button>

        <button onclick="append('7')">7</button>
        <button onclick="append('8')">8</button>
        <button onclick="append('9')">9</button>
        <button class="operator" onclick="append('*')">×</button>

        <button onclick="append('4')">4</button>
        <button onclick="append('5')">5</button>
        <button onclick="append('6')">6</button>
        <button class="operator" onclick="append('-')">−</button>

        <button onclick="append('1')">1</button>
        <button onclick="append('2')">2</button>
        <button onclick="append('3')">3</button>
        <button class="operator" onclick="append('+')">+</button>

        <button onclick="append('0')">0</button>
        <button onclick="append('00')">00</button>
        <button onclick="backspace()">⌫</button>
        <button class="equal" onclick="calculate()">=</button>

    </div>
</div>

<script>

    const display = document.getElementById("display");

    function append(value){
        if(display.innerText === "0") display.innerText = "";
        display.innerText += value;
    }

    function clearDisplay(){
        display.innerText = "0";
    }

    function backspace(){
        display.innerText = display.innerText.slice(0,-1);
        if(display.innerText === "") display.innerText = "0";
    }

    function calculate(){
        try{
            let result = eval(display.innerText);

            if(result === Infinity || result === -Infinity){
                display.innerText = "Cannot divide by zero";
                return;
            }

            display.innerText = result;
        }catch(e){
            display.innerText = "Error";
        }
    }

</script>

</body>
</html>
