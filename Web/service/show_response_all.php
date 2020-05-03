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
  $textid = $_POST['textid'];
  $sql = "select * from review where textid = '$textid' ORDER BY review_date desc ";
  $sel = mysql_query($sql,$conn);
  while ($res = mysql_fetch_array($sel)) {
  	$userid = $res['userid'];
  	$content = explode("/",$res['review_content']);
    $time = strtotime($res['review_date']);
    $sql2 = "select username from user where userid='$userid' ";
    $sel2 = mysql_query($sql2,$conn);
    $res2 = mysql_fetch_array($sel2);
    echo $res2['username']."评论："."&nbsp;";
    echo tranExpression($content)."&nbsp;&nbsp;&nbsp;&nbsp;".tranTime($time)."<br>";
  }
?>