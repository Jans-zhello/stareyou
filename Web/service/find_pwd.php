<?php 
include_once '../DB/connect.php';
 ?>
 <?php 
  if (!empty($_POST['txtName']) and !empty($_POST['txtPwd']) and !empty($_POST['txtMail'])) 
  {
    $username = $_POST['txtName'];
    $phone = $_POST['txtPwd'];
    $mail = $_POST['txtMail'];
    $sql = "select password from user,register where username='$username' and phone='$phone' and mail='$mail' ";
    $sel = mysql_query($sql,$conn);
     if (mysql_num_rows($sel)<=0){
        echo "找回失败,请重新检验所填信息！";
        exit();
     }
    $res = mysql_fetch_array($sel);?>
   <?php 
   echo "您的密码为: ".$res[0];
   ?>
  <?php 
  }else{
    echo  "信息不完整";
  }
  ?>