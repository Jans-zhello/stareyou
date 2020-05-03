<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
  $textid = $_POST['textid']; 
  $sql5 = "select userid,praise_userid from praise where textid = $textid";
  $sel5 = mysql_query($sql5,$conn);
  $num = mysql_num_rows($sel5);
  $array = array();
  while ($res5 = mysql_fetch_array($sel5)) {
  $sql6 = "select * from user where userid = ' $res5[0] ' ";
  $sel6 = mysql_query($sql6,$conn);
  $res6 = mysql_fetch_array($sel6);
  $sql = "select username from user where userid = '$res5[1]' ";
  $sel = mysql_query($sql,$conn);
  $res = mysql_fetch_array($sel);
  $praised_username = $res[0];
  array_push($array,$res6['username']);
  if ($num<12) {
    echo $res6['username']."、"; 
   }
}
if ($num>=12) {
   for ($i=0; $i <11 ; $i++) { 
      echo $array[$i]."、";    
   }
    echo "<font color='#698B22' size='2.5'>等</font>";
  }  
   echo $num."<font color='#698B22' size='2.5'>人为</font>".
   $praised_username."<font color='#698B22' size='2.5'>点赞</font>";
?>
