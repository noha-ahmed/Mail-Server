package application;

import MyDataStructures.DLinkedList;
import MyDataStructures.LinkedBasedQueue;
import MyDataStructures.NodeStack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Filter implements IFilter{
	String FilterType;
	String SearchFor;
	
	public Filter( String filterType , String searchFor) {
		this.FilterType = filterType;
		this.SearchFor = searchFor;
	}
	
	public DLinkedList Search(DLinkedList numbers) {
		DLinkedList index = new DLinkedList();
		NodeStack stack = new NodeStack();
		stack.push(0);
		stack.push(numbers.size()-1);
		Sort sort = new Sort();
		if(FilterType=="Date") {
			sort.SortType="Date";
			sort.Sorting(numbers);
			DateFormat Format = new SimpleDateFormat("yyyy-dd-MM"); 
			while(!stack.isEmpty()) {
				int end = (int)stack.pop();
				int start = (int)stack.pop();
				if(end < start) {
					continue;
				}
				int p = start + ((end-start)/2);
				if((Format.format(((EmailInfo)numbers.get(p)).getDate())).compareTo(SearchFor)==0) {
					index.add(((EmailInfo)numbers.get(p)));
					stack.push(p+1);
					stack.push(end);
					stack.push(start);
					stack.push(p-1);
				}else if(end==start) {
					continue;
				}else if((Format.format(((EmailInfo)numbers.get(p)).getDate())).compareTo(SearchFor) > 0) {
					stack.push(p+1);
					stack.push(end);
				}else if((Format.format(((EmailInfo)numbers.get(p)).getDate())).compareTo(SearchFor) < 0) {
					stack.push(start);
					stack.push(p);
				}
			}
		}else if(FilterType=="Subject") {
			sort.SortType="Subject";
			sort.Sorting(numbers);
			while(!stack.isEmpty()) {
				int end = (int)stack.pop();
				int start = (int)stack.pop();
				if(end < start) {
					continue;
				}
				int p = start + ((end-start)/2);
				if(((EmailInfo)numbers.get(p)).getSubject().compareToIgnoreCase(SearchFor) == 0) {
					index.add(((EmailInfo)numbers.get(p)));
					stack.push(p+1);
					stack.push(end);
					stack.push(start);
					stack.push(p-1);
				}else if(end==start) {
					continue;
				}else if(((EmailInfo)numbers.get(p)).getSubject().compareToIgnoreCase(SearchFor) < 0) {
					stack.push(p+1);
					stack.push(end);
				}else if(((EmailInfo)numbers.get(p)).getSubject().compareToIgnoreCase(SearchFor) > 0) {
					stack.push(start);
					stack.push(p);
				}
			}
		}else if(FilterType=="Sender") {
			sort.SortType="Sender";
			sort.Sorting(numbers);
			while(!stack.isEmpty()) {
				int end = (int)stack.pop();
				int start = (int)stack.pop();
				if(end < start) {
					continue;
				}
				int p = start + ((end-start)/2);
				if(((EmailInfo)numbers.get(p)).getSender().compareToIgnoreCase(SearchFor) == 0) {
					index.add(((EmailInfo)numbers.get(p)));
					stack.push(p+1);
					stack.push(end);
					stack.push(start);
					stack.push(p-1);
				}else if(end==start) {
					continue;
				}else if(((EmailInfo)numbers.get(p)).getSender().compareToIgnoreCase((String)SearchFor) < 0) {
					stack.push(p+1);
					stack.push(end);
				}else if(((EmailInfo)numbers.get(p)).getSender().compareToIgnoreCase((String)SearchFor) > 0) {
					stack.push(start);
					stack.push(p);
				}
			}
		}else if(FilterType=="Priority") {
			sort.SortType="Priority";
			sort.Sorting(numbers);
			while(!stack.isEmpty()) {
				int end = (int)stack.pop();
				int start = (int)stack.pop();
				if(end < start) {
					continue;
				}
				int p = start + ((end-start)/2);
				if(((EmailInfo)numbers.get(p)).getPriority() == Integer.parseInt(SearchFor)) {
					index.add(((EmailInfo)numbers.get(p)));
					stack.push(p+1);
					stack.push(end);
					stack.push(start);
					stack.push(p-1);
				}else if(end==start) {
					continue;
				}else if(((EmailInfo)numbers.get(p)).getPriority() < Integer.parseInt(SearchFor)) {
					stack.push(p+1);
					stack.push(end);
				}else if(((EmailInfo)numbers.get(p)).getPriority() > Integer.parseInt(SearchFor)) {
					stack.push(start);
					stack.push(p);
				}
			}
		}/*else if(FilterType=="Receiver") {
			DLinkedList SearchList = new DLinkedList();
			for(int i=0; i<numbers.size(); i++) {
				LinkedBasedQueue receivers= ((EmailInfo)numbers.get(i)).getReceiver();
				for(int j=0; j<receivers.size(); j++) {
					ReceiversSearch add = new ReceiversSearch();
					add.setReceiver((String)receivers.dequeue());
					add.setIndex(i);
					SearchList.add(add);
				}
			}
			sort.SortType="Receiver";
			sort.iterativeQsort(SearchList);
			while(!stack.isEmpty()) {
				int end = (int)stack.pop();
				int start = (int)stack.pop();
				if(end < start) {
					continue;
				}
				int p = start + ((end-start)/2);
				if(((ReceiversSearch)SearchList.get(p)).getReceiver().equals(SearchFor)) {
					index.add((numbers.get(((ReceiversSearch)SearchList.get(p)).getIndex())));
					stack.push(p+1);
					stack.push(end);
					stack.push(start);
					stack.push(p-1);
				}else if(end==start) {
					continue;
				}else if(((ReceiversSearch)SearchList.get(p)).getReceiver().compareToIgnoreCase((String)SearchFor) < 0) {
					stack.push(p+1);
					stack.push(end);
				}else if(((ReceiversSearch)SearchList.get(p)).getReceiver().compareToIgnoreCase((String)SearchFor) > 0) {
					stack.push(start);
					stack.push(p);
				}
			}
		}*/
		return index;
	}
}
		
