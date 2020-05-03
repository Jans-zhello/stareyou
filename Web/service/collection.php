<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
  $userid0 = $_POST['userid0'];
  $userid = $_POST['userid'];
  $textid = $_POST['textid'];
  $date = date("Y-m-d H:i:s");
  $sql00 = "select * from collection";
  $sel00 = mysql_query($sql00,$conn);
  while ($res00 = mysql_fetch_array($sel00)) {
     if (($userid0==$res00['userid']) && ($userid==$res00['collect_userid']) && ($textid==$res00['collect_textid'])) {
       echo "您已收藏!请尝试刷新在列表中查看";
       return;
     }
   }
  $sql01 = "select * from new";
  $sel01 = mysql_query($sql01,$conn);
  while ($res01 = mysql_fetch_array($sel01)) {
     if (($userid0==$res01['userid']) && ($userid==$res01['new_userid']) && ($textid==$res01['textid']) && $res01['new_content']=="收藏") {
       echo "您已收藏!请尝试刷新在列表中查看";
       return;
     }
   }
  $sql = "insert into collection (userid,collect_userid,collect_textid,collection_date) values ('$userid0','$userid','$textid','$date')";
  $sql2 = "update user set collection_number = collection_number+1 where userid = '$userid0' ";
  mysql_query($sql,$conn);
  mysql_query($sql2,$conn);
  $sql3 = "select collection_number from user where userid = '$userid0' ";
  $sel = mysql_query($sql3,$conn);
  $res = mysql_fetch_array($sel);
  echo $res['collection_number'];
$sql5 = "insert into new(new_content,userid,new_userid,new_userid_number,
 textid,new_date,new_status) values('收藏','$userid0','$userid','1','$textid','$date','已查看')";
  mysql_query($sql5);
  mysql_close();
?>