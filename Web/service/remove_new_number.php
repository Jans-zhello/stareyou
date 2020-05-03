<?php 
  session_start();
  header('content-type:text/html charset:utf-8');
 ?>
 <?php
  include_once '../DB/connect.php';
  $userid0 = $_POST['userid0']; 
  $sql = "update new set new_userid_number = 0 where new_userid='$userid0' ";
  mysql_query($sql,$conn);
  echo "处理完成";
  ?>