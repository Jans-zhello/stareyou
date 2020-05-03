<?php
$count_ip = file("countlog.txt");
$temp0 = array();
for ($i=0;$i<count($count_ip);$i++) { 
 if (trim($count_ip[$i]) != $_SERVER["REMOTE_ADDR"]){
 	array_push($temp0,trim($count_ip[$i])."\n");
 }
}
array_push($temp0,$_SERVER["REMOTE_ADDR"]."\n");
$entries0 = implode("",$temp0);
// opens countlog.txt to change new hit number
$datei = fopen("countlog.txt","w");
fputs($datei,$entries0);
fclose($datei);
echo "网站总访问量:".count($temp0);
?>
<?php 
$online_log = "count.txt"; //保存人数的文件,
$timeout = 300;//300秒内没动作者,认为掉线
$entries = file($online_log);
$temp = array();
for ($i=0;$i<count($entries);$i++) {
$entry = explode(",",trim($entries[$i]));
if (($entry[0] != $_SERVER["REMOTE_ADDR"]) && ($entry[1] > time())) {
array_push($temp,$entry[0].",".$entry[1]."\n"); //检查更新之前浏览者的信息,并去掉超时者,保存进$temp
}
}
array_push($temp,$_SERVER["REMOTE_ADDR"].",".(time()+($timeout))."\n"); //增加新浏览者并更新时间
$users_online = count($temp); //计算在线人数
$entries = implode("",$temp);
//写入文件
$fp = fopen($online_log,"w");
flock($fp,LOCK_EX); //flock() 不能在NFS以及其他的一些网络文件系统中正常工作
fputs($fp,$entries);
flock($fp,LOCK_UN);
fclose($fp);
echo "&nbsp;&nbsp;&nbsp;当前在线".$users_online."人";
 ?>