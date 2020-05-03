<?php 
   include_once '../DB/connect.php';
   if (isset($_POST['submit']) and $_POST['submit']=="提交") {
   	 $title = $_POST['title'];
   	 $content = $_POST['content'];
     $mail = $_POST['mail'];
   	 $date = date("Y-m-d H:i:s");
     $sql = "insert into suggestion(suggestion_title,suggestion_content,suggestion_user,suggestion_date) values
     ('$title','$content','$mail','$date')";
   	 $insert = mysql_query($sql,$conn);
   	 if ($insert) {
       echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
   	   echo "<script> alert('感谢您的反馈!');window.location.href='../index.html';
   	       </script>;";
   	       return;
   	 }
      echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
   	  echo "<script> alert('网络错误!请重新反馈');window.location.href='../index.html';
   	      </script>;";
   }
   ?>