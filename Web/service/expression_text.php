 <script type="text/javascript" charset="utf-8" >
    function insertHtmlAtCaret(html,$id2) {  
        var sel, range;  
        if (window.getSelection) {  
            sel = window.getSelection();  
            if (sel.getRangeAt && sel.rangeCount) {  
                range = sel.getRangeAt(0);  
                range.deleteContents();  
                var el = document.createElement("div");  
                el.innerHTML = html;  
                var frag = document.createDocumentFragment(), node, lastNode;  
                while ((node = el.firstChild)) {  
                    lastNode = frag.appendChild(node);  
                }  
                range.insertNode(frag);  
                if (lastNode) {  
                    range = range.cloneRange();  
                    range.setStartAfter(lastNode);  
                    range.collapse(true);  
                    sel.removeAllRanges();  
                    sel.addRange(range);  
                }  
            }  
        } else if (document.selection && document.selection.type != "Control") {  
            document.selection.createRange().pasteHTML(html);  
        }
      $($id2).hide();  
    }  
</script>
<?php 
function expressionTran($id,$id2){?>
 <img src="expression/0.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus();insertHtmlAtCaret('/微笑/',<?php echo $id2 ?>)"/>|
 <img src="expression/1.gif" width="30"  height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/撇嘴/',<?php echo $id2 ?>)"/>|
 <img src="expression/2.gif" width="30"  height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/色/',<?php echo $id2 ?>)"/>| 
<img src="expression/3.gif" width="30"  height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/发呆/',<?php echo $id2 ?>)" />| 
<img src="expression/4.gif" width="30"  height="30"/ onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/得意/',<?php echo $id2 ?>)">| 
<img src="expression/5.gif" width="30"  height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/流泪/',<?php echo $id2 ?>)"/>| 
<img src="expression/6.gif" width="30"  height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/害羞/',<?php echo $id2 ?>)"/>| 
<img src="expression/7.gif" width="30"  height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/闭嘴/',<?php echo $id2 ?>)"/>|  
<img src="expression/8.gif" width="30"  height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/睡/',<?php echo $id2 ?>)"/>|  
<img src="expression/9.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/大哭/',<?php echo $id2 ?>)"/>|  
<img src="expression/10.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/尴尬/',<?php echo $id2 ?>)"/>| 
<img src="expression/11.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/发怒/',<?php echo $id2 ?>)"/>| 
<img src="expression/12.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/调皮/',<?php echo $id2 ?>)"/>|
<img src="expression/13.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/龇牙/',<?php echo $id2 ?>)"/>| 
<img src="expression/14.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/惊讶/',<?php echo $id2 ?>)"/>| 
<img src="expression/15.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/难过/',<?php echo $id2 ?>)"/>| 
<img src="expression/16.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/酷/',<?php echo $id2 ?>)"/>| 
<img src="expression/17.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/冷汗/',<?php echo $id2 ?>)"/>| 
<img src="expression/18.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/抓狂/',<?php echo $id2 ?>)"/>|  
<img src="expression/19.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/吐/',<?php echo $id2 ?>)"/>|  
<img src="expression/20.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/偷笑/',<?php echo $id2 ?>)"/>|
<img src="expression/21.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/可爱/',<?php echo $id2 ?>)"/>| 
<img src="expression/22.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/白眼/',<?php echo $id2 ?>)"/>| 
<img src="expression/23.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/傲慢/',<?php echo $id2 ?>)"/>|
<img src="expression/24.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/饥饿/',<?php echo $id2 ?>)"/>| 
<img src="expression/25.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/困/',<?php echo $id2 ?>)"/>| 
<img src="expression/26.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/惊恐/',<?php echo $id2 ?>)"/>| 
<img src="expression/27.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/流汗/',<?php echo $id2 ?>)"/>| 
<img src="expression/28.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/憨笑/',<?php echo $id2 ?>)"/>| 
<img src="expression/29.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/大兵/',<?php echo $id2 ?>)"/>|  
<img src="expression/30.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/奋斗/',<?php echo $id2 ?>)"/>|  
<img src="expression/31.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/咒骂/',<?php echo $id2 ?>)"/>|
<img src="expression/32.gif" width="30" height="30"  onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/疑问/',<?php echo $id2 ?>)"/>| 
<img src="expression/33.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/嘘/',<?php echo $id2 ?>)"/>| 
<img src="expression/34.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/晕/',<?php echo $id2 ?>)"/>|
<img src="expression/35.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/折磨/',<?php echo $id2 ?>)"/>| 
<img src="expression/36.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/衰/',<?php echo $id2 ?>)"/>| 
<img src="expression/37.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/骷髅/',<?php echo $id2 ?>)"/>| 
<img src="expression/38.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/敲打/',<?php echo $id2 ?>)"/>| 
<img src="expression/39.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/再见/',<?php echo $id2 ?>)"/>| 
<img src="expression/40.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/擦汗/',<?php echo $id2 ?>)"/>|  
<img src="expression/41.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/抠鼻/',<?php echo $id2 ?>)"/>|  
<img src="expression/42.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/鼓掌/',<?php echo $id2 ?>)"/>|
<img src="expression/43.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/糗大了/',<?php echo $id2 ?>)"/>| 
<img src="expression/44.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/坏笑/',<?php echo $id2 ?>)"/>| 
<img src="expression/45.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/左哼哼/',<?php echo $id2 ?>)"/>|
<img src="expression/46.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/右哼哼/',<?php echo $id2 ?>)"/>| 
<img src="expression/47.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/哈欠/',<?php echo $id2 ?>)"/>| 
<img src="expression/48.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/鄙视/',<?php echo $id2 ?>)"/>| 
<img src="expression/49.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/委屈/',<?php echo $id2 ?>)"/>| 
<img src="expression/50.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/快哭了/',<?php echo $id2 ?>)"/>| 
<img src="expression/51.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/阴险/',<?php echo $id2 ?>)"/>|  
<img src="expression/52.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/亲亲/',<?php echo $id2 ?>)"/>|  
<img src="expression/53.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/吓/',<?php echo $id2 ?>)"/>|
<img src="expression/54.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/可怜/',<?php echo $id2 ?>)"/>| 
<img src="expression/55.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/菜刀/',<?php echo $id2 ?>)"/>| 
<img src="expression/56.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/西瓜/',<?php echo $id2 ?>)"/>|
<img src="expression/57.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/啤酒/',<?php echo $id2 ?>)"/>| 
<img src="expression/58.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/篮球/',<?php echo $id2 ?>)"/>| 
<img src="expression/59.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/乒乓球/',<?php echo $id2 ?>)"/>| 
<img src="expression/60.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/拥抱/',<?php echo $id2 ?>)"/>|
<img src="expression/61.gif" width="30" height="30" onclick="document.getElementById('<?php echo $id; ?>').focus(); insertHtmlAtCaret('/握手/',<?php echo $id2 ?>)"/>| 
<?php }
 ?>
