<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>I-Decisions</title>
        <script src="resource/jquery.min.js"></script>
        <style>
            body{
                margin:0;
            }
            .bar{
                height: 65px;
                /* padding: 0px; */
                /* background: rgb(227,116,27); */
                background: rgb(46,46,46);
            }
            .left {
                float: left;
                width: 50%;
                background: rgb(227,116,27);
                height: 90%;
                border-right: 1px solid rgb(227,116,27);
                border-top-right-radius: 24em;
                border-left: none;
                border-bottom-right-radius: 24em;
            }
            .right {
                float:left; 
                height: 100%;
            }
            img{
                float:right; 
            }
            .outline {
                background: rgb(255, 255, 255);
                padding: 4px 2px 94px 2px;
                width: 55%;
                height: 46%;
                margin-left: 70%;
                margin-top: 18%;
                /* border-radius: 3px; */
                -webkit-box-shadow: 2px 3px 70px 0px rgba(195,195,195,1);
                -moz-box-shadow: 2px 3px 70px 0px rgba(195,195,195,1);
                box-shadow: 2px 3px 70px 0px rgba(195,195,195,1);
            }
            .login_details {
                margin-top: 5%;
                height: 5%;
                padding: 1%;

            }
            .color{
                color:rgb(140, 128, 128);
            }
            h3{
                color: rgb(227,116,27);
                font-family: Zurich BT;
            }
            hr{
                border-width: 1.5px;
                border-style: solid;
                border-color: rgb(227,116,27);
            }
            p,label{
                color: rgb(227,116,27);
                font-family: Zurich BT;
                font-size: 90%;
            }
            input[type="text"],input[type="password"],select{
                border:0;
                width:55%;
                margin-left:6%;
                border-bottom: 1px solid #c3c3c3;
                outline: none;
/*                color:rgb(227,116,27);*/

            }
            label{
                font-size: 14;
                margin-left:8%;
                margin-top:5%;
            }
            input:disabled{
                background: none;
            }
            #SUBMIT{
                background:rgb(227,116,27);
                background: -webkit-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)); /*Safari 5.1-6*/
                background: -o-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)); /*Opera 11.1-12*/
                background: -moz-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)); /*Fx 3.6-15*/
                background: linear-gradient(to right,rgb(134, 68, 16),rgb(227,116,27));/*Standard*/ 
                margin-top:10%;
                height:27px;
                width:40%;
                color:#fff;
                float:right;
                margin-right: 12%;
                cursor: pointer;
            }
            .hr{
                height: 1px;
                background: #c3c3c3;
                margin-top: 26%;
            }
            select{
                color: #c3c3c3;
                -webkit-appearance: textfield;
                -moz-appearance: textfield;
            }
            select::-ms-expand {
                display: none;
            }
            input[placeholder], [placeholder], *[placeholder] {
                color:  #c3c3c3;
            }
            input[type="text"]:focus,input[type="password"]:focus{
               color:rgb(227,116,27);
            }
            select option:first-child{
                color: rgba(203, 205, 218, 1) ! important; 
            }

        </style>
    </head>