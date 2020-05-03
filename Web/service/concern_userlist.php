<?php
   session_start();
?>
<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8'); 
  $userid0 = $_POST['userid0'];
  $listsql = "select attention_id from concern where userid = '$userid0' ";
  $listsel = mysql_query($listsql,$conn);
  while ($fanslistres = mysql_fetch_array($listsel)) {
   $listsql2 = "select username from user where userid = '$fanslistres[0]' ";
   $listsel2 = mysql_query($listsql2,$conn);
   $listres2 = mysql_fetch_array($listsel2);
   echo "<a href='./view_other.php?userid=$fanslistres[0]&&userid0=$userid0' target='_blank'><font color='#473C8B' style='margin-left:110px;margin-top:5px;' size='4'>".++$num."&emsp;&emsp;".$listres2['username']."</font></a>"."<br>";  
    }
?>