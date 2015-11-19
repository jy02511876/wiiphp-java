#!/usr/bin/php
<?php
/*
 * Created on 2013?12?15?
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
$word2count = array();
while(($line = fgets(STDIN)) !== false){
	$line = trim($line);
	list($word,$count) = explode(chr(9),$line);
	$count = intval($count);
	if($count > 0) $word2count[$word] += $count;
}
ksort($word2count);
foreach($word2count as $word=>$count){
	echo $word,chr(9),$count,PHP_EOL;
}
?>
