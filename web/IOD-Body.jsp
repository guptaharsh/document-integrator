<body>
        <div class="bar"><img src="./icici_bank_logo.png">&nbsp;&nbsp;</div>
        <div class="left">
            <div class="outline">
                <div align="center"><h3><span class="color">Welcome To </span> I-Decisions</h3></div>
                <hr>
                <p align="center">Login with NT-Username and Password</p>
                <div class="login_details">
                    <label>Username</label>
                           <input type="text" placeholder="Username" id="username" onkeydown="if (event.keyCode == 13)
                        document.getElementById('SUBMIT').click()">
                </div>
                <div class="login_details">
                    <label>Password</label>
                           <input type="password" placeholder="Password" id="password" onkeydown="if (event.keyCode == 13)
                        document.getElementById('SUBMIT').click()">
                </div>
                <div class="login_details">
                    <label>User Type</label>
                    <select id="apsOdUserType" disabled>
                        <option value="V">Vendor</option>
                        <option selected value="E">Employee</option>
                    </select>
                </div>
                <div class="login_button">
                    <input type="button" value="Login" id="SUBMIT" onclick="login('Y');"/>
                </div>
                <div class="hr"></div> 
            </div>
        </div>
    </body>