<?php
   session_start();
?>
<?php 
  include_once 'date.php';
  include_once 'expression_show.php';
 ?>
<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
  $content = $_POST['review_content']; 
  $userid0 = $_POST['userid0'];
  $userid  = $_POST['userid'];
  $textid  = $_POST['textid'];
  $date = date("Y-m-d H:i:s");
  $sql = "insert into review (review_content,userid,review_userid,textid,review_date) values('$content','$userid0','$userid','$textid','$date')";
   $sql4 = "insert into new(new_content,userid,new_userid,new_userid_number,textid,new_date,new_status) values('评论','$userid0','$userid','1','$textid','$date','未回复')";
   mysql_query($sql,$conn);
   mysql_query($sql4);
   mysql_close();
 ?>
 <?php 
  $sql2 = "select review_content,userid,review_date from review where textid = '$textid' ";
  $sel2 = mysql_query($sql2,$conn);
  while ($res2 = mysql_fetch_array($sel2)) {
  $sql3 = "select * from user where userid = ' $res2[1] ' ";
  $sel3 = mysql_query($sql3,$conn);
  $res3 = mysql_fetch_array($sel3);
  $content2 = explode("/",$res2['review_content']);
  $time = strtotime($res2['review_date']);
  echo  "<font size='2.5' color='#912CEE'>".$res3['username']."</font>"."评论: ";
  echo  tranExpression($content2)."&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  echo tranTime($time)."<br>";
}
?>
