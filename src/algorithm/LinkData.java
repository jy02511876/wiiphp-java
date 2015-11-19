package algorithm;

public class LinkData {
	
	
}


class DATA2
{
	String key;
	String name;
	int age;
}

class CLType
{
	DATA2 nodeData = new DATA2();
	CLType nextNode;
	
	CLType CLTypeAddEnd(CLType head,DATA2 nodeData){
		CLType node,htemp;
		if((node = new CLType()) == null){
			System.out.print("申请内存失败！\n");
			return null;
		}else{
			node.nodeData = nodeData;
			node.nextNode = null;
			if(head == null){
				head = node;
				return head;
			}
			htemp = head;
			while(htemp.nextNode != null)
			{
				htemp = htemp.nextNode;
			}
			htemp.nextNode = node;
			return head;
		}
	}
	
	
	CLType CLAddFirst(CLType head,DATA2 nodeData){
		
	}
}