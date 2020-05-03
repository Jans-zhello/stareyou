<?php
   session_start();
 ?>
 <?php
  include_once '../DB/connect.php'; 
  header('content-type:text/html charset:utf-8');
  $id = $_GET['id'];
  $textid = $_GET['textid'];
  $text_date = $_GET['text_date'];
  $sql0 = "SET FOREIGN_KEY_CHECKS=0;";
  $sql = "delete from send_text where textid='$textid' ";
  $sql2 = "delete from send_picture where picture_date= '$text_date' ";
  $sql3 = "delete from send_video where video_date='$text_date'";
  $sel0 = mysql_query($sql0,$conn);
  $sel = mysql_query($sql,$conn);
  $sel2 = mysql_query($sql2,$conn);
  $sel3 = mysql_query($sql3,$conn);
  if ($sql0 and ($sel or $sel2 or $sel3)) {
  	echo "<script> window.location.href='../askcontent_list.php?id=$id';
   	      </script>;";
  }else{
  	echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />'; 
  	echo "<script> alert('删除出错!');
   	      </script>;"; 
  }
  ?>