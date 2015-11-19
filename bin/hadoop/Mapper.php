#!/usr/bin/php
<?php
/*
 * Created on 2013?12?15?
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
$word2count = array();
while($line=fgets(STDIN) !== false){
	$line = strtolower(trim($line));
	$words = preg_split('/\W/',$line,0,PREG_SPLIT_NO_EMPTY);
	foreach($words as $word){
		$word2count[$word] +=1;
	}
}

foreach($word2count as $word=>$count){
	echo $word,chr(9),$count,PHP_EOL;
}
?>
