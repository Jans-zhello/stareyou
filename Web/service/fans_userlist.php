<?php
   session_start();
?>
<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
  $userid0 = $_POST['userid0']; 
  $fanslistsql = "select userid from concern where attention_id = '$userid0' ";
  $fanslistsel = mysql_query($fanslistsql,$conn);
  while ($fanslistres = mysql_fetch_array($fanslistsel)) {
   $fanslistsql2 = "select username from user where userid = '$fanslistres[0]' ";
   $fanslistsel2 = mysql_query($fanslistsql2,$conn);
   $fanslistres2 = mysql_fetch_array($fanslistsel2);
   echo "<a href='./view_other.php?userid=$fanslistres[0]&&userid0=$userid0' target='_blank'><font color='#473C8B' style='margin-left:110px;margin-top:5px;' size='4'>".++$num."&emsp;&emsp;".$fanslistres2['username']."</font></a>"."<br>";  
    }
?>