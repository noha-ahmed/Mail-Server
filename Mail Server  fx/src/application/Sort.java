package application;

import java.util.Date;
import MyDataStructures.DLinkedList;
import MyDataStructures.NodeStack;

public class Sort implements ISort{
	String SortType;
	
	public Sort() {
		
	}
	public Sort( String SortType) {
		this.SortType = SortType;
	}
	
	public void Sorting(DLinkedList index) {
		if(SortType=="Priority") {
			PrioritySort(index);
		}else {
			iterativeQuickSort(index);
		}
	}
	
	public void PrioritySort(DLinkedList index) {
		PriorityQueue priority = new PriorityQueue();
		for(int i=0; i<index.size(); i++) {
			priority.insert(index.get(i), ((EmailInfo)index.get(i)).getPriority());
		}
		int j=0;
		while(!priority.isEmpty()) {
			index.set(j, priority.removeMin());
			j++;
		}
	}
	
	public void iterativeQuickSort(DLinkedList index) { 
		NodeStack stack = new NodeStack(); 
		stack.push(0); 
		stack.push(index.size()); 
		while (!stack.isEmpty()) { 
			int end = (int)stack.pop(); 
			int start = (int)stack.pop(); 
			if (end - start < 2) { 
				continue; 
			} 
			int p = start + ((end - start) / 2);
			if(SortType=="Date") {
				p = partitionByDate(index, p, start, end);	
			} else {
				p = partitionByString(index, p, start, end);
			}
			stack.push(p + 1); 
			stack.push(end); 
			stack.push(start); 
			stack.push(p); 
			} 
		}
		
		private static int partitionByDate(DLinkedList input, int position, int start, int end) { 
			int l = start; 
			int h = end - 2; 
			Date piv = ((EmailInfo)input.get(position)).getDate(); 
			swap(input, position, end - 1); 
			while (l < h) { 
				if (((EmailInfo)input.get(l)).getDate().after(piv) || ((EmailInfo)input.get(l)).getDate().equals(piv)) { 
					l++;
				} else if (((EmailInfo)input.get(h)).getDate().before(piv)) {
					h--; 
				} else { 
					swap(input, l, h); 
				} 
			} 
			int idx = h; 
			if (((EmailInfo)input.get(h)).getDate().after(piv)) { 
				idx++; 
			} 
			swap(input, end - 1, idx); 
			return idx; 
		}

		private int partitionByString(DLinkedList input, int position, int start, int end) { 
			int idx = -1;
			int l = start; 
			int h = end - 2;
			if(SortType=="Subject") {
				String piv = ((EmailInfo)input.get(position)).getSubject();
				swap(input, position, end - 1); 
				while (l < h) { 
					if (((EmailInfo)input.get(l)).getSubject().compareToIgnoreCase(piv) < 0) { 
						l++;
					} else if (((EmailInfo)input.get(h)).getSubject().compareToIgnoreCase(piv) >= 0) {
						h--; 
					} else { 
						swap(input, l, h); 
					} 
				} 
				idx = h; 
				if (((EmailInfo)input.get(h)).getSubject().compareToIgnoreCase(piv) < 0) { 
					idx++; 
				}
				swap(input, end - 1, idx); 
			}else if(SortType=="Sender"){
				String piv = ((EmailInfo)input.get(position)).getSender();
				swap(input, position, end - 1); 
				while (l < h) { 
					if (((EmailInfo)input.get(l)).getSender().compareToIgnoreCase(piv) < 0) { 
						l++;
					} else if (((EmailInfo)input.get(h)).getSender().compareToIgnoreCase(piv) >= 0) {
						h--; 
					} else { 
						swap(input, l, h); 
					} 
				} 
				idx = h; 
				if (((EmailInfo)input.get(h)).getSender().compareToIgnoreCase(piv) < 0) { 
					idx++; 
				}
				swap(input, end - 1, idx); 
			}/*else if(SortType=="Receiver"){
				String piv = ((Filter.ReceiversSearch)input.get(position)).getReceiver();
				swap(input, position, end - 1); 
				while (l < h) { 
					if (((Filter.ReceiversSearch)input.get(l)).getReceiver().compareToIgnoreCase(piv) < 0) { 
						l++;
					} else if (((Filter.ReceiversSearch)input.get(h)).getReceiver().compareToIgnoreCase(piv) >= 0) {
						h--; 
					} else { 
						swap(input, l, h); 
					} 
				} 
				idx = h; 
				if (((Filter.ReceiversSearch)input.get(h)).getReceiver().compareToIgnoreCase(piv) < 0) { 
					idx++; 
				}
				swap(input, end - 1, idx); 
			}*/
			return idx;
		}
		
		private static void swap(DLinkedList arr, int i, int j) { 
			Object temp = arr.get(i); 
			arr.set(i, arr.get(j));
			arr.set(j, temp); 
		}

}
