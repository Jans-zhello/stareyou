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
  $content = $_POST['response_content']; 
  $userid0  = $_POST['new_userid'];
  $userid = $_POST['userid'];
  $textid  = $_POST['textid'];
  $strtotime_new_date = $_POST['new_date'];
  $new_date = date("Y-m-d H:i:s",$strtotime_new_date);
  $date = date("Y-m-d H:i:s");
  $sql = "insert into review (review_content,userid,review_userid,textid,review_date) values('$content','$userid0','$userid','$textid','$date')";
   $sql4 = "insert into new(new_content,userid,new_userid,new_userid_number,textid,new_date,new_status) values('评论','$userid0','$userid','1','$textid','$date','未回复')";
   $sql5 = "update new set new_status = '已回复' where new_date='$new_date' ";
   mysql_query($sql,$conn);
   $insert_id = mysql_insert_id();
   mysql_query($sql4);
   mysql_query($sql5);
   mysql_close();
 ?>
 <?php  
   $sql6 = "select * from review where reviewid='$insert_id'";
   $sel6 = mysql_query($sql6,$conn);
   $res6 = mysql_fetch_array($sel6);
   $review_content = explode("/",$res6['review_content']);
   $review_time = strtotime($res6['review_date']);
   $response_id = $res6['userid'];
   $responsed_id = $res6['review_userid'];
   $sql7 = "select username from user where userid = '$response_id' ";
   $sql8 = "select username from user where userid = '$responsed_id' ";
   $sel7 = mysql_query($sql7,$conn);
   $sel8 = mysql_query($sql8,$conn);
   $res7 = mysql_fetch_array($sel7);
   $res8 = mysql_fetch_array($sel8);
   echo "<font color='#8B2500'>".$res7['username']."</font>回复"."<font color='#BF3EFF'>".$res8['username']."</font>：";
   echo tranExpression($review_content)."&nbsp;&nbsp;&nbsp;"."<font color='#8B2500'>".tranTime($review_time)."</font>";
?>
