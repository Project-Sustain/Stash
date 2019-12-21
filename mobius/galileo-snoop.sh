#!/bin/bash
while IFS='' read -r line || [[ -n "$line" ]]; do
    
    ssh $line "cat /s/$line/a/nobackup/galileo/sapmitra/galileo-sapmitra/storage-node.log | grep ENTIRE | tail -1 > /s/chopin/b/grad/sapmitra/Documents/Prophecy/results/times/$line;"&
	echo "Snooping galileo on $line"
done < "$1"

