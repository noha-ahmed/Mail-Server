package application;

public class PriorityQueue implements IPriorityQueue{
	int size=0;
	public class Node{
		Object element;
		int key;
		Node next;
	}
	Node head;
	Node tail;
	
	@Override
	public void insert(Object item, int key) {
		
		Node node = new Node();
		node.element=item;
		node.key=key;
		if(size==0) {
			node.next=null;
			head=node;
			tail=node;
		}else {
			Node temp = head;
			Node prev = temp;
			while(temp!=null) {
				if(temp.key==node.key) {
					node.next=temp.next;
					temp.next=node;
					break;
				}else if(temp.key > node.key) {
					if(temp==head) {
						node.next=head;
						head=node;
					}else {
						node.next=temp;
						prev.next=node;
					}
					break;
				}
				prev=temp;
				temp=temp.next;
			}
		if(temp==null) {
			node.next=null;
			tail.next=node;
			tail=node;
		}
		}
		size++;
	}

	@Override
	public Object removeMin() {
		if(size==0) {
			return null;	
		}else {
			size--;
			Object result = head.element;
			head=head.next;
			return result;
		}
	}

	@Override
	public Object min() {
		if(size==0) {
			return null;
		}else {
			return head.element;
		}
	}

	@Override
	public boolean isEmpty() {
		if(size==0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int size() {
		return size;
	}

}
