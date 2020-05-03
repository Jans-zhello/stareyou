﻿<?php 
  session_start();
 ?>
<?php
include_once '../DB/connect.php';
if (isset($_POST['username']) and isset($_POST['password'])) {
  $username = $_POST['username'];
  $password = $_POST['password'];
  if (isset($_SESSION['username'])) {
    unset($_SESSION['username']);
  }
  $_SESSION['username'] = $username;
  $id = session_id();
  $sql = "select * from user where username = '$username' and password = '$password'";
  $sel = mysql_query($sql,$conn);
  if (($username=="zbcooper" && $password=="zbcooper")||($username=="mirages"&&$password=="mirages")) {
echo "<script>window.location.href='../admin/background.php?id=$id';</script>";
        return;
  }
   if (mysql_num_rows($sel)==1 and ($_POST['authcode']==$_SESSION['authcode'])) {
  echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
   echo "<script> alert('登录成功!');window.location.href='../my_page.php?id=$id';
   	       </script>";
   	    return;
  	 }	
   echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />'; 
   echo  "<script> alert('登录失败');window.location.href='../index.html';
   	       </script>";
}
 ?>