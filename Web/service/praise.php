<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
  $userid0 = $_POST['userid0'];
  $userid  = $_POST['userid'];
  $textid  = $_POST['textid'];
  $date = date("Y-m-d H:i:s");
  $sql00 = "select * from praise";
  $sel00 = mysql_query($sql00,$conn);
  while ($res00 = mysql_fetch_array($sel00)) {
     if (($userid0==$res00['userid']) && ($userid==$res00['praise_userid']) && ($textid==$res00['textid'])) {
       echo "您已赞过";
       return;
     }
   }
  $sql01 = "select * from new";
  $sel01 = mysql_query($sql01,$conn);
  while ($res01 = mysql_fetch_array($sel01)) {
     if (($userid0==$res01['userid']) && ($userid==$res01['new_userid']) && ($textid==$res01['textid']) && $res01['new_content']=="怒赞") {
       echo "您已赞过";
       return;
     }
   } 
  $sql = "insert into praise(userid,praise_userid,textid,praise_date) values('$userid0','$userid','$textid','$date')";
   mysql_query($sql);
  $sql2 = "insert into new(new_content,userid,new_userid,new_userid_number,textid,new_date,new_status) values('怒赞','$userid0','$userid','1','$textid','$date','已查看')";
   mysql_query($sql2);
   mysql_close();
 ?>
 <?php
  $sql5 = "select userid from praise where textid = '$textid' ";
  $sel5 = mysql_query($sql5,$conn);
  while ($res5 = mysql_fetch_array($sel5)) {
  $sql6 = "select * from user where userid = ' $res5[0] ' ";
  $sel6 = mysql_query($sql6,$conn);
  $res6 = mysql_fetch_array($sel6);  
  echo $res6['username']."##".++$num."&&";
}
?>