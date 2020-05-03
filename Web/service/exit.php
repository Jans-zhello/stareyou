<?php
 session_id($_GET['id']);  
 session_start();
$_SESSION['username']=null;
unset($_GET['id']);
 session_destroy();
 echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
 echo "<script> window.location.href='../index.html';
   	 </script>;";
 ?>