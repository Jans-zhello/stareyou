<?php
   session_start();
?>
<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
   $userid0 = $_POST['userid0'];
  $sql = "select attention_number,username from user where userid = '$userid0' ";
  $sel = mysql_query($sql,$conn);
  $res = mysql_fetch_array($sel);
  echo $res['attention_number'];
  mysql_close();
?>