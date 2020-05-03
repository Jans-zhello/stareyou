<?php 
   include_once '../DB/connect.php';
   if (isset($_POST['submit']) and $_POST['submit']=="注册") {
   	 $username = $_POST['username'];
   	 $password = $_POST['password'];
   	 $sex = $_POST['sex'];
   	 $mail = $_POST['mail'];
   	 $phone = $_POST['phone'];
   	 $address = $_POST['address'];
   	 $date = date("Y-m-d H:i:s");
     $check = mysql_query("select * from user");
     while ($array = mysql_fetch_array($check)) {
        if ($username == $array['username']) {
        echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
        echo "<script> alert('用户名已存在!');window.location.href='../register.html';
           </script>;";
           return;
        }
     }
     $sql = "insert into user(username,password,date) values
     ('$username','$password','$date')";
   	 $insert = mysql_query($sql,$conn);
   	 $variable = mysql_insert_id();//获取上个sql插入的id
   	 $sql2 = "insert into register(sex,mail,phone,address,userid) values('$sex','$mail','$phone','$address','$variable')";
   	 $insert2 = mysql_query($sql2,$conn);
     mysql_close();
   	 if ($insert and $insert2) {
       echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
   	   echo "<script> alert('注册成功!');window.location.href='../index.html';
   	       </script>;";
   	       return;
   	 }
      echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
   	  echo "<script> alert('注册失败,请重新注册!');window.location.href='../index.html';
   	      </script>;";
   }
   ?>